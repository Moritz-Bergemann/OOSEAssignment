package Controller;

import Model.Items.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileStockManager implements StockManager {
    String filePath;

    public FileStockManager(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Set<Item> loadStock() throws StockManagerException {
        Set<Item> items = new HashSet<>();
        int lineNum = 1;

        //Trying to read file with resources
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while (line != null) {
                try {
                    Item newItem = interpretLine(line, lineNum);
                    items.add(newItem);
                }
                catch (FileStockManagerException e) {
                    System.out.println("Error reading line in file - " + e.getMessage()); //TODO proper error reporting
                }

                line = br.readLine();
                lineNum++;
            }
        }
        catch (FileNotFoundException fnf) {
            throw new StockManagerException("File not found", fnf);
        }
        catch (IOException io) {
            throw new StockManagerException("Failed to read file", io);
        }

        return items;
    }

    private Item interpretLine(String line, int lineNum) throws FileStockManagerException {
        Item newItem = null;

        //Splitting line into segments by ',' character
        String[] seg = line.split(",");

        switch (seg[0].strip()) {
            case "W": //Weapon
                try {
                    //Getting values from string
                    String name = seg[1].strip();
                    int minDamage = Integer.parseInt(seg[2].strip());
                    int maxDamage = Integer.parseInt(seg[3].strip());
                    int cost = Integer.parseInt(seg[4].strip());
                    String damageType = seg[5];
                    String weaponType = seg[6];

                    //Creating new weapon (& verifying parameters are valid via constructor)
                    try {
                        newItem = new WeaponBase(name, weaponType, damageType, minDamage, maxDamage, cost);
                    }
                    catch (IllegalArgumentException i) {
                        throw new FileStockManagerException(String.format("Line %d contains invalid parameters for " +
                                "weapon - %s", lineNum, i.getMessage()));
                    }
                }
                catch (NumberFormatException n) {
                    throw new FileStockManagerException(String.format("Line %d does not contain valid number", lineNum));
                }
                break;
            case "A": //Armour
                try {
                    //Getting values from string
                    String name = seg[1].strip();
                    int minDefence = Integer.parseInt(seg[2].strip());
                    int maxDefence = Integer.parseInt(seg[3].strip());
                    int cost = Integer.parseInt(seg[4].strip());
                    String material = seg[5];

                    //Creating new armour (& verifying parameters are valid via constructor)
                    try {
                        newItem = new Armour(name, material, minDefence, maxDefence, cost);
                    }
                    catch (IllegalArgumentException i) {
                        throw new FileStockManagerException(String.format("Line %d contains invalid parameters for " +
                                "armour - %s", lineNum, i.getMessage()));
                    }
                }
                catch (NumberFormatException n) {
                    throw new FileStockManagerException(String.format("Line %d does not contain valid number", lineNum));
                }
                break;
            case "P": //Potion
                try {
                    //Getting values from string
                    String name = seg[1].strip();
                    int minEffect = Integer.parseInt(seg[2].strip());
                    int maxEffect = Integer.parseInt(seg[3].strip());
                    int cost = Integer.parseInt(seg[4].strip());
                    String type = seg[5].strip();

                    //Creating new potion (& verifying parameters are valid via constructor)
                    try {
                        switch (type) {
                            case "H":
                                newItem = new HealingPotion(name, cost, minEffect, maxEffect);
                                break;
                            case "D":
                                newItem = new DamagingPotion(name, cost, minEffect, maxEffect);
                                break;
                            default:
                                throw new FileStockManagerException(String.format("Line %d did not contain valid " +
                                                "potion type", lineNum));
                        }
                    }
                    catch (IllegalArgumentException i) {
                        throw new FileStockManagerException(String.format("Line %d contains invalid parameters for " +
                                "potion - %s", lineNum, i.getMessage()));
                    }
                }
                catch (NumberFormatException n) {
                    throw new FileStockManagerException(String.format("Line %d does not contain valid number", lineNum));
                }
                break;
            default:
                throw new FileStockManagerException(String.format("Line %d did not contain valid item type", lineNum));
        }

        return newItem;
    }
}

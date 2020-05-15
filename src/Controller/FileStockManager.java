package Controller;

import Model.Items.*;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FileStockManager implements StockManager {
    private String filePath;
    private Set<Weapon> weapons;
    private Set<Armour> armour;
    private Set<Potion> potions;


    public FileStockManager(String filePath) {
        this.filePath = filePath;
        weapons = new HashSet<>();
        armour = new HashSet<>();
        potions = new HashSet<>();
    }

    @Override
    public void loadStock() throws StockManagerException {
        //Resetting all item sets
        weapons = new HashSet<>();
        armour = new HashSet<>();
        potions = new HashSet<>();

        int lineNum = 1;

        //Trying to read file with resources
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while (line != null) {
                try {
                    interpretLine(line, lineNum);
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
    }

    @Override
    public Set<Item> getLoadedStock() throws StockManagerException {
        Set<Item> entireStock = new HashSet<>();

        entireStock.addAll(weapons);
        entireStock.addAll(armour);
        entireStock.addAll(potions);

        return Collections.unmodifiableSet(entireStock);
    }

    @Override
    public Set<Weapon> getLoadedWeapons() throws StockManagerException {
        return Collections.unmodifiableSet(weapons);
    }

    @Override
    public Set<Armour> getLoadedArmour() throws StockManagerException {
        return Collections.unmodifiableSet(armour);
    }

    @Override
    public Set<Potion> getLoadedPotions() throws StockManagerException {
        return Collections.unmodifiableSet(potions);
    }

    /**
     * Interprets the current line and adds the item to the corresponding set if valid
     * @param line line to read
     * @param lineNum number of the line to read
     * @throws FileStockManagerException if line read does not define a valid item
     */
    private void interpretLine(String line, int lineNum) throws FileStockManagerException {
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
                    String damageType = seg[5].strip().toLowerCase();
                    String weaponType = seg[6].strip().toLowerCase();

                    //Creating new weapon & adding it to weapon set (& verifying parameters are valid via constructor)
                    try {
                        Weapon newWeapon = new WeaponBase(name, weaponType, damageType, minDamage, maxDamage, cost);
                        weapons.add(newWeapon);
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
                    String material = seg[5].strip().toLowerCase();

                    //Creating new armour & adding it to armour set (& verifying parameters are valid via constructor)
                    try {
                        Armour newArmour = new Armour(name, material, minDefence, maxDefence, cost);
                        armour.add(newArmour);
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

                    Potion newPotion;

                    //Creating new potion (& verifying parameters are valid via constructor)
                    try {
                        switch (type) {
                            case "H":
                                newPotion = new HealingPotion(name, cost, minEffect, maxEffect);
                                break;
                            case "D":
                                newPotion = new DamagingPotion(name, cost, minEffect, maxEffect);
                                break;
                            default:
                                throw new FileStockManagerException(String.format("Line %d did not contain valid " +
                                                "potion type", lineNum));
                        }

                        //Adding created potion to potions set
                        potions.add(newPotion);
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
    }
}

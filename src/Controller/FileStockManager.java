package Controller;

import Model.*;

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

        //Trying to read file with resources
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while (line != null) {
                try {
                    Item newItem = interpretLine(line);
                    items.add(newItem);
                }
            }
        }
        catch (FileNotFoundException fnf) {
            throw new StockManagerException("File not found", fnf);
        }
        catch (IOException io) {
            throw new StockManagerException("Failed to read file", io);
        }
    }

    private Item interpretLine(String line) {
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

                    //Creating new weapon
                    newItem = new WeaponBase(name, weaponType, damageType, minDamage, maxDamage, cost);
                }
                catch (NumberFormatException n) { //TODO what exceptions? REPEAT FOR NEXT ONES
                    //TODO what to do in case of error? Should inform user?
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

                    //Creating new armour
                    newItem = new Armour(name, material, minDefence, maxDefence, cost);
                }
                catch () { //TODO
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

                    //Creating new potion
                    switch (type) {
                        case "H":
                            newItem = new HealingPotion(name, cost, minEffect, maxEffect);
                            break;
                        case "D":
                            newItem = new DamagingPotion(name, cost, minEffect, maxEffect);
                            break;
                        default:
                            //TODO error thing here
                    }
                }
                catch () { //TODO
                }
                break;
        }

        return newItem;
    }
}

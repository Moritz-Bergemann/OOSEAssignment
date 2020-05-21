package Model.Items;

import Model.Chance;
import Model.ItemUser;

import java.util.Objects;

/**
 * Class for armour item type - can be used to calculate the armour's defence
 */
public class Armour implements Item {
    String name;
    String material;
    int minDefence;
    int maxDefence;
    int cost;

    public Armour(String name, String material, int minDefence, int maxDefence, int cost) {
        if (name.length() == 0 || material.length() == 0) {
            throw new IllegalArgumentException("String cannot be empty");
        }
        if (minDefence < 0 || maxDefence < 0 || cost < 0) {
            throw new IllegalArgumentException("Value cannot be less than 0");
        }
        if (minDefence > maxDefence) {
            throw new IllegalArgumentException("Minimum defence cannot be greater than maximum defence");
        }
        
        this.name = name;
        this.material = material;
        this.minDefence = minDefence;
        this.maxDefence = maxDefence;
        this.cost = cost;
    }

    public int calcDefence() {
        return Chance.randBetween(minDefence, maxDefence);
    }

    //From Item interface
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getMinEffect() {
        return minDefence;
    }

    @Override
    public int getMaxEffect() {
        return maxDefence;
    }

    @Override
    public void addToInventory(ItemUser itemUser) throws InventoryException {
        itemUser.addArmour(this);
    }

    @Override
    public void removeFromInventory(ItemUser itemUser) throws InventoryException {
        itemUser.removeArmour(this);
    }

    @Override
    public String getDescription() {
        return String.format("%s armour", material);
    }

    @Override
    public String getType() {
        return "armour";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Armour armour = (Armour) o;
        return minDefence == armour.minDefence &&
                maxDefence == armour.maxDefence &&
                cost == armour.cost &&
                name.equals(armour.name) &&
                material.equals(armour.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, material, minDefence, maxDefence, cost);
    }
}

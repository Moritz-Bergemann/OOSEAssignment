package Model.Items;

import Controller.Chance;
import Model.ItemUser;

public class Armour implements Item {
    String name;
    String material;
    int minDefence;
    int maxDefence;
    int cost;

    public Armour(String name, String material, int minDefence, int maxDefence, int cost) {
        this.name = name;
        this.material = material;
        this.minDefence = minDefence;
        this.maxDefence = maxDefence;
        this.cost = cost;
    }

    public int calcDefence() {
        return Chance.randBetween(minDefence, maxDefence);
    }

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
}

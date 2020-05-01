package Model.Items;

import Model.Character;
import Model.ItemUser;

import java.util.Objects;

public abstract class Potion implements Item{
    private String name;
    private int cost;

    public Potion(String name, int cost) {
        if (name.length() == 0) {
            throw new IllegalArgumentException("String cannot be empty");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }

        this.name = name;
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    /**
     * Applies the potion to a character.
     * The logic for this is defined in the 'Potion' class rather than the controller (like applying attacks is)
     *  as the logic for what should be done by the potion is highly correlated with the potion itself.
     * @param character
     */
    public abstract void apply(Character character);

    @Override
    public void addToInventory(ItemUser itemUser) throws InventoryException {
        itemUser.addPotion(this);
    }

    @Override
    public void removeFromInventory(ItemUser itemUser) throws InventoryException {
        itemUser.removePotion(this);
    }
    
    /**
     * Gives description of potion. Inheriting potion types should replace the '_TYPE_' flag with the potion's type.
     * @return potion description
     */
    @Override
    public String getDescription() {
        return "_TYPE_ potion";
    }

    //Declaring equals method as abstract to ensure it is implemented by subclasses
    public abstract boolean equals(Object o);
}

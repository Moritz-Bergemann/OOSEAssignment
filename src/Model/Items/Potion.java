package Model.Items;

import Model.GameCharacter;
import Model.ItemUser;

/**
 * Abstract class for potions that can be applied to characters.
 * Must be extended by individual potion types.
 */
public abstract class Potion implements Item{
    protected String name;
    protected int cost;

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

    @Override
    public String getName() {
        return name;
    }

    /**
     * Applies the potion to a character.
     * The logic for this is defined in the 'Potion' class rather than the controller (like applying attacks is)
     *  as the logic for what should be done by the potion is highly correlated with the potion itself.
     * @param target target for the potion application
     * @return the amount of effect caused by the potion (as an integer)
     */
    public abstract int apply(GameCharacter target);

    @Override
    public void addToInventory(ItemUser itemUser) throws InventoryException {
        itemUser.addPotion(this);
    }

    /**
     * Returns the effect of the potion. Used PURELY for interface purposes, not as an instanceof equivalent.
     * @return Type of potion (as a string)
     */
    public abstract String getEffectType();


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

    @Override
    public String getType() {
        return "potion";
    }

    //Declaring equals method as abstract to ensure it is implemented by subclasses
    public abstract boolean equals(Object o);
}

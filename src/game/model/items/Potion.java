package game.model.items;

import game.model.GameCharacter;
import game.model.ItemUser;

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

    public Potion(Potion potion) {
        this.name = potion.name;
        this.cost = potion.cost;
    }

    /**
     * Makes the imported user use the potion within the context of conflict with the imported enemy. This usage
     *  may or may not involve effects on the user and/or the enemy, depending on the potion type.
     * @param user Character that used the potion
     * @param enemy Character user was battling when using the potion
     * @return A description of the events that occurred during the potion's usage (for user interface purposes)
     */
    public abstract String use(GameCharacter user, GameCharacter enemy);

    /**
     * Returns the effect of the potion. Used PURELY for interface purposes, not as an instanceof equivalent.
     * @return Type of potion (as a string)
     */
    public abstract String getEffectType();

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getName() {
        return name;
    }

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

    @Override
    public String getType() {
        return "potion";
    }

    //Declaring equals method as abstract to ensure it is implemented by subclasses
    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract Potion clone();
}

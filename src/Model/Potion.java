package Model;

public abstract class Potion implements Item{
    private String name;
    private int cost;

    public Potion(String name, int cost) {
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
}

package Model;

/**
 * Interface for items that may be acquired/used by the player
 */
public interface Item {
    public String getName();

    public int getCost();

    public int getMinEffect();

    public int getMaxEffect();

    //TODO add getDescription message
}

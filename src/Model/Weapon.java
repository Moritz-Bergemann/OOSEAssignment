package Model;

public interface Weapon extends Item {
    public int calcAttack();

    public String getName();

    public int getCost();

    public int getMinEffect();

    public int getMaxEffect();

    public void addToInventory(ItemUser itemUser);
}

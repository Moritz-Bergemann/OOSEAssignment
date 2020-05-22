package game.model.items;

public interface Weapon extends Item {
    public int calcAttack();

    @Override
    public Weapon clone();
}

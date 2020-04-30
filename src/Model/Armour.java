package Model;

import Controller.Chance;

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
}

package Model;

import Controller.Chance;

public class Armour implements Item {
    String name;
    String material;
    int minDefense;
    int maxDefense;
    int cost;

    public Armour(String name, String material, int minDefense, int maxDefense, int cost) {
        this.name = name;
        this.material = material;
        this.minDefense = minDefense;
        this.maxDefense = maxDefense;
        this.cost = cost;
    }

    public int calcDefense() {
        return Chance.randBetween(minDefense, maxDefense);
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
        return minDefense;
    }

    @Override
    public int getMaxEffect() {
        return maxDefense;
    }
}

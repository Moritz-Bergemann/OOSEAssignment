package Model;

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getMinEffect() {
        return 0;
    }

    @Override
    public int getMaxEffect() {
        return 0;
    }
}

package Controller;

import Model.Items.*;

public class EnchantmentFactory {
    public static void applyEnchantment(String name, Weapon weapon) {
        //Applying enchantment based on name
        switch (name) {
            case "DamageUp2":
                weapon = new DamageUp2Enchantment(weapon);
                break;
            case "DamageUp5":
                weapon = new DamageUp5Enchantment(weapon);
                break;
            case "FireDamage":
                weapon = new FireDamageEnchantment(weapon);
                break;
            case "PowerUp":
                weapon = new PowerUpEnchantment(weapon);
                break;
            default:
                throw new IllegalArgumentException("Non-existent enchantment name");
        }
    }

    public static int getEnchantmentCost(String name) {
        int cost;

        switch (name) {
            case "DamageUp2":
                cost = DamageUp2Enchantment.getIndividualCost();
                break;
            case "DamageUp5":
                cost = DamageUp5Enchantment.getIndividualCost();
                break;
            case "FireDamage":
                cost = FireDamageEnchantment.getIndividualCost();
                break;
            case "PowerUp":
                cost = PowerUpEnchantment.getIndividualCost();
                break;
            default:
                throw new IllegalArgumentException("Non-existent enchantment name");
        }

        return cost;
    }
}

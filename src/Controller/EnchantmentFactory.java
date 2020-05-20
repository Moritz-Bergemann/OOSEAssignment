package Controller;

import Model.Items.*;
import Model.Items.Enchantments.DamageUp2Enchantment;
import Model.Items.Enchantments.DamageUp5Enchantment;
import Model.Items.Enchantments.FireDamageEnchantment;
import Model.Items.Enchantments.PowerUpEnchantment;

import java.util. ArrayList;
import java.util.List;

public class EnchantmentFactory {
    public static Weapon applyEnchantment(String name, Weapon weapon) {
        Weapon enchantedWeapon;

        //Applying enchantment based on name
        switch (name) {
            case "DamageUp2":
                enchantedWeapon = new DamageUp2Enchantment(weapon);
                break;
            case "DamageUp5":
                enchantedWeapon = new DamageUp5Enchantment(weapon);
                break;
            case "FireDamage":
                enchantedWeapon = new FireDamageEnchantment(weapon);
                break;
            case "PowerUp":
                enchantedWeapon = new PowerUpEnchantment(weapon);
                break;
            default:
                throw new IllegalArgumentException("Non-existent enchantment name");
        }

        return enchantedWeapon;
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

    public static String getEnchantmentDescription(String name) {
        String description;

        switch (name) {
            case "DamageUp2":
                description = DamageUp2Enchantment.getIndividualDescripton();
                break;
            case "DamageUp5":
                description = DamageUp5Enchantment.getIndividualDescripton();
                break;
            case "FireDamage":
                description = FireDamageEnchantment.getIndividualDescripton();
                break;
            case "PowerUp":
                description = PowerUpEnchantment.getIndividualDescripton();
                break;
            default:
                throw new IllegalArgumentException("Non-existent enchantment name");
        }

        return description;
    }

    public static List<String> getAllEnchantmentNames() {
        List<String> nameSet = new  ArrayList<>();
        nameSet.add("DamageUp2");
        nameSet.add("DamageUp5");
        nameSet.add("FireDamage");
        nameSet.add("PowerUp");

        return nameSet;
    }
}

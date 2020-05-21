package Controller;

import Model.Items.*;
import Model.Items.WeaponEnchantments.DamageUp2Enchantment;
import Model.Items.WeaponEnchantments.DamageUp5Enchantment;
import Model.Items.WeaponEnchantments.FireDamageEnchantment;
import Model.Items.WeaponEnchantments.PowerUpEnchantment;

import java.util. ArrayList;
import java.util.List;

/**
 * Factory for creating (and getting information about) enchantments using the enchantment name as a string to retrieve
 *  the desired enchantment
 */
public class EnchantmentFactory {
    /**
     * Applies an enchantment to a weapon. Throws IllegalArgumentException if the enchantment is not known about.
     * @param name name of enchantment to apply
     * @param weapon weapon to apply enchantment to
     * @return weapon with enchantment applied to it
     */
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

    /**
     * Retrieve the cost of an enchantment by name
     * @param name name of enchantment
     * @return cost of enchantment
     */
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

    /**
     * Retrieve the description of an enchantment by name
     * @param name name of enchantment
     * @return description of enchantment
     */
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

    /**
     * Creates a list of the names of all enchantments the factory knows about
     * @return list of all known enchantment names
     */
    public static List<String> getAllEnchantmentNames() {
        List<String> nameSet = new  ArrayList<>();
        nameSet.add("DamageUp2");
        nameSet.add("DamageUp5");
        nameSet.add("FireDamage");
        nameSet.add("PowerUp");

        return nameSet;
    }
}

package testing;

import game.model.items.*;
import game.model.items.WeaponEnchantments.DamageUp5Enchantment;
import game.model.items.WeaponEnchantments.FireDamageEnchantment;
import game.model.items.WeaponEnchantments.PowerUpEnchantment;
import game.model.items.WeaponBase;

public class ItemTest {
    public static void main(String[] args) {
        System.out.println("WEAPONS CHECK:");

        Weapon weapon1, weapon2, weapon2Duplicate;
        weapon1 = new WeaponBase("Handaxe", "axe", "chopping", 4, 8, 20);
        weapon2 = new WeaponBase("Longsword", "sword", "stabbing", 10, 15, 40);
        weapon2Duplicate = new WeaponBase("Longsword", "sword", "stabbing", 10, 15, 40);

        System.out.println("Checking if weapon 1 equal to weapon 2 (no): " + weapon1.equals(weapon2));
        System.out.println("Checking if weapon 2 equal to its duplicate (yes): " + weapon2.equals(weapon2Duplicate));
        System.out.println();
        System.out.println("Enchanting all 3 weapons with fire & then damage up 5...");
        weapon1 = new FireDamageEnchantment(weapon1);
        weapon1 = new DamageUp5Enchantment(weapon1);
        weapon2 = new FireDamageEnchantment(weapon1);
        weapon2 = new DamageUp5Enchantment(weapon1);
        weapon2Duplicate = new FireDamageEnchantment(weapon1);
        weapon2Duplicate = new DamageUp5Enchantment(weapon1);
        System.out.println("Checking if weapon 1 equal to weapon 2 (no): " + weapon1.equals(weapon2));
        System.out.println("Checking if weapon 2 still equal to its duplicate (yes): " + weapon2.equals(weapon2Duplicate));
        System.out.println();

        System.out.println("Enchanting weapon 2 duplicate with power up...");
        weapon2Duplicate = new PowerUpEnchantment(weapon2Duplicate);
        System.out.println("Checking if weapon 2 still equal to its duplicate (no): " + weapon2.equals(weapon2Duplicate));
        System.out.println();
    }
}

package org.chocon.itemPlugin.Item.Weapon;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public enum WeaponType {
    //============================================Type============================================
    BLACK_KNIFE("black-knife", new NamespacedKey(
            JavaPlugin.getProvidingPlugin(Weapon.class), "black-knife")),
    FIRE_SWORD("fire-sword", new NamespacedKey(
            JavaPlugin.getProvidingPlugin(Weapon.class), "fire-sword"));

    //==========================================Variable==========================================
    private final String name;
    private final NamespacedKey key;

    //============================================Get=============================================
    public String getName() { return name; }
    public NamespacedKey getKey() { return key; }

    //========================================Constructor=========================================
    WeaponType(String name, NamespacedKey key) {
        this.name = name;
        this.key = key;
    }

    public static NamespacedKey getKey(String name) {
        for (WeaponType type : WeaponType.values()) {
            if (type.getName().equals(name)) {
                return type.getKey();
            }
        }

        return null;
    }
}

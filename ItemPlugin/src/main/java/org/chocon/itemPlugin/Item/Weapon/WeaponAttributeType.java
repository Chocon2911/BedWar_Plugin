package org.chocon.itemPlugin.Item.Weapon;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.chocon.itemPlugin.ItemPlugin;

public enum WeaponAttributeType {
    //============================================Type============================================
    UUID("uuid"),
    PHYSIC_DAMAGE("physic-damage"),
    MAGIC_DAMAGE("magic-damage"),
    DURABILITY("durability"),
    ATTACK_SPEED("attack-speed"),
    ATTACK_RANGE("attack-range");

    //==========================================Variable==========================================
    private final String name;
    private final NamespacedKey key;

    //============================================Get=============================================
    public String getName() { return name; }
    public NamespacedKey getKey() { return key; }

    //========================================Constructor=========================================
    WeaponAttributeType(String name) {
        this.name = name;
        this.key = new NamespacedKey(JavaPlugin.getProvidingPlugin(ItemPlugin.class), name);
    }
}

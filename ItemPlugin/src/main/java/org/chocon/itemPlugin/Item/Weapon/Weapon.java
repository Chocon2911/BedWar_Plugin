package org.chocon.itemPlugin.Item.Weapon;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.chocon.itemPlugin.ItemPlugin;
import org.chocon.itemPlugin.Support.MyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Weapon {
    //===========================================Method===========================================
    public static ItemStack create(ItemPlugin plugin, WeaponType type) {
        FileConfiguration config = plugin.getConfig();
        String name = config.getString(type.getName() + ".name");
        Material material = Material.getMaterial(config.getString(
                type.getName() + ".material"));
        double physicDamage = config.getInt(type.getName() + ".physicDamage");
        double magicDamage = config.getInt(type.getName() + ".magicDamage");
        int durability = config.getInt(type.getName() + ".durability");
        double attackSpeed = config.getDouble(type.getName() + ".attackSpeed");
        double attackRange = config.getDouble(type.getName() + ".attackRange");

        ItemStack item = new ItemStack(material);
        item.setAmount(1);
        item.setDurability((short) durability);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MyUtil.rainbow(name));
//        double baseAtkSpeed = getBaseAttackSpeed(item);
//        AttributeModifier atkSpeedMod = new AttributeModifier(
//                UUID.randomUUID(),
//                "Attack Speed",
//                attackSpeed - baseAtkSpeed,
//                AttributeModifier.Operation.ADD_NUMBER,
//                EquipmentSlot.HAND);
//        meta.addAttributeModifier(Attribute.ATTACK_SPEED, atkSpeedMod);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Physic Damage: " + physicDamage);
        lore.add(ChatColor.GRAY + "Magic Damage: " + magicDamage);
        lore.add(ChatColor.GRAY + "Attack Speed: " + attackSpeed);
        lore.add(ChatColor.GRAY + "Attack Range: " + attackRange);
        meta.setLore(lore);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey uuidKey = new NamespacedKey(plugin,
                WeaponAttributeType.UUID.getName());
        NamespacedKey physicDamageKey = new NamespacedKey(plugin,
                WeaponAttributeType.PHYSIC_DAMAGE.getName());
        NamespacedKey magicDamageKey = new NamespacedKey(plugin,
                WeaponAttributeType.MAGIC_DAMAGE.getName());
        NamespacedKey attackSpeedKey = new NamespacedKey(plugin,
                WeaponAttributeType.ATTACK_SPEED.getName());
        NamespacedKey attackRangeKey = new NamespacedKey(plugin,
                WeaponAttributeType.ATTACK_RANGE.getName());

        container.set(uuidKey, PersistentDataType.STRING, UUID.randomUUID().toString());
        container.set(physicDamageKey, PersistentDataType.DOUBLE, physicDamage);
        container.set(magicDamageKey, PersistentDataType.DOUBLE, magicDamage);
        container.set(attackSpeedKey, PersistentDataType.DOUBLE, attackSpeed);
        container.set(attackRangeKey, PersistentDataType.DOUBLE, attackRange);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isWeapon(ItemStack item) {
        if (item == null) return false;
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        for (WeaponType type : WeaponType.values()) {
            if (!container.has(type.getKey(), PersistentDataType.STRING)) continue;
            return true;
        }

        return false;
    }

    public static double getBaseAttackSpeed(ItemStack item) {
        switch (item.getType()) {
            case WOODEN_SWORD, STONE_SWORD, IRON_SWORD, GOLDEN_SWORD,
                 DIAMOND_SWORD, NETHERITE_SWORD -> { return 1.6; }
            case WOODEN_AXE -> { return 0.8; }
            case STONE_AXE -> { return 0.8; }
            case IRON_AXE -> { return 0.9; }
            case GOLDEN_AXE -> { return 1.0; }
            case DIAMOND_AXE -> { return 1.0; }
            case NETHERITE_AXE -> { return 1.0; }
            default -> { return 4.0; }
        }
    }
}

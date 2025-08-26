package org.chocon.shopPlugin.Listener.Shop.Support;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.chocon.shopPlugin.Code.ItemShopCode;
import org.chocon.shopPlugin.ShopPlugin;

public class ItemShopListener extends AbstractShopListener implements Listener {
    //==========================================Variable==========================================
    private final ShopPlugin plugin;
    public NamespacedKey key;

    //========================================Constructor=========================================
    public ItemShopListener(ShopPlugin plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "item_shop");
    }

    //===========================================Event============================================
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        ItemStack clicked = e.getCurrentItem();

        if (clicked == null || !clicked.hasItemMeta()) return;
        ItemMeta meta = clicked.getItemMeta();

        if (meta == null) {
            System.out.println("ERROR ItemShopListener: no item meta");
            return;
        }
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;

        e.setCancelled(true); // cancel taking item action
        String codeStr = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        ItemShopCode code = ItemShopCode.fromString(codeStr);

        if (code == null) return;

        switch (code) {
            // Open submenus
            case OPEN_WEAPON_MENU:
                createWeaponMenu(player);
                break;
            case OPEN_BLOCK_MENU:
                createBlockMenu(player);
                break;
            case OPEN_ARMOR_MENU:
                createAmorMenu(player);
                break;
            case OPEN_UTILITY_MENU:
                createUtilityMenu(player);
                break;

            // Back to main shop
            case BACK:
                createShopMenu(player);
                break;

            // Buy items
            case WOODEN_SWORD:
                buyItem(player, Material.WOODEN_SWORD, 10, Material.IRON_INGOT);
                break;
            case STONE_SWORD:
                buyItem(player, Material.STONE_SWORD, 20, Material.IRON_INGOT);
                break;
            case IRON_SWORD:
                buyItem(player, Material.IRON_SWORD, 4, Material.GOLD_INGOT);
                break;
        }
    }

    //============================================Menu============================================
    @Override
    public void createShopMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Item Shop");

        inv.setItem(10, createMenuItem(Material.IRON_SWORD, ChatColor.RED + "Weapons",
                ItemShopCode.OPEN_WEAPON_MENU.toString()));
        inv.setItem(12, createMenuItem(Material.BRICKS, ChatColor.YELLOW + "Blocks",
                ItemShopCode.OPEN_BLOCK_MENU.toString()));
        inv.setItem(14, createMenuItem(Material.IRON_CHESTPLATE, ChatColor.AQUA + "Armor",
                ItemShopCode.OPEN_ARMOR_MENU.toString()));
        inv.setItem(16, createMenuItem(Material.FIRE_CHARGE, ChatColor.GREEN + "Utilities",
                ItemShopCode.OPEN_UTILITY_MENU.toString()));

        player.openInventory(inv);
    }

    private void createWeaponMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Weapons");

        inv.setItem(10, createMenuItem(Material.WOODEN_SWORD, "Wood Sword - 10 Iron",
                ItemShopCode.WOODEN_SWORD.toString()));
        inv.setItem(12, createMenuItem(Material.STONE_SWORD, "Stone Sword - 20 Iron",
                ItemShopCode.STONE_SWORD.toString()));
        inv.setItem(14, createMenuItem(Material.IRON_SWORD, "Iron Sword - 4 Gold",
                ItemShopCode.IRON_SWORD.toString()));

        inv.setItem(26, createMenuItem(Material.ARROW, ChatColor.YELLOW + "Back",
                ItemShopCode.BACK.toString()));

        player.openInventory(inv);
    }

    private void createBlockMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Blocks");

        inv.setItem(10, createMenuItem(Material.WHITE_WOOL, "16x Wool - 4 Iron",
                ItemShopCode.WOOL.toString()));
        inv.setItem(12, createMenuItem(Material.OAK_PLANKS, "12x End Stone - 24 Iron",
                ItemShopCode.WOODEN_PLANK.toString()));
        inv.setItem(14, createMenuItem(Material.OBSIDIAN, "4x Obsidian - 4 Emerald",
                ItemShopCode.OBSIDIAN.toString()));

        inv.setItem(26, createMenuItem(Material.ARROW, ChatColor.YELLOW + "Back",
                ItemShopCode.BACK.toString()));

        player.openInventory(inv);
    }

    private void createAmorMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Armor");

        inv.setItem(10, createMenuItem(Material.CHAINMAIL_BOOTS, "Chainmail Armor - 40 Iron",
                ItemShopCode.CHAIN_AMOR.toString()));
        inv.setItem(12, createMenuItem(Material.IRON_BOOTS, "Iron Armor - 12 Gold",
                ItemShopCode.IRON_AMOR.toString()));
        inv.setItem(14, createMenuItem(Material.DIAMOND_BOOTS, "Diamond Armor - 6 Emerald",
                ItemShopCode.DIAMOND_AMOR.toString()));

        inv.setItem(26, createMenuItem(Material.ARROW, ChatColor.YELLOW + "Back",
                ItemShopCode.BACK.toString()));

        player.openInventory(inv);
    }

    private void createUtilityMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Utility");

        inv.setItem(10, createMenuItem(Material.FIRE_CHARGE, "Fireball - 40 Iron",
                ItemShopCode.FIRE_BALL.toString()));
        inv.setItem(12, createMenuItem(Material.ENDER_PEARL, "Ender Pearl - 4 Emerald",
                ItemShopCode.ENDER_PEARL.toString()));
        inv.setItem(14, createMenuItem(Material.TNT, "TNT - 8 Gold",
                ItemShopCode.TNT.toString()));
        inv.setItem(16, createMenuItem(Material.GOLDEN_APPLE, "Golden Apple - 3 Gold",
                ItemShopCode.GOLDEN_APPLE.toString()));
        inv.setItem(18, createMenuItem(Material.IRON_GOLEM_SPAWN_EGG, "Iron Golem Egg - 12 Gold",
                ItemShopCode.IRON_GOLEM_EGG.toString()));

        inv.setItem(26, createMenuItem(Material.ARROW, ChatColor.YELLOW + "Back",
                ItemShopCode.BACK.toString()));

        player.openInventory(inv);
    }

    //==========================================Support===========================================
    private ItemStack createMenuItem(Material mat, String name, String itemCodeStr) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            System.out.println("ERROR ItemShopListener: no item meta");
            return item;
        }
        meta.setDisplayName(name);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, itemCodeStr);
        item.setItemMeta(meta);
        return item;
    }

    private void buyItem(Player player, Material item, int cost, Material currency) {
        if (player.getInventory().containsAtLeast(new ItemStack(currency), cost)) {
            player.getInventory().removeItem(new ItemStack(currency, cost));
            player.getInventory().addItem(new ItemStack(item));
            player.sendMessage(ChatColor.GREEN + "Purchased " + item.name() + "!");
        } else {
            player.sendMessage(ChatColor.RED + "Not enough " + currency.name() + "!");
        }
    }
}

package org.chocon.shopPlugin.Listener.Shop.Model;

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
import org.chocon.shopPlugin.Code.IngredientCode;
import org.chocon.shopPlugin.Code.ItemShopCode;
import org.chocon.shopPlugin.Listener.Shop.Constructor.AbstractShopListener;
import org.chocon.shopPlugin.ShopPlugin;

public class ItemShopListener extends AbstractShopListener implements Listener {
    //==========================================Variable==========================================
    private final ShopPlugin plugin;
    public NamespacedKey key;

    //========================================Constructor=========================================
    public ItemShopListener(ShopPlugin plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "item shop");
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
        e.setCancelled(true);
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

            // Weapons
            case STONE_SWORD:
                buyItem(player, IngredientCode.IRON, 20, Material.STONE_SWORD);
                break;
            case IRON_SWORD:
                buyItem(player, IngredientCode.GOLD, 4, Material.IRON_SWORD);
                break;
            case DIAMOND_SWORD:
                buyItem(player, IngredientCode.EMERALD, 4, Material.DIAMOND_SWORD);

            // Blocks
            case WOOL:
                buyItem(player, IngredientCode.IRON, 16, 4, Material.WHITE_WOOL); // 16x Wool - 4 Iron
                break;
            case WOODEN_PLANK:
                buyItem(player, IngredientCode.IRON, 12, 24, Material.END_STONE); // 12x End Stone - 24 Iron
                break;
            case OBSIDIAN:
                buyItem(player, IngredientCode.EMERALD, 4, 4, Material.OBSIDIAN); // 4x Obsidian - 4 Emerald
                break;

            // Armors
            case CHAIN_AMOR:
                buyItem(player, IngredientCode.IRON, 40, Material.CHAINMAIL_BOOTS);
                buyItem(player, IngredientCode.IRON, 0, Material.CHAINMAIL_LEGGINGS);
                break;
            case IRON_AMOR:
                buyItem(player, IngredientCode.GOLD, 12, Material.IRON_BOOTS);
                buyItem(player, IngredientCode.GOLD, 0, Material.IRON_LEGGINGS);
                break;
            case DIAMOND_AMOR:
                buyItem(player, IngredientCode.EMERALD, 6, Material.DIAMOND_BOOTS);
                buyItem(player, IngredientCode.EMERALD, 0, Material.DIAMOND_LEGGINGS);
                break;

            // Utilities
            case FIRE_BALL:
                buyItem(player, IngredientCode.IRON, 40, Material.FIRE_CHARGE);
                break;
            case ENDER_PEARL:
                buyItem(player, IngredientCode.EMERALD, 4, Material.ENDER_PEARL);
                break;
            case TNT:
                buyItem(player, IngredientCode.GOLD, 8, Material.TNT);
                break;
            case GOLDEN_APPLE:
                buyItem(player, IngredientCode.GOLD, 3, Material.GOLDEN_APPLE);
                break;
            case IRON_GOLEM_EGG:
                buyItem(player, IngredientCode.GOLD, 120, Material.IRON_GOLEM_SPAWN_EGG);
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

        inv.setItem(10, createMenuItem(Material.STONE_SWORD, "Stone Sword - 20 Iron",
                ItemShopCode.STONE_SWORD.toString()));
        inv.setItem(12, createMenuItem(Material.IRON_SWORD, "Iron Sword - 4 Gold",
                ItemShopCode.IRON_SWORD.toString()));
        inv.setItem(14, createMenuItem(Material.WOODEN_SWORD, "Diamond Sword - 4 Emerald",
                ItemShopCode.DIAMOND_SWORD.toString()));

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

    private void buyItem(Player player, IngredientCode ingredient, int cost, Material item) {
        ItemStack reward = new ItemStack(item, 1);
        ItemStack payment = ingredient.getItem(1);

        if (player.getInventory().containsAtLeast(payment, cost)) {
            ItemStack toRemove = ingredient.getItem(cost);
            player.getInventory().removeItem(toRemove);
            player.getInventory().addItem(reward);

            player.sendMessage(ChatColor.GREEN + "Purchased " + ingredient.name() + "!");
        } else {
            player.sendMessage(ChatColor.RED + "Not enough " + ingredient.name() + "!");
        }
    }



    private void buyItem(Player player, IngredientCode ingredient, int amount,
                         int cost, Material item) {
        ItemStack reward = new ItemStack(item, amount);
        ItemStack payment = ingredient.getItem(1);

        if (player.getInventory().containsAtLeast(payment, cost)) {
            ItemStack toRemove = ingredient.getItem(cost);
            player.getInventory().removeItem(toRemove);
            player.getInventory().addItem(reward);

            player.sendMessage(ChatColor.GREEN + "Purchased " + amount + "x " + item.name() + "!");
        } else {
            player.sendMessage(ChatColor.RED + "Not enough " + ingredient.name() + "!");
        }
    }
}

package org.chocon.shopPlugin.Listener.Shop.Model;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.chocon.shopPlugin.Listener.Shop.Constructor.AbstractShopListener;
import org.chocon.shopPlugin.ShopPlugin;

public class UpgradeShopListener extends AbstractShopListener implements Listener {
    //==========================================Variable==========================================
    private final ShopPlugin plugin;
    public NamespacedKey key;

    //========================================Constructor=========================================
    public UpgradeShopListener(ShopPlugin plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "upgrade shop");
    }

    //===========================================Event============================================
    @Override
    public void onInventoryClick(InventoryClickEvent e) {

    }

    //============================================Menu============================================
    @Override
    public void createShopMenu(Player player) {

    }
}

package org.chocon.shopPlugin.Listener.Shop.Constructor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class AbstractShopListener implements Listener {
    public abstract void createShopMenu(Player player);
    @EventHandler
    public abstract void onInventoryClick(InventoryClickEvent e);
}

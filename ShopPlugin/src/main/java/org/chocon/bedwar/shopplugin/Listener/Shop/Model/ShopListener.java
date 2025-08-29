package org.chocon.bedwar.shopplugin.Listener.Shop.Model;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.chocon.bedwar.shopplugin.Code.ShopCode;
import org.chocon.bedwar.shopplugin.ShopPlugin;

public class ShopListener implements Listener {
    private final ItemShopListener itemShopListener;
    private final UpgradeShopListener upgradeShopListener;
    private final ShopPlugin plugin;
    public static NamespacedKey SHOP_KEY;
    public static NamespacedKey INGREDIENT_KEY;

    public ShopListener(ShopPlugin plugin, ItemShopListener itemShopListener,
                        UpgradeShopListener upgradeShopListener) {
        this.plugin = plugin;
        this.itemShopListener = itemShopListener;
        this.upgradeShopListener = upgradeShopListener;
        SHOP_KEY = new NamespacedKey(this.plugin, "shop");
        INGREDIENT_KEY = new NamespacedKey(this.plugin, "ingredient");
    }

    //===========================================Event============================================
    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();

        if (e.getRightClicked() instanceof Villager villager) {
            String code = villager.getPersistentDataContainer().get(SHOP_KEY, PersistentDataType.STRING);
            if (code == null) return;
            if (code.equals(ShopCode.ITEM.toString())) {
                this.itemShopListener.createShopMenu(player);
            } else if (code.equals(ShopCode.UPGRADE.toString())) {
                this.upgradeShopListener.createShopMenu(player);
            }

            e.setCancelled(true);
        }
    }
}

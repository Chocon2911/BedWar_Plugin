package org.chocon.bedwar.shopplugin.Command;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.persistence.PersistentDataType;
import org.chocon.bedwar.shopplugin.Code.ShopCode;
import org.chocon.bedwar.shopplugin.Listener.Shop.Model.ItemShopListener;
import org.chocon.bedwar.shopplugin.Listener.Shop.Model.UpgradeShopListener;
import org.chocon.bedwar.shopplugin.ShopPlugin;

public class ShopCommand implements CommandExecutor {
    //==========================================Variable==========================================
    private final ShopPlugin plugin;
    private final ItemShopListener itemShopListener;
    private final UpgradeShopListener upgradeShopListener;

    //========================================Constructor=========================================
    public ShopCommand(ShopPlugin plugin, ItemShopListener itemShopListener,
                       UpgradeShopListener upgradeShopListener) {
        this.plugin = plugin;
        this.itemShopListener = itemShopListener;
        this.upgradeShopListener = upgradeShopListener;
    }

    //==========================================Command===========================================
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shop")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }

            if (args.length != 2) {
                sender.sendMessage("Invalid Shop command! Need to be 'shop spawn <item|upgrade>'");
                return true;
            }

            if (!args[0].equals("spawn")) {
                sender.sendMessage("Invalid Shop command! Need to be 'shop spawn <item|upgrade>'");
            }

            switch (args[1].toLowerCase()) {
                case "item":
                    this.spawnShop(player, ShopCode.ITEM);
                    sender.sendMessage("Item shop spawned!");
                    break;
                case "upgrade":
                    this.spawnShop(player, ShopCode.UPGRADE);
                    sender.sendMessage("Upgrade shop spawned!");
                    break;
                default:
                    sender.sendMessage("Subcommand Invalid! Should be 'item_shop' or 'upgrade_shop'");
            }

            return true;
        }
        return false;
    }

    //=======================================Spawn Shop===========================================
    private void spawnShop(Player player, ShopCode code) {
        if (code == null) {
            System.out.println("ERROR ShopCommand: code is null");
            return;
        }
        Location loc = player.getLocation();
        Villager villager = (Villager) player.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        villager.setCustomName(code == ShopCode.ITEM ? "§aItem Shop" : "§bUpgrade Shop");
        villager.setCustomNameVisible(true);

        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setCollidable(false);
        villager.setSilent(true);
        villager.setProfession(Villager.Profession.NONE);

        NamespacedKey key = null;
        switch (code) {
            case ITEM:
                key = itemShopListener.key;
                break;
            case UPGRADE:
                key = upgradeShopListener.key;
                break;
            default:
                System.out.println("ERROR ShopCommand: code is invalid");
                break;
        }
        villager.getPersistentDataContainer().set(key, PersistentDataType.STRING, code.toString());
    }
}

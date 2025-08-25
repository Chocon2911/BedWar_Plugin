package org.chocon.itemPlugin.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.chocon.itemPlugin.Item.Weapon.Weapon;
import org.chocon.itemPlugin.Item.Weapon.WeaponType;
import org.chocon.itemPlugin.ItemPlugin;

public class GiveWeaponCommand implements CommandExecutor {
    private final ItemPlugin plugin;

    public GiveWeaponCommand(ItemPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only player can use this command.");
            return true;
        }

        Player player = (Player) sender;
        String weaponName = args[0];
        for (WeaponType type : WeaponType.values()) {
            if (!type.getName().equals(weaponName)) continue;
            ItemStack item = Weapon.create(this.plugin, type);
            player.getInventory().addItem(item);
            player.sendMessage("You have received " + type.getName() + "!");
            return true;
        }

        player.sendMessage("Invalid weapon name.");
        return true;
    }
}

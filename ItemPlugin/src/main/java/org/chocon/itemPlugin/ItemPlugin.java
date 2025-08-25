package org.chocon.itemPlugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.chocon.itemPlugin.Command.GiveWeaponCommand;
import org.chocon.itemPlugin.Listener.WeaponListener;

public final class ItemPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("giveWeapon").setExecutor(new GiveWeaponCommand(this));
        getServer().getPluginManager().registerEvents(new WeaponListener(this), this);

        System.out.println("ItemPlugin: Enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("ItemPlugin: Disabled!");
    }
}

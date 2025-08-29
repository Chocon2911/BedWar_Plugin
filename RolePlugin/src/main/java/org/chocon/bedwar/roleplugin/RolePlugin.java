package org.chocon.bedwar.roleplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.chocon.bedwar.roleplugin.Db.Model.RoleDb;

import java.sql.SQLException;

public final class RolePlugin extends JavaPlugin {

    //==========================================Variable==========================================
    private static RolePlugin instance;

    //==========================================get Set===========================================
    public static RolePlugin getInstance() { return instance; }

    //===========================================Spigot===========================================
    @Override
    public void onEnable() {
        try {
            RoleDb.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
        System.out.println("RolePlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RolePlugin disabled!");
    }
}

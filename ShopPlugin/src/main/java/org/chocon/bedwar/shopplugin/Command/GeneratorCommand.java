package org.chocon.bedwar.shopplugin.Command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.chocon.bedwar.shopplugin.Component.GeneratorConfigLoader;
import org.chocon.bedwar.shopplugin.Component.GeneratorStat;
import org.chocon.bedwar.shopplugin.Entity.GeneratorMachine;
import org.chocon.bedwar.shopplugin.ShopPlugin;

import java.util.List;
import java.util.Map;

public class GeneratorCommand implements CommandExecutor {
    //==========================================Variable==========================================
    private final ShopPlugin plugin;
    private final GeneratorConfigLoader generatorConfigLoader;

    //========================================Constructor=========================================
    public GeneratorCommand(ShopPlugin plugin, GeneratorConfigLoader generatorConfigLoader) {
        this.plugin = plugin;
        this.generatorConfigLoader = generatorConfigLoader;
    }

    //===========================================Method===========================================
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("Usage: /generator spawn <base|emerald|diamond> <level>");
            return true;
        }

        String sub = args[0].toLowerCase();
        if (!sub.equals("spawn")) {
            sender.sendMessage("Unknown subcommand. Use: spawn");
            return true;
        }

        String type = args[1].toLowerCase();
        int level;
        try {
            level = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Level must be a number!");
            return true;
        }

        switch (type) {
            case "base":
                spawnGenerator(player, "BASE", level);
                break;
            case "emerald":
                spawnGenerator(player, "EMERALD", level);
                break;
            case "diamond":
                spawnGenerator(player, "DIAMOND", level);
                break;
            default:
                sender.sendMessage("Invalid generator type! Use: base, emerald, diamond");
                return true;
        }

        sender.sendMessage("Spawned " + type + " generator at level " + level + "!");
        return true;
    }

    private void spawnGenerator(Player player, String type, int level) {
        Location loc = player.getLocation();

        // Load stats từ JSON
        Map<Integer, List<GeneratorStat>> stats = generatorConfigLoader.getStats(type);

        if (stats.isEmpty()) {
            player.sendMessage("§Found no config for generator type §e" + type);
            return;
        }

        if (!stats.containsKey(level)) {
            player.sendMessage("§cCan't find config for generator level §e" + level
                    + " §cfor generator type §e" + type);
            return;
        }

        GeneratorMachine machine = new GeneratorMachine(plugin, stats, loc, level);
        // Add to game room List<GeneratorMachine>
        player.sendMessage("§aSpawned §e" + type + " §a at level: §e" + level + " §aat position: ("
                + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")!");
    }
}

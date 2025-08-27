package org.chocon.shopPlugin.Entity;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.chocon.shopPlugin.Component.GeneratorStat;
import org.chocon.shopPlugin.ShopPlugin;

import java.util.List;
import java.util.Map;

public class GeneratorMachine {
    //==========================================Variable==========================================
    private final ShopPlugin plugin;
    private final Map<Integer, List<GeneratorStat>> levelStats;
    private final Location location;

    /*========================================Constructor=========================================*/
    public GeneratorMachine(ShopPlugin plugin, Map<Integer, List<GeneratorStat>> levelStats,
                            Location location) {
        this.plugin = plugin;
        this.levelStats = levelStats;
        this.location = location;
    }

    //===========================================Method===========================================
    public void generateAtLevel(int level) {
        if (!levelStats.containsKey(level)) return;

        List<GeneratorStat> stats = levelStats.get(level);
        for (GeneratorStat stat : stats) {
            if (stat.canGenerate()) {
                spawnIngredient(stat);
                stat.markAsGenerated();
            }
        }
    }

    private void spawnIngredient(GeneratorStat stat) {
        ItemStack item = stat.getIngredient().getItem(stat.getAmount());

        if (location == null) {
            System.out.println("ERROR GeneratorMachine: Location is null");
            return;
        }
        if (location.getWorld() == null) {
            System.out.println("ERROR GeneratorMachine: World is null");
            return;
        }
        location.getWorld().dropItemNaturally(location, item);
    }
}

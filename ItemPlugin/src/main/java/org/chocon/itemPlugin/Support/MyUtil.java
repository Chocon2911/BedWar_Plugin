package org.chocon.itemPlugin.Support;

import org.bukkit.ChatColor;

public class MyUtil {
    public static String rainbow(String txt) {
        ChatColor[] colors = {
                ChatColor.RED,
                ChatColor.GOLD,
                ChatColor.YELLOW,
                ChatColor.GREEN,
                ChatColor.AQUA,
                ChatColor.BLUE,
                ChatColor.LIGHT_PURPLE
        };

        StringBuilder sb = new StringBuilder();
        int colorIndex = 0;

        for (char c : txt.toCharArray()) {
            if (c != ' ') {
                sb.append(colors[colorIndex % colors.length]).append(c);
                colorIndex++;
            }
            else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}

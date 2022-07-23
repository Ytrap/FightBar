package io.github.ytrap.fightbar.utils;

import io.github.ytrap.fightbar.Main;
import org.bukkit.ChatColor;

public class Utils {
    static Main plugin = Main.getPlugin(Main.class);

    public Utils() {
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
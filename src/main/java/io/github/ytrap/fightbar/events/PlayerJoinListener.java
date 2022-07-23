package io.github.ytrap.fightbar.events;

import io.github.ytrap.fightbar.Main;
import io.github.ytrap.fightbar.utils.UpdateChecker;
import io.github.ytrap.fightbar.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private static Main plugin;

    public PlayerJoinListener(Main plugin) {
        PlayerJoinListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("fightbar.reload")) {
            (new UpdateChecker(plugin, 93909)).getVersion((version) -> {
                if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                    p.sendMessage(Utils.chat("&l[&c&lFight&6&lBar&f&l] &cThere is a new update available (" + plugin.getDescription().getVersion() + " → &l" + version + "&r&c) \n&9► &ehttps://www.spigotmc.org/resources/fightbar.93909/"));
                }

            });
        }

    }
}
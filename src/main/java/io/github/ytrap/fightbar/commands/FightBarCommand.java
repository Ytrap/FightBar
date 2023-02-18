package io.github.ytrap.fightbar.commands;

import io.github.ytrap.fightbar.Main;
import io.github.ytrap.fightbar.utils.UpdateChecker;
import io.github.ytrap.fightbar.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FightBarCommand implements CommandExecutor {
    private static Main plugin;

    public FightBarCommand(Main plugin) {
        FightBarCommand.plugin = plugin;
        plugin.getCommand("fightbar").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            (new UpdateChecker(plugin, 93909)).getVersion((version) -> {
                if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                    sender.sendMessage(Utils.chat("\n&7&m------------------- &c&lFight&6&lBar&7&m-------------------&r\n \n&eYour version&r : &f&l" + plugin.getDescription().getVersion() + "&a☑&r\n" + "&eAuthor&r : &9&lYtrap\n" + "&ePlugin&r : &6&lhttps://www.spigotmc.org/resources/fightbar.93909/\n \n" + "&r&7&m---------------------------------------------" + "&r\n "));
                } else {
                    sender.sendMessage(Utils.chat("\n&7&m------------------- &c&lFight&6&lBar&7&m-------------------&r\n \n&eYour version&r : &f&l" + plugin.getDescription().getVersion() + " &c☒ &aThere is a new update available : &r" + version + " \n" + "&eAuthor&r : &9&lYtrap\n" + "&ePlugin&r : &6&lhttps://www.spigotmc.org/resources/fightbar.93909/\n \n" + "&r&7&m---------------------------------------------" + "&r\n "));
                }

            });
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(Utils.chat("&l[&c&lFight&6&lBar&f&l] &a&lThe plugin has been reloaded!"));
        }

        return true;
    }
}
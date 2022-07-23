package io.github.ytrap.fightbar;


import io.github.ytrap.fightbar.events.EntityDamageByEntityListener;
import io.github.ytrap.fightbar.events.PlayerJoinListener;
import io.github.ytrap.fightbar.utils.UpdateChecker;
import io.github.ytrap.fightbar.commands.FightBarCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {
    Logger logger = this.getLogger();

    public void onEnable() {
        this.saveDefaultConfig();
        new FightBarCommand(this);
        new EntityDamageByEntityListener(this);
        new PlayerJoinListener(this);
        (new UpdateChecker(this, 93909)).getVersion((version) -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                this.logger.info("Plugin is up to date.");
            } else {
                this.logger.warning("There is a new update available (" + version + ") -> https://www.spigotmc.org/resources/fightbar.93909/");
            }

        });

        logger.info(" ");
        logger.info("  ______ _       _     _     ____");
        logger.info(" |  ____(_)     | |   | |   |  _ \\");
        logger.info(" | |__   _  __ _| |__ | |_  | |_) | __ _ _ __");
        logger.info(" |  __| | |/ _` | '_ \\| __| |  _ < / _` | '__|");
        logger.info(" | |    | | (_| | | | | |_  | |_) | (_| | |");
        logger.info(" |_|    |_|\\__, |_| |_|\\__| |____/ \\__,_|_");
        logger.info("            __/ |");
        logger.info("           |___/");
        logger.info(" ");
        logger.info("I'm ready to fight!");
        logger.info(" ");

    }

    public void onDisable() {
        logger.info("By, have a nice day!");
    }
}
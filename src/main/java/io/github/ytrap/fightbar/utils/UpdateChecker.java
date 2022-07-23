package io.github.ytrap.fightbar.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                Throwable var2 = null;
                Object var3 = null;

                try {
                    InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId)).openStream();

                    try {
                        Scanner scanner = new Scanner(inputStream);

                        try {
                            if (scanner.hasNext()) {
                                consumer.accept(scanner.next());
                            }
                        } finally {
                            if (scanner != null) {
                                scanner.close();
                            }

                        }
                    } catch (Throwable var19) {
                        if (var2 == null) {
                            var2 = var19;
                        } else if (var2 != var19) {
                            var2.addSuppressed(var19);
                        }

                        if (inputStream != null) {
                            inputStream.close();
                        }

                        throw var2;
                    }

                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Throwable var20) {
                    if (var2 == null) {
                        var2 = var20;
                    } else if (var2 != var20) {
                        var2.addSuppressed(var20);
                    }

                    throw var2;
                }
            } catch (Throwable var21) {
                this.plugin.getLogger().info("Cannot look for updates: " + var21.getMessage());
            }

        });
    }
}
package io.github.ytrap.fightbar.events;

import io.github.ytrap.fightbar.Main;
import io.github.ytrap.fightbar.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntityListener implements Listener {
    private static Main plugin;

    public EntityDamageByEntityListener(Main plugin) {
        EntityDamageByEntityListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    static boolean checkIfPlayerCan(EntityDamageByEntityEvent e) {
        LivingEntity damager;
        if (e.getDamager() instanceof Projectile) {
            if (((Projectile) e.getDamager()).getShooter() instanceof BlockProjectileSource) {
                return false;
            }

            ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
            damager = (LivingEntity) shooter;
        } else {
            if (!(e.getDamager() instanceof LivingEntity)) {
                return false;
            }

            damager = (LivingEntity) e.getDamager();
        }

        if (plugin.getConfig().getStringList("disabledWorlds").contains(e.getDamager().getWorld().getName())) {
            return false;
        } else if (damager instanceof Player && e.getEntity() instanceof LivingEntity) {
            if ((!plugin.getConfig().getString("enableOn").equalsIgnoreCase("PLAYERS") || e.getEntity().getType() == EntityType.PLAYER) && (!plugin.getConfig().getString("enableOn").equalsIgnoreCase("MOBS") || e.getEntity().getType() != EntityType.PLAYER)) {
                return !plugin.getConfig().getStringList("disabledEntities").contains(e.getEntity().getName());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (checkIfPlayerCan(e)) {
            Player damager;
            if (e.getDamager() instanceof Projectile) {
                ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
                damager = (Player) shooter;
            } else {
                damager = (Player) e.getDamager();
            }

            String damagerName = e.getDamager().getName();
            LivingEntity target = (LivingEntity) e.getEntity();
            String targetName = e.getEntity().getName();
            int intDamage = (int) e.getFinalDamage();
            double doubleDamage = e.getFinalDamage();
            int intMaxHealth = (int) target.getMaxHealth();
            double doubleMaxHealth = target.getMaxHealth();
            int intHealth = (int) (target.getHealth() - (double) intDamage);
            double doubleHealth = target.getHealth() - doubleDamage;
            String crit = "";
            if (damager.getFallDistance() > 0.0F && !(e.getDamager() instanceof Projectile)) {
                crit = plugin.getConfig().getString("critSymbole");
            }

            if (intHealth < 0) {
                intHealth = 0;
            }

            if (doubleHealth < 0.0) {
                doubleHealth = 0.0;
            }

            if (damager.getInventory().getItemInMainHand().getType() == Material.BOW || damager.getInventory().getItemInMainHand().getType() == Material.CROSSBOW) {
                double distance = Math.sqrt(Math.pow((target.getLocation().getX()-damager.getLocation().getX()), 2.0) + Math.pow((target.getLocation().getY()-damager.getLocation().getY()), 2.0) + Math.pow((target.getLocation().getZ()-damager.getLocation().getZ()), 2.0));
                damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.chat(plugin.getConfig().getString("pvpbarMessageProjectile").replaceAll("%DAMAGER_NAME%", damagerName).replaceAll("%TARGET_NAME%", targetName).replaceAll("%INT_DAMAGE%", String.valueOf(intDamage)).replaceAll("%DOUBLE_DAMAGE%", String.format("%.2f", doubleDamage)).replaceAll("%INT_MAX_HEALTH%", String.valueOf(intMaxHealth)).replaceAll("%DOUBLE_MAX_HEALTH%", String.format("%.2f", doubleMaxHealth)).replaceAll("%INT_HEALTH%", String.valueOf(intHealth)).replaceAll("%DOUBLE_HEALTH%", String.format("%.2f", doubleHealth)).replaceAll("%CRIT%", crit).replaceAll("%DISTANCE%", String.valueOf(Math.round(distance*100.0)/100.0)))));
            } else {
                damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.chat(plugin.getConfig().getString("pvpbarMessage").replaceAll("%DAMAGER_NAME%", damagerName).replaceAll("%TARGET_NAME%", targetName).replaceAll("%INT_DAMAGE%", String.valueOf(intDamage)).replaceAll("%DOUBLE_DAMAGE%", String.format("%.2f", doubleDamage)).replaceAll("%INT_MAX_HEALTH%", String.valueOf(intMaxHealth)).replaceAll("%DOUBLE_MAX_HEALTH%", String.format("%.2f", doubleMaxHealth)).replaceAll("%INT_HEALTH%", String.valueOf(intHealth)).replaceAll("%DOUBLE_HEALTH%", String.format("%.2f", doubleHealth)).replaceAll("%CRIT%", crit))));
            }

        }
    }

    public boolean isCritical(Player p) {
        return p.getVelocity().getY() + 0.0784000015258789 <= 0.0;
    }
}

package de.flo.flyplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class FlyPlugin extends JavaPlugin {

    public String prefix;
    private static FlyPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        config.setDefaults();
        prefix = config.getString("Allgemein.prefix");

        getLogger().info("Das FlyPlugin von EinfachFloTV wurde aktiviert.");

    }

    @Override
    public void onDisable() {
        getLogger().info("Das FlyPlugin von EinfachFloTV wurde deaktiviert.");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fly")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                    if (args.length > 1) {
                        player.sendMessage(prefix + ChatColor.RED + "Benutzung: /fly oder /fly <Spieler>");
                        return true;
                    }


                    if (args.length == 1) {
                        if (player.hasPermission("flysystem.fly.others")) {
                            Player target = Bukkit.getPlayer(args[0]);
                            if (target != null && target.isOnline()) {
                                if (target.getAllowFlight()) {
                                    target.setAllowFlight(false);
                                    target.sendMessage(prefix + ChatColor.RED + "Dir wurde der Flugmodus deaktiviert.");
                                    player.sendMessage(prefix + ChatColor.RED + "Dem Spieler " + target.getName() + " wurde Flugmodus entfernt!");
                                } else {
                                    target.setAllowFlight(true);
                                    target.sendMessage(prefix + ChatColor.GREEN + "Dir wurde der Flugmodus aktiviert.");
                                    player.sendMessage(prefix + ChatColor.GREEN + "Der Spieler " + target.getName() + " wurde in Flugmodus versetzt!");
                                }

                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "Spieler nicht gefunden oder offline.");
                            }
                            return true;
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl zu verwenden.");
                            return true;
                        }
                    } else {
                        if (player.hasPermission("flysystem.fly.self")) {
                            if (player.getAllowFlight()) {
                                player.setAllowFlight(false);
                                player.sendMessage(prefix + ChatColor.RED + "Du hast den Flugmodus deaktiviert.");
                            } else {
                                player.setAllowFlight(true);
                                player.sendMessage(prefix + ChatColor.GREEN + "Du hast den Flugmodus aktiviert.");
                            }
                            return true;
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl zu verwenden.");
                            return true;
                        }
                    }
                }
            } else if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(prefix + "Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
                return true;
            }

        if (command.getName().equalsIgnoreCase("flyspeed")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("flysystem.flyspeed.self")) {
                    if (args.length == 1) {
                        try {
                            float speed = Float.parseFloat(args[0]);

                            if (speed < 0 || speed > 10) {
                                player.sendMessage(prefix + ChatColor.RED + "Die Geschwindigkeit muss zwischen 0 und 10 liegen.");
                                return true;
                            }

                            player.setFlySpeed(speed / 10);
                            player.sendMessage(prefix + ChatColor.GREEN + "Deine Fluggeschwindigkeit wurde auf " + speed + " gesetzt.");
                        } catch (NumberFormatException e) {
                            player.sendMessage(prefix + ChatColor.RED + "Ungültige Geschwindigkeit: " + args[0]);
                        }
                    } else if (args.length == 2) {
                        if (player.hasPermission("flysystem.flyspeed.others")) {
                            Player target = Bukkit.getPlayer(args[0]);
                            if (target != null && target.isOnline()) {
                                try {
                                    float speed = Float.parseFloat(args[1]);

                                    if (speed < 0 || speed > 10) {
                                        player.sendMessage(prefix + ChatColor.RED + "Die Geschwindigkeit muss zwischen 0 und 10 liegen.");
                                        return true;
                                    }

                                    target.setFlySpeed(speed / 10);
                                    player.sendMessage(prefix + ChatColor.GREEN + "Fluggeschwindigkeit von " + target.getName() + " auf " + speed + " gesetzt.");
                                } catch (NumberFormatException e) {
                                    player.sendMessage(prefix + ChatColor.RED + "Ungültige Geschwindigkeit: " + args[1]);
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "Spieler nicht gefunden oder offline.");
                            }
                            return true;
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Du hast keine Berechtigung, die Fluggeschwindigkeit für andere Spieler einzustellen.");
                            return true;
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "Benutzung: /flyspeed <Geschwindigkeit> oder /flyspeed <Spieler> <Geschwindigkeit>");
                    }
                    return true;
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl zu verwenden.");
                    return true;
                }
            } else if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(prefix + "Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
                return true;
            }
        }

        return false;
    }

    public static FlyPlugin getInstance() {
        return instance;
    }
}
package fr.rainbowyoshi.aimskill;

import org.bukkit.*;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;

public class AimskillCreator {
    public static void CageCreation(Player p, String args) {
        int xp = (int) p.getLocation().getX();
        int yp = (int) p.getLocation().getY();
        int zp = (int) p.getLocation().getZ();
        World worldp = p.getLocation().getWorld();
        p.teleport(new Location(worldp, xp + 0.5, yp, zp + 0.5));
        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " L'arène de AimSkill a été créer.");
        p.getWorld().getBlockAt((int) (p.getLocation().getX() - 0.5), (int) (p.getLocation().getY() - 1), (int) (p.getLocation().getZ())-1).setType(Material.SEA_LANTERN);
        int X = (int) (p.getLocation().getX() - 1);
        int Y = (int) (p.getLocation().getY());
        int Z = (int) (p.getLocation().getZ() - 1);
        String world = p.getWorld().getName();
            if(!Maps.contains(args)){
                Main.getData().set("data." + args + ".x", X);
                Main.getData().set("data." + args + ".y", Y);
                Main.getData().set("data." + args + ".z", Z);
                Main.getData().set("data." + args + ".world", world);
                Main.getData().set("data." + args + ".name", args);
                Maps.add(args);
                MapsWorld.put(args, world);
                MapsX.put(args, X);
                MapsY.put(args, Y);
                MapsZ.put(args, Z);
                Main.saveData();
                for (int x = (int) p.getLocation().getX() + 3; x <= (int) p.getLocation().getX() + 3; x++) {
                    for (int y = (int) p.getLocation().getY(); y <= (int) p.getLocation().getY() + 2; y++) {
                        for (int z = (int) p.getLocation().getZ() - 3; z <= (int) p.getLocation().getZ() + 1; z++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.FENCE);
                        }
                    }
                }
                for (int x = (int) p.getLocation().getX() + 3; x <= (int) p.getLocation().getX() + 3; x++) {
                    for (int y = (int) p.getLocation().getY() + 3; y <= (int) p.getLocation().getY() + 3; y++) {
                        for (int z = (int) p.getLocation().getZ() - 3; z <= (int) p.getLocation().getZ() + 1; z++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.STEP);
                        }
                    }
                }

                for (int x = (int) p.getLocation().getX() - 5; x <= (int) p.getLocation().getX() - 5; x++) {
                    for (int y = (int) p.getLocation().getY(); y <= (int) p.getLocation().getY() + 2; y++) {
                        for (int z = (int) p.getLocation().getZ() - 3; z <= (int) p.getLocation().getZ() + 1; z++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.FENCE);
                        }
                    }
                }
                for (int x = (int) p.getLocation().getX() - 5; x <= (int) p.getLocation().getX() - 5; x++) {
                    for (int y = (int) p.getLocation().getY() + 3; y <= (int) p.getLocation().getY() + 3; y++) {
                        for (int z = (int) p.getLocation().getZ() - 3; z <= (int) p.getLocation().getZ() + 1; z++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.STEP);
                        }
                    }
                }

                for (int z = (int) p.getLocation().getZ() + 3; z <= (int) p.getLocation().getZ() + 3; z++) {
                    for (int y = (int) p.getLocation().getY(); y <= (int) p.getLocation().getY() + 2; y++) {
                        for (int x = (int) p.getLocation().getX() - 3; x <= (int) p.getLocation().getX() + 1; x++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.FENCE);
                        }
                    }
                }
                for (int z = (int) p.getLocation().getZ() + 3; z <= (int) p.getLocation().getZ() + 3; z++) {
                    for (int y = (int) p.getLocation().getY() + 3; y <= (int) p.getLocation().getY() + 3; y++) {
                        for (int x = (int) p.getLocation().getX() - 3; x <= (int) p.getLocation().getX() + 1; x++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.STEP);
                        }
                    }
                }

                for (int z = (int) p.getLocation().getZ() - 5; z <= (int) p.getLocation().getZ() - 5; z++) {
                    for (int y = (int) p.getLocation().getY(); y <= (int) p.getLocation().getY() + 2; y++) {
                        for (int x = (int) p.getLocation().getX() - 3; x <= (int) p.getLocation().getX() + 1; x++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.FENCE);
                        }
                    }
                }
                for (int z = (int) p.getLocation().getZ() - 5; z <= (int) p.getLocation().getZ() - 5; z++) {
                    for (int y = (int) p.getLocation().getY() + 3; y <= (int) p.getLocation().getY() + 3; y++) {
                        for (int x = (int) p.getLocation().getX() - 3; x <= (int) p.getLocation().getX() + 1; x++) {
                            p.getWorld().getBlockAt(x, y, z).setType(Material.STEP);
                        }
                    }
                }
                p.teleport(new Location(Bukkit.getWorld(MapsWorld.get(args)), MapsX.get(args) + 0.5, MapsY.get(args) + 0.5, MapsZ.get(args) + 0.5));
            }
    }

    public static void loadMaps(Location loc, String number){
        MapsX.put(number, (int) loc.getX());
        MapsY.put(number, (int) loc.getY());
        MapsZ.put(number, (int) loc.getZ());
        MapsWorld.put(number, loc.getWorld().getName());
        Maps.add(number);
    }

    public static ArrayList<String> Maps = new ArrayList<String>();
    public static HashMap<String, Integer> MapsX = new HashMap<String, Integer>();
    public static HashMap<String, Integer> MapsY = new HashMap<String, Integer>();
    public static HashMap<String, Integer> MapsZ = new HashMap<String, Integer>();
    public static HashMap<String, String> MapsWorld = new HashMap<String, String>();

}

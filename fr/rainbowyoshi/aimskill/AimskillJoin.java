package fr.rainbowyoshi.aimskill;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class AimskillJoin {

    public static HashMap<Player, Material> Block = new HashMap<>();

    public static void Start(Player p) {
        Random r = new Random();
        if (Main.data.getConfig().contains("spawn")) {
            if (AimskillCreator.Maps.size() > 0) {
                String var = AimskillCreator.Maps.get(r.nextInt(AimskillCreator.Maps.size()));
                p.teleport(new Location(Bukkit.getWorld(AimskillCreator.MapsWorld.get(var)), AimskillCreator.MapsX.get(var) + 0.5, AimskillCreator.MapsY.get(var), AimskillCreator.MapsZ.get(var) + 0.5));
                AimskillCreator.Maps.remove(var);
                PlaceBlock(p);
                StatusPlayer.OnParty.put(p, var);
            } else if (!Main.data.getConfig().contains("data")) {
                p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Veuillez créer une arène avec la commande /aimskill create.");
            } else {
                p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Aucunes parties disponibles pour le moment, veuillez patienter...");
            }
        } else
            p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Veuillez placer le spawn avant de commencer une partie.");
    }

    public static void JoinServer(Player p) {
        FileConfiguration file = Main.data.getConfig();
        if (file.contains("spawn")) {
            p.teleport(new Location(Bukkit.getWorld(String.valueOf(file.get("spawn.world"))), (Integer) file.get("spawn.x") - 0.5, (Integer) file.get("spawn.y"), (Integer) file.get("spawn.z") - 0.5));
        }
        Stats.setup();
        Stats.get().set("Statistiques." + p.getUniqueId() + ".Pseudo", p.getName());
        Stats.get().set("Statistiques." + p.getUniqueId() + ".Meilleur Score", CommandAS.Best.get(p));
        Stats.get().set("Statistiques." + p.getUniqueId() + ".Parties jouées", CommandAS.Parties.get(p));
        Stats.get().set("Statistiques." + p.getUniqueId() + ".Argent", CommandAS.Coins.get(p));
        Stats.save();
    }

    public static void PlaceBlock(Player p) {
        int random = Main.Random.get((int) (Math.random() * Main.Random.size()));
        if (random == 1) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 1)).setType(Block.get(p));
        } else if (random == 2) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ())).setType(Block.get(p));
        } else if (random == 3) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 1)).setType(Block.get(p));
        } else if (random == 4) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 2)).setType(Block.get(p));
        } else if (random == 5) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 2)).setType(Block.get(p));
        } else if (random == 6) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 1)).setType(Block.get(p));
        } else if (random == 7) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ())).setType(Block.get(p));
        } else if (random == 8) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 1)).setType(Block.get(p));
        } else if (random == 9) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 2)).setType(Block.get(p));
        } else if (random == 10) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 2)).setType(Block.get(p));
        } else if (random == 11) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 1)).setType(Block.get(p));
        } else if (random == 12) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ())).setType(Block.get(p));
        } else if (random == 13) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 1)).setType(Block.get(p));
        } else if (random == 14) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 2)).setType(Block.get(p));
        } else if (random == 15) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 2)).setType(Block.get(p));
        } else if (random == 16) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 1)).setType(Block.get(p));
        } else if (random == 17) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ())).setType(Block.get(p));
        } else if (random == 18) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 1)).setType(Block.get(p));
        } else if (random == 19) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 2)).setType(Block.get(p));
        } else if (random == 20) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 2)).setType(Block.get(p));
        } else if (random == 21) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 1)).setType(Block.get(p));
        } else if (random == 22) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ())).setType(Block.get(p));
        } else if (random == 23) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 1)).setType(Block.get(p));
        } else if (random == 24) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 2)).setType(Block.get(p));
        } else if (random == 25) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 2)).setType(Block.get(p));
        } else if (random == 26) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 1)).setType(Block.get(p));
        } else if (random == 27) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ())).setType(Block.get(p));
        } else if (random == 28) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 1)).setType(Block.get(p));
        } else if (random == 29) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 2)).setType(Block.get(p));
        } else if (random == 30) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 2)).setType(Block.get(p));
        } else if (random == 31) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 32) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 33) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 34) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 35) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 36) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 37) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 38) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 39) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 40) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 41) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 42) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 43) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 44) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 45) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Block.get(p));
        } else if (random == 46) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 47) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 48) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 49) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 50) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 51) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 52) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 53) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 54) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 55) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 56) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 57) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 58) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 59) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        } else if (random == 60) {
            Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Block.get(p));
        }
    }
}


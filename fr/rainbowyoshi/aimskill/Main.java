package fr.rainbowyoshi.aimskill;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;

public class Main extends JavaPlugin {
    public static Config data;
    public Title title = new Title();

    public static ArrayList<Integer> Random = new ArrayList<Integer>();

    public void onEnable(){
        Blocs.setup();
        if(!Blocs.get().contains("Blocs")){
            Blocs.get().set("Blocs.WOOL.Materiel", "WOOL");
            Blocs.get().set("Blocs.WOOL.Page", 1);
            Blocs.get().set("Blocs.WOOL.Slot", 0);
            Blocs.get().set("Blocs.WOOL.Nom", "Laine");
            Blocs.get().set("Blocs.WOOL.Catégorie", "&7Commun");
            Blocs.get().set("Blocs.WOOL.Type d'achat", "Gratuit");

            Blocs.get().set("Blocs.GLASS.Materiel", "GLASS");
            Blocs.get().set("Blocs.GLASS.Page", 1);
            Blocs.get().set("Blocs.GLASS.Slot", 1);
            Blocs.get().set("Blocs.GLASS.Nom", "Verre");
            Blocs.get().set("Blocs.GLASS.Catégorie", "&9Rare");
            Blocs.get().set("Blocs.GLASS.Type d'achat", "Argent");
            Blocs.get().set("Blocs.GLASS.Coût", "300");

            Blocs.get().set("Blocs.IRON_BLOCK.Materiel", "IRON_BLOCK");
            Blocs.get().set("Blocs.IRON_BLOCK.Page", 1);
            Blocs.get().set("Blocs.IRON_BLOCK.Slot", 3);
            Blocs.get().set("Blocs.IRON_BLOCK.Nom", "&7Fer");
            Blocs.get().set("Blocs.IRON_BLOCK.Catégorie", "&5Epique");
            Blocs.get().set("Blocs.IRON_BLOCK.Type d'achat", "Score");
            Blocs.get().set("Blocs.IRON_BLOCK.Score", "90");

            Blocs.get().set("Blocs.SEA_LANTERN.Materiel", "SEA_LANTERN");
            Blocs.get().set("Blocs.SEA_LANTERN.Page", 1);
            Blocs.get().set("Blocs.SEA_LANTERN.Slot", 2);
            Blocs.get().set("Blocs.SEA_LANTERN.Nom", "&bLanterne aquatique");
            Blocs.get().set("Blocs.SEA_LANTERN.Catégorie", "&9Rare");
            Blocs.get().set("Blocs.SEA_LANTERN.Type d'achat", "Parties");
            Blocs.get().set("Blocs.SEA_LANTERN.Nombre", "100");
            Blocs.save();
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (StatusPlayer.OnParty.get(p) != null) {
                        if (StatusPlayer.Chrono.get(p) > 0) {
                            if (StatusPlayer.Score.get(p) > 0) {
                                StatusPlayer.Chrono.put(p, StatusPlayer.Chrono.get(p) - 1);
                                title.sendActionBar(p, ChatColor.GREEN + "" + ChatColor.BOLD + "Temps restant: " + StatusPlayer.Chrono.get(p) + "s   Score: " + StatusPlayer.Score.get(p));
                            }
                        } else if(StatusPlayer.Chrono.get(p) <= 0){
                            if (StatusPlayer.Score.get(p) > 0) {
                                finish(p);
                                StatusPlayer.LastScore.put(p, StatusPlayer.Score.get(p));
                                if (CommandAS.Best.get(p) < StatusPlayer.LastScore.get(p)) {
                                    CommandAS.Best.put(p, StatusPlayer.LastScore.get(p));
                                }
                                CommandAS.Parties.put(p, CommandAS.Parties.get(p) + 1);
                                CommandAS.Coins.put(p, CommandAS.Coins.get(p) + (StatusPlayer.LastScore.get(p)/5));
                                p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Vous avez fait un score de " + StatusPlayer.Score.get(p) + ".");
                                StatusPlayer.Score.put(p, 0);
                                AimskillCreator.Maps.add(StatusPlayer.OnParty.get(p));
                                StatusPlayer.OnParty.put(p, null);
                                StatusPlayer.Chrono.put(p, 60);
                                AimskillJoin.JoinServer(p);

                            }
                        }
                    }
                }
            }
        }.runTaskTimer(this,20,20);
        data = new Config(this);
        getCommand("aimskill").setExecutor((CommandExecutor) new CommandAS());
        this.getServer().getPluginManager().registerEvents(new StatusPlayer(), this);
        this.getServer().getPluginManager().registerEvents(new ClickOnInventory(), this);
        RandomBlock();
    }

    public void onLoad(){
        data = new Config(this);
        for(Player p : Bukkit.getOnlinePlayers()){
            if(StatusPlayer.OnParty.get(p) != null){
                finish(p);
            }
            if (AimskillJoin.Block.get(p) == null) {
                Stats.setup();
                if(Stats.get().get("Statistiques."+p.getUniqueId()+".Block") == null) {
                    AimskillJoin.Block.put(p, Material.WOOL);
                    Stats.get().set("Statistiques."+p.getUniqueId()+".Pseudo", p.getName());
                    Stats.get().set("Statistiques."+p.getUniqueId()+".Block", AimskillJoin.Block.get(p).toString());
                    Stats.save();
                } else if(Stats.get().get("Statistiques."+p.getUniqueId()+".Block") != null) {
                    AimskillJoin.Block.put(p, Material.valueOf(Stats.get().getString("Statistiques."+p.getUniqueId()+".Block")));
                }
                if(Stats.get().get("Statistiques."+p.getUniqueId()+".Meilleur Score") != null) {
                    CommandAS.Best.put(p, Integer.valueOf(Stats.get().getString("Statistiques."+p.getUniqueId()+".Meilleur Score")));
                }
                if(Stats.get().get("Statistiques."+p.getUniqueId()+".Parties jouées") != null) {
                    CommandAS.Parties.put(p, Integer.valueOf(Stats.get().getString("Statistiques."+p.getUniqueId()+".Parties jouées")));
                }
                if(Stats.get().get("Statistiques."+p.getUniqueId()+".Argent") != null) {
                    CommandAS.Coins.put(p, Integer.valueOf(Stats.get().getString("Statistiques."+p.getUniqueId()+".Argent")));
                }
            }

            if (CommandAS.Best.get(p) == null) {
                CommandAS.Best.put(p, 0);
            }
            if (CommandAS.Parties.get(p) == null) {
                CommandAS.Parties.put(p, 0);
            }
            if (CommandAS.Coins.get(p) == null) {
                CommandAS.Coins.put(p, 0);
            }
            if (StatusPlayer.LastScore.get(p) == null) {
                StatusPlayer.LastScore.put(p, 0);
            }
            StatusPlayer.Chrono.put(p, 60);
            StatusPlayer.Score.put(p, 0);
            if (Main.data.getConfig().contains("spawn")) {
                if(Main.data.getConfig().get("spawn.tp on join").equals(true)) {
                    AimskillJoin.JoinServer(p);
                }
            }
        }
        if(data.getConfig().contains("data")) {
            loadMaps();
        }
        RandomBlock();
    }

    public void onDisable() {
        saveData();
    }

    public static FileConfiguration getData() {
        return data.getConfig();
    }
    public static void saveData(){
        data.saveConfig();
    }

    public void loadMaps(){
        FileConfiguration file = data.getConfig();
        data.getConfig().getConfigurationSection("data").getKeys(false).forEach(maps -> {
            Location loc = new Location(Bukkit.getWorld(file.getString("data." + maps + ".world")),
                    file.getDouble("data." + maps + ".x"), file.getDouble("data." + maps + ".y"), file.getDouble("data." + maps + ".z"));
            String number = file.getString("data." + maps + ".name");
            AimskillCreator.loadMaps(loc, number);
        });
    }

    public void RandomBlock(){
        Random.add(1); Random.add(2); Random.add(3); Random.add(4); Random.add(5); Random.add(6); Random.add(7); Random.add(8); Random.add(9); Random.add(10);
        Random.add(11); Random.add(12); Random.add(13); Random.add(14); Random.add(15); Random.add(16); Random.add(17); Random.add(18); Random.add(19); Random.add(20);
        Random.add(21); Random.add(22); Random.add(23); Random.add(24); Random.add(25); Random.add(26); Random.add(27); Random.add(28); Random.add(29); Random.add(30);
        Random.add(31); Random.add(32); Random.add(33); Random.add(34); Random.add(35); Random.add(36); Random.add(37); Random.add(38); Random.add(39); Random.add(40);
        Random.add(41); Random.add(42); Random.add(43); Random.add(44); Random.add(45); Random.add(46); Random.add(47); Random.add(48); Random.add(49); Random.add(50);
        Random.add(51); Random.add(52); Random.add(53); Random.add(54); Random.add(55); Random.add(56); Random.add(57); Random.add(58); Random.add(59); Random.add(60);
    }

    public static void finish(Player p){
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ())).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ())).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ())).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ())).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ())).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ())).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 1)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 4), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 2)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() + 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY(), (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 1, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() + 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX()), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 1), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 2), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);
        Bukkit.getServer().getWorld(p.getLocation().getWorld().getName()).getBlockAt((int) (p.getLocation().getX() - 3), (int) p.getLocation().getY() + 2, (int) (p.getLocation().getZ() - 3)).setType(Material.AIR);

    }
}

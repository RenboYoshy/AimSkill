package fr.rainbowyoshi.aimskill;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class StatusPlayer implements Listener {
    public static HashMap<Player, String> OnParty = new HashMap<Player, String>();
    public static HashMap<Player, Integer> Chrono = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> Score = new HashMap<Player, Integer>();
    public Title title = new Title();

    public static HashMap<Player, Integer> LastScore = new HashMap<Player, Integer>();

    @EventHandler
    public void OnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
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

    @EventHandler
    public void OnLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(OnParty.get(p) != null) {
            AimskillCreator.Maps.add(OnParty.get(p));
            if(AimskillCreator.Maps.size() == 2 && AimskillCreator.Maps.contains(-69)){
                AimskillCreator.Maps.remove(-69);
            }
            Main.finish(p);
            OnParty.put(p, null);
            StatusPlayer.Score.put(p, 0);
            StatusPlayer.Chrono.put(p, 60);
        }
        Stats.setup();
        Stats.get().set("Statistiques."+p.getUniqueId()+".Pseudo", p.getName());
        Stats.get().set("Statistiques."+p.getUniqueId()+".Meilleur Score", CommandAS.Best.get(p));
        Stats.get().set("Statistiques."+p.getUniqueId()+".Parties jouées", CommandAS.Parties.get(p));
        Stats.get().set("Statistiques."+p.getUniqueId()+".Argent", CommandAS.Coins.get(p));
        Stats.save();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (OnParty.get(p) != null) {
            if (e.getFrom().getZ() != e.getTo().getZ()) {
                p.teleport(new Location(Bukkit.getWorld(AimskillCreator.MapsWorld.get(OnParty.get(p))), AimskillCreator.MapsX.get(OnParty.get(p)) + 0.5, AimskillCreator.MapsY.get(OnParty.get(p)), AimskillCreator.MapsZ.get(OnParty.get(p)) + 0.5));
            } else if (e.getFrom().getX() != e.getTo().getX()) {
                p.teleport(new Location(Bukkit.getWorld(AimskillCreator.MapsWorld.get(OnParty.get(p))), AimskillCreator.MapsX.get(OnParty.get(p)) + 0.5, AimskillCreator.MapsY.get(OnParty.get(p)), AimskillCreator.MapsZ.get(OnParty.get(p)) + 0.5));
            } else if (e.getFrom().getY() != e.getTo().getY()) {
                p.teleport(new Location(Bukkit.getWorld(AimskillCreator.MapsWorld.get(OnParty.get(p))), AimskillCreator.MapsX.get(OnParty.get(p)) + 0.5, AimskillCreator.MapsY.get(OnParty.get(p)), AimskillCreator.MapsZ.get(OnParty.get(p)) + 0.5));
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (OnParty.get(p) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (OnParty.get(p) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnClickBlock(PlayerInteractEvent e){
        Action act = e.getAction();
        Player p = e.getPlayer();
        if(act == Action.LEFT_CLICK_BLOCK || act == Action.RIGHT_CLICK_BLOCK){
            Material m = e.getClickedBlock().getType();
            if(m == AimskillJoin.Block.get(p) && OnParty.get(p) != null){
                e.getClickedBlock().setType(Material.AIR);
                p.getWorld().spigot().playEffect(e.getClickedBlock().getLocation(), Effect.HAPPY_VILLAGER);
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.5F, 0.5F);
                AimskillJoin.PlaceBlock(p);
                StatusPlayer.Score.put(p, StatusPlayer.Score.get(p)+1);
                title.sendActionBar(p, ChatColor.GREEN + "" + ChatColor.BOLD + "Temps restant: " + StatusPlayer.Chrono.get(p) + "s   Score: " + StatusPlayer.Score.get(p));
            }
        }
    }
}

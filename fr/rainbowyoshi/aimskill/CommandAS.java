package fr.rainbowyoshi.aimskill;

import jdk.nashorn.internal.ir.Block;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandAS implements CommandExecutor {

    public static Config data;
    public static HashMap<Player, Integer> Parties = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> Coins = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> Best = new HashMap<Player, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equals("aimskill")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Liste des commandes :");
                    p.sendMessage(ChatColor.GREEN + "as help" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Permet d'afficher cette liste.");
                    p.sendMessage(ChatColor.GREEN + "as create" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Cet commande crée une arène de aimskill à l'emplacement du joueur.");
                    p.sendMessage(ChatColor.GREEN + "as join" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous fait rejoindre une partie.");
                    p.sendMessage(ChatColor.GREEN + "as leave" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous fait quitter la partie en cours.");
                    p.sendMessage(ChatColor.GREEN + "as spawn" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Permet de vous téléporter au spawn.");
                    p.sendMessage(ChatColor.GREEN + "as stats" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Permet de voir vos stats.");
                    p.sendMessage(ChatColor.GREEN + "as setspawn" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Place le spawn à voir position.");
                    p.sendMessage(ChatColor.GREEN + "as leaderboard" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous donne le top 10 des meilleurs joueurs.");
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        if (p.hasPermission("as.creator")) {
                            AimskillCreator.CageCreation(p, args[1]);
                        }
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("join")) {
                        if (StatusPlayer.OnParty.get(p) == null) {
                            AimskillJoin.Start(p);
                        } else
                            p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Veuillez quitter votre partie avant d'en relancer une autre.");
                    } else if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Liste des commandes :");
                        p.sendMessage(ChatColor.GREEN + "as help" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Permet d'afficher cette liste.");
                        p.sendMessage(ChatColor.GREEN + "as create {nom de l'arène}" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Cet commande crée une arène de aimskill à l'emplacement du joueur.");
                        p.sendMessage(ChatColor.GREEN + "as join" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous fait rejoindre une partie.");
                        p.sendMessage(ChatColor.GREEN + "as leave" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous fait quitter la partie en cours.");
                        p.sendMessage(ChatColor.GREEN + "as spawn" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Permet de vous téléporter au spawn.");
                        p.sendMessage(ChatColor.GREEN + "as stats" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Permet de voir vos stats.");
                        p.sendMessage(ChatColor.GREEN + "as setspawn" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Place le spawn à voir position.");
                        p.sendMessage(ChatColor.GREEN + "as leaderboard" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous donne le top 10 des meilleurs joueurs.");
                    } else if (args[0].equalsIgnoreCase("leave")) {
                        if (StatusPlayer.OnParty.get(p) != null) {
                            Main.finish(p);
                            StatusPlayer.Chrono.put(p, 60);
                            StatusPlayer.Score.put(p, 0);
                            AimskillCreator.Maps.add(StatusPlayer.OnParty.get(p));
                            StatusPlayer.OnParty.put(p, null);
                            AimskillJoin.JoinServer(p);
                        } else
                            p.sendMessage("AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous n'êtes pas dans une partie.");
                    } else if (args[0].equalsIgnoreCase("spawn")) {
                        AimskillJoin.JoinServer(p);
                    } else if (args[0].equalsIgnoreCase("stats")) {
                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vos statistiques Aimskill :");
                        p.sendMessage(ChatColor.GREEN + "Meilleur Score" + ChatColor.GRAY + " ► " + ChatColor.BLUE + Best.get(p));
                        p.sendMessage(ChatColor.GREEN + "Nombre de parties jouées" + ChatColor.GRAY + " ► " + ChatColor.BLUE + Parties.get(p));
                        p.sendMessage(ChatColor.GREEN + "Votre dernier score" + ChatColor.GRAY + " ► " + ChatColor.BLUE + StatusPlayer.LastScore.get(p));
                        p.sendMessage(ChatColor.GREEN + "Votre Block" + ChatColor.GRAY + " ► " + ChatColor.BLUE + AimskillJoin.Block.get(p));
                        p.sendMessage(ChatColor.GREEN + "Votre argent" + ChatColor.GRAY + " ► " + ChatColor.BLUE + Coins.get(p));
                    } else if (args[0].equalsIgnoreCase("setspawn")) {
                        if (p.hasPermission("as.setspawn")) {
                            int X = (int) p.getLocation().getX();
                            int Y = (int) p.getLocation().getY();
                            int Z = (int) p.getLocation().getZ();
                            String world = p.getWorld().getName();
                            Main.getData().set("spawn.x", X);
                            Main.getData().set("spawn.y", Y);
                            Main.getData().set("spawn.z", Z);
                            Main.getData().set("spawn.world", world);
                            Main.getData().set("spawn.tp on join", true);
                            Main.saveData();
                            p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Le spawn du aimskill a bien été placé.");
                        } else
                            p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Vous ne pouvez pas placer le spawn.");
                    } else if (args[0].equalsIgnoreCase("blocs")) {
                        Menu.PageMenu.put(p, 1);
                        Menu.Blocks(p);
                    } else if (args[0].equalsIgnoreCase("leaderboard")) {
                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Top 10 :");
                        ConfigurationSection cf = Stats.get().getConfigurationSection("Statistiques");
                        cf.getValues(false)
                                .entrySet()
                                .stream()
                                .sorted((a1, a2) -> {
                                    int points1 = ((MemorySection) a1.getValue()).getInt("Meilleur Score");
                                    int points2 = ((MemorySection) a2.getValue()).getInt("Meilleur Score");
                                    return points2 - points1;
                                })
                                .limit(10)
                                .forEach(f -> {
                                    int points = ((MemorySection) f.getValue()).getInt("Meilleur Score");
                                    String playername = ((MemorySection) f.getValue()).getString("Pseudo");
                                    p.sendMessage(ChatColor.GRAY + playername + ": " + points);

                                });
                    } else
                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Commande incorrecte, vous pouvez voir toutes les commandes avec la commande /as help");
                } else
                    p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Commande incorrecte, vous pouvez voir toutes les commandes avec la commande /as help");
            }
        }
        return false;
    }
}

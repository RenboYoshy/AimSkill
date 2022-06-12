package fr.rainbowyoshi.aimskill;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
public class Menu {

    public static ArrayList<Integer> Pages = new ArrayList<Integer>();
    public static HashMap<Player, Integer> PageMenu = new HashMap<Player, Integer>();
    public static void Blocks(Player p){
        Inventory Blocks = Bukkit.createInventory(p, 54, ChatColor.GREEN + "Page " + PageMenu.get(p));
        ConfigurationSection cf = Blocs.get().getConfigurationSection("Blocs");
        cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("Page");
                    int points2 = ((MemorySection) a2.getValue()).getInt("Page");
                    return points2 - points1;
                })
                .forEach(f -> {
                    int page = ((MemorySection) f.getValue()).getInt("Page");
                    int slot = ((MemorySection) f.getValue()).getInt("Slot");
                    Material materiel = Material.valueOf(((MemorySection) f.getValue()).getString("Materiel"));
                    String name = ((MemorySection) f.getValue()).getString("Nom").replace("&", "§");
                    String rareté = ((MemorySection) f.getValue()).getString("Catégorie").replace("&", "§");
                    String TypeAchat = ((MemorySection) f.getValue()).getString("Type d'achat").replace("&", "§");
                    Pages.add(page);

                    if(page == PageMenu.get(p)){
                        if(TypeAchat.equalsIgnoreCase("Gratuit")) {
                            Blocks.setItem(slot, new ItemBuilder(materiel, 1).setName(name).setLore(ChatColor.GREEN + "Catégorie : " + rareté).toItemStack());
                        } else if(TypeAchat.equalsIgnoreCase("Argent")){
                            String Coins = ((MemorySection) f.getValue()).getString("Coût").replace("&", "§");
                            Blocks.setItem(slot, new ItemBuilder(materiel, 1).setName(name).setLore(ChatColor.GREEN + "Catégorie : " + rareté, ChatColor.GREEN + "Type d'achat : " + ChatColor.BLUE + TypeAchat, ChatColor.GREEN + "Prix : " + ChatColor.BLUE + Coins).toItemStack());
                        } else if(TypeAchat.equalsIgnoreCase("Score")){
                            String Score = ((MemorySection) f.getValue()).getString("Score").replace("&", "§");
                            Blocks.setItem(slot, new ItemBuilder(materiel, 1).setName(name).setLore(ChatColor.GREEN + "Catégorie : " + rareté, ChatColor.GREEN + "Type d'achat : " + ChatColor.BLUE + TypeAchat, ChatColor.GREEN + "Score : " + ChatColor.BLUE + Score).toItemStack());
                        } else if(TypeAchat.equalsIgnoreCase("Parties")){
                            String Parties = ((MemorySection) f.getValue()).getString("Nombre").replace("&", "§");
                            Blocks.setItem(slot, new ItemBuilder(materiel, 1).setName(name).setLore(ChatColor.GREEN + "Catégorie : " + rareté, ChatColor.GREEN + "Type d'achat : " + ChatColor.BLUE + TypeAchat, ChatColor.GREEN + "Nombre de parties : " + ChatColor.BLUE + Parties).toItemStack());
                        }
                    }
                    if(!Pages.contains(PageMenu.get(p)+1)){
                        Blocks.setItem(51, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
                    } else if(Pages.contains(PageMenu.get(p)+1)){
                        Blocks.setItem(51, new ItemBuilder(Material.ARROW, 1).setName(ChatColor.GREEN + "Page Suivante").toItemStack());
                    }
                    if(!Pages.contains(PageMenu.get(p)-1)){
                        Blocks.setItem(47, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
                    } else if(Pages.contains(PageMenu.get(p)-1)){
                        Blocks.setItem(47, new ItemBuilder(Material.ARROW, 1).setName(ChatColor.GREEN + "Page Précédente").toItemStack());
                    }
                });
        Blocks.setItem(45, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        Blocks.setItem(46, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        Blocks.setItem(48, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        Blocks.setItem(49, new ItemBuilder(AimskillJoin.Block.get(p), 1).setName(ChatColor.BLUE + "Bloc sélectionné").toItemStack());
        Blocks.setItem(50, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        Blocks.setItem(52, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        Blocks.setItem(53, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        p.openInventory(Blocks);
    }
}

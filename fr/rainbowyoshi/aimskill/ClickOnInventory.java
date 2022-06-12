package fr.rainbowyoshi.aimskill;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ClickOnInventory implements Listener {
    public static HashMap<Player, Material> achat = new HashMap<>();
    public static HashMap<Player, Integer> prix= new HashMap<>();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        ItemStack itemstack = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
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
                    int slot = ((MemorySection) f.getValue()).getInt("Slot");
                    Material materiel = Material.valueOf(((MemorySection) f.getValue()).getString("Materiel"));
                    String name = ((MemorySection) f.getValue()).getString("Nom").replace("&", "§");
                    String rareté = ((MemorySection) f.getValue()).getString("Catégorie").replace("&", "§");
                    String TypeAchat = ((MemorySection) f.getValue()).getString("Type d'achat").replace("&", "§");
                    if(itemstack != null) {
                        if (inv.getName().equalsIgnoreCase(ChatColor.GREEN + "Page " + Menu.PageMenu.get(p))) {
                            if (itemstack.getType() == materiel && itemstack.getItemMeta().hasDisplayName() && itemstack.getItemMeta().getDisplayName().equalsIgnoreCase(name) && e.getSlot() == slot) {
                                if(TypeAchat.equalsIgnoreCase("Gratuit")) {
                                    AimskillJoin.Block.put(p, materiel);
                                    p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous avez séléctionné le bloc de " + name);
                                    p.closeInventory();
                                } else if(TypeAchat.equalsIgnoreCase("Argent")){
                                    Integer Coins = Integer.valueOf(((MemorySection) f.getValue()).getString("Coût").replace("&", "§"));
                                    Integer PlayerMoney = Integer.valueOf(String.valueOf(Stats.get().get("Statistiques."+p.getUniqueId()+".Argent")));
                                    if(Stats.get().get("Statistiques."+p.getUniqueId()+"."+materiel) != "oui"){
                                        if(Coins <= PlayerMoney){
                                            Inventory Blocks = Bukkit.createInventory(p, 9, ChatColor.GREEN + "Voulez-vous acheter le bloc de " + materiel + " ?");
                                            Blocks.setItem(2, new ItemBuilder(Material.WOOL, 1, (short) 5).setName(ChatColor.BLUE + "OUI").toItemStack());
                                            Blocks.setItem(6, new ItemBuilder(Material.WOOL, 1, (short) 14).setName(ChatColor.BLUE + "NON").toItemStack());
                                            ClickOnInventory.achat.put(p, materiel);
                                            ClickOnInventory.prix.put(p, Coins);
                                            p.openInventory(Blocks);
                                        } else {
                                            p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous ne pouvez pas acheter ce bloc.");
                                            p.closeInventory();
                                        }
                                    } else if(Stats.get().get("Statistiques."+p.getUniqueId()+"."+materiel) == "oui"){
                                        AimskillJoin.Block.put(p, materiel);
                                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous avez séléctionné le bloc de " + materiel);
                                        p.closeInventory();
                                    }
                                } else if(TypeAchat.equalsIgnoreCase("Score")){
                                    Integer Score = Integer.valueOf(((MemorySection) f.getValue()).getString("Score").replace("&", "§"));
                                    Integer PlayerScore = Integer.valueOf(String.valueOf(Stats.get().get("Statistiques."+p.getUniqueId()+".Meilleur Score")));
                                    if(Score <= PlayerScore) {
                                        AimskillJoin.Block.put(p, materiel);
                                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous avez séléctionné le bloc de " + name);
                                        p.closeInventory();
                                    } else {
                                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous n'avez pas atteint le score pour obtenir ce bloc.");
                                        p.closeInventory();
                                    }
                                } else if(TypeAchat.equalsIgnoreCase("Parties")) {
                                    Integer Parties = Integer.valueOf(((MemorySection) f.getValue()).getString("Nombre").replace("&", "§"));
                                    Integer PlayerParties = Integer.valueOf(String.valueOf(Stats.get().get("Statistiques." + p.getUniqueId() + ".Parties jouées")));
                                    if (Parties <= PlayerParties) {
                                        AimskillJoin.Block.put(p, materiel);
                                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous avez séléctionné le bloc de " + name);
                                        p.closeInventory();
                                    } else {
                                        p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous n'avez pas fait suffisamment de parties pour choisir ce bloc.");
                                        p.closeInventory();
                                    }
                                }
                            } else if (itemstack.getType() == Material.ARROW && itemstack.getItemMeta().hasDisplayName() && itemstack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Page Suivante") && e.getSlot() == 51) {
                                Menu.PageMenu.put(p, Menu.PageMenu.get(p)+1);
                                Menu.Blocks(p);
                            } else if (itemstack.getType() == Material.ARROW && itemstack.getItemMeta().hasDisplayName() && itemstack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Page Précédente") && e.getSlot() == 47) {
                                Menu.PageMenu.put(p, Menu.PageMenu.get(p)-1);
                                Menu.Blocks(p);
                            }
                        } if (inv.getName().equalsIgnoreCase(ChatColor.GREEN + "Voulez-vous acheter le bloc de " + ClickOnInventory.achat.get(p) + " ?")) {
                            if (itemstack.getType() == Material.WOOL && itemstack.getItemMeta().hasDisplayName() && itemstack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE + "OUI") && e.getSlot() == 2) {
                                CommandAS.Coins.put(p, (Integer) Stats.get().get("Statistiques." + p.getUniqueId() + ".Argent") - ClickOnInventory.prix.get(p));
                                Stats.get().set("Statistiques." + p.getUniqueId() + ".Argent", CommandAS.Coins.get(p));
                                AimskillJoin.Block.put(p, ClickOnInventory.achat.get(p));
                                p.sendMessage(ChatColor.GREEN + "AimSkill" + ChatColor.GRAY + " ► " + ChatColor.BLUE + "Vous avez séléctionné le bloc de " + ClickOnInventory.achat.get(p));
                                Stats.get().set("Statistiques."+p.getUniqueId()+"."+ClickOnInventory.achat.get(p), "oui");
                                Stats.save();
                                ClickOnInventory.achat.put(p, null);
                                ClickOnInventory.prix.put(p, null);
                                p.closeInventory();
                            } else if (itemstack.getType() == Material.WOOL && itemstack.getItemMeta().hasDisplayName() && itemstack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE + "NON") && e.getSlot() == 6) {
                                ClickOnInventory.achat.put(p, null);
                                ClickOnInventory.prix.put(p, null);
                                p.closeInventory();
                            }
                        }
                    } else e.setCancelled(true);
                });
    }
}


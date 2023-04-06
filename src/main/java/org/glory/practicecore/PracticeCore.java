package org.glory.practicecore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.glory.practicecore.Core.InventoryGUI;
import org.glory.practicecore.Core.KitManager;
import org.glory.practicecore.Core.PracticeItems;

import java.util.ArrayList;
import java.util.HashMap;

public final class PracticeCore extends JavaPlugin implements Listener {
    public static PracticeItems PracticeItems = new PracticeItems();
    public static InventoryGUI PracticeInventoryGUI = new InventoryGUI();
    public static KitManager PracticeKitManager = new KitManager();
    public static ArrayList<Player> UnrankedNodebuffQueue = new ArrayList<>();
    public static HashMap<Player, Player> UnrankedNodebuffMatches = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.broadcastMessage("§7[§aPracticeCore§7] §aSuccessfully loaded.");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> { // Scoreboard Handling
            for (Player player : Bukkit.getOnlinePlayers()){

                if (UnrankedNodebuffMatches.containsKey(player)){ // If player is in match show specific scoreboard of player they are against and match type.
                    ScoreboardManager SBManager = Bukkit.getScoreboardManager();

                    final Scoreboard scoreboard = SBManager.getNewScoreboard();

                    final Objective objective = scoreboard.registerNewObjective("test", "dummy");

                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    objective.setDisplayName("§a§lPRACTICE");

                    Score Score10 = objective.getScore("§7————————————————————————————————————");
                    Score10.setScore(10);

                    Score Score9 = objective.getScore("§fAgainst: §a" + UnrankedNodebuffMatches.get(player).getName());
                    Score9.setScore(9);

                    Score Score8 = objective.getScore("§fCurrently In: §aNodebuff Match");
                    Score8.setScore(8);

                    Score Score7 = objective.getScore("");
                    Score7.setScore(7);

                    Score Score6 = objective.getScore("§aeu.practice.club");
                    Score6.setScore(6);

                    Score Score5 = objective.getScore("§c§7————————————————————————————————————");
                    Score5.setScore(5);

                    player.setScoreboard(scoreboard);
                }
                else { // If player is not in match show default scoreboard.
                    ScoreboardManager SBManager = Bukkit.getScoreboardManager();

                    final Scoreboard scoreboard = SBManager.getNewScoreboard();

                    final Objective objective = scoreboard.registerNewObjective("test", "dummy");

                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    objective.setDisplayName("§a§lPRACTICE");

                    Score Score10 = objective.getScore("§7————————————————————————————————————");
                    Score10.setScore(10);

                    Score Score9 = objective.getScore("§fOnline: §a" + Bukkit.getOnlinePlayers().size());
                    Score9.setScore(9);

                    int CurrentRunningMatches = UnrankedNodebuffMatches.size(); // Plus any additional game-modes you may add.
                    Score Score8 = objective.getScore("§fPlaying: §a" + CurrentRunningMatches);
                    Score8.setScore(8);

                    Score Score7 = objective.getScore("");
                    Score7.setScore(7);

                    Score Score6 = objective.getScore("§aeu.practice.club");
                    Score6.setScore(6);

                    Score Score5 = objective.getScore("§c§7————————————————————————————————————");
                    Score5.setScore(5);

                    player.setScoreboard(scoreboard);
                }

            }
        },20, 20);
    }


    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PracticeItems.GiveQueueingItems(player); // On Player Join give Queuing Items.
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        assert event.getClickedInventory() != null;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getName().equals("§a§lUnranked Queue")) {
            // Below contains code of Adding a player to the queue and assigning players to matches straight after.
            if (event.getCurrentItem() != null){
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lNoDebuff")) {
                    player.closeInventory();
                    PracticeInventoryGUI.LeaveQueue(player);
                    if (!(UnrankedNodebuffQueue.contains(player))) {
                        UnrankedNodebuffQueue.add(player);
                        player.sendMessage("§aYou have joined the Unranked Queue for Nodebuff.");
                    } else
                        player.sendMessage("§aYou are already in the Unranked Queue for Nodebuff!");

                    if (UnrankedNodebuffQueue.size() >= 2) {
                        Player Player1 = UnrankedNodebuffQueue.get(0);
                        Player Player2 = UnrankedNodebuffQueue.get(1);
                        if (!(Player1.getUniqueId().equals(Player2.getUniqueId()))) {
                            UnrankedNodebuffQueue.remove(Player1);
                            UnrankedNodebuffQueue.remove(Player2);
                            Player1.sendMessage("§aStarting match against " + Player2.getDisplayName());
                            Player2.sendMessage("§aStarting match against " + Player1.getDisplayName());
                            PracticeKitManager.NodebuffKit(Player1);
                            PracticeKitManager.NodebuffKit(Player2);
                            UnrankedNodebuffMatches.put(Player1, Player2);
                            UnrankedNodebuffMatches.put(Player2, Player1);

                            // IMPLEMENT BASIC ARENA SYSTEM HERE //

                        }
                    }

                }
            }

            event.setCancelled(true);

            }
        }





    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) { // Pretty self-explanatory but in event of death while in duel, tell players who won and return to spawn.
        Player Player1 = event.getEntity().getPlayer();
        if (UnrankedNodebuffMatches.containsKey(Player1)){
            Player Player2 = UnrankedNodebuffMatches.get(Player1);
            Player1.sendMessage("§aYou have lost the match against " + Player2.getName() + "!");
            Player2.sendMessage("§aYou have won the match against " + Player1.getName() + "!");

            PracticeItems.GiveQueueingItems(Player1);
            PracticeItems.GiveQueueingItems(Player2);

            UnrankedNodebuffMatches.remove(Player1);
            UnrankedNodebuffMatches.remove(Player2);
        }
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event){
        PracticeItems.GiveQueueingItems(event.getPlayer());
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event){ // in event of logging while in duel, tell other player who won and return to spawn.
        Player Player1 = event.getPlayer();
        if (UnrankedNodebuffMatches.containsKey(Player1)){
            Player Player2 = UnrankedNodebuffMatches.get(Player1);
            Player2.sendMessage("§aYou have won the match against " + Player1.getName() + "!");

            PracticeItems.GiveQueueingItems(Player2);

            UnrankedNodebuffMatches.remove(Player1);
            UnrankedNodebuffMatches.remove(Player2);
        }
    }

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        Player player = event.getPlayer();
        if (item.getItemMeta().getDisplayName().equals("§aUnranked Queue §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
        else if (item.getItemMeta().getDisplayName().equals("§aRanked Queue §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
        else if (item.getItemMeta().getDisplayName().equals("§aEdit Kit §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
        else if (item.getItemMeta().getDisplayName().equals("§aCreate Party §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
        else if (item.getItemMeta().getDisplayName().equals("§aHost Events §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
        else if (item.getItemMeta().getDisplayName().equals("§aLeaderboards §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
        else if (item.getItemMeta().getDisplayName().equals("§aEdit Settings §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
        else if (item.getItemMeta().getDisplayName().equals("§cLeave Queue §7(Right Click)")){
            player.sendMessage("§cOops, you might not want to drop that!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerInteractionEvent(PlayerInteractEvent event) { // Handling opening the GUI(s), and interaction with Practice core items.
        Player player = event.getPlayer();
        if (event.getItem() != null){
            if (event.getItem().getItemMeta().getDisplayName().equals("§aUnranked Queue §7(Right Click)")){
                PracticeInventoryGUI.OpenUnrankedGUI(player);
            }
            else if (event.getItem().getItemMeta().getDisplayName().equals("§aRanked Queue §7(Right Click)")){
                player.sendMessage("§cOops, this feature is not yet added!");
            }
            else if (event.getItem().getItemMeta().getDisplayName().equals("§aEdit Kit §7(Right Click)")){
                player.sendMessage("§cOops, this feature is not yet added!");
            }
            else if (event.getItem().getItemMeta().getDisplayName().equals("§aCreate Party §7(Right Click)")){
                player.sendMessage("§cOops, this feature is not yet added!");
            }
            else if (event.getItem().getItemMeta().getDisplayName().equals("§aHost Events §7(Right Click)")){
                player.sendMessage("§cOops, this feature is not yet added!");
            }
            else if (event.getItem().getItemMeta().getDisplayName().equals("§aLeaderboards §7(Right Click)")){
                player.sendMessage("§cOops, this feature is not yet added!");
            }
            else if (event.getItem().getItemMeta().getDisplayName().equals("§aEdit Settings §7(Right Click)")){
                player.sendMessage("§cOops, this feature is not yet added!");
            }
            else if (event.getItem().getItemMeta().getDisplayName().equals("§cLeave Queue §7(Right Click)")){
                UnrankedNodebuffQueue.remove(player);
                player.sendMessage("§aYou have left the queue!");
                PracticeItems.GiveQueueingItems(player); // Give Queueing Items
            }
        }

    }
}

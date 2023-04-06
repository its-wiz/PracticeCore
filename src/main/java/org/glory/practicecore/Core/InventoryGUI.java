package org.glory.practicecore.Core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.glory.practicecore.PracticeCore;

import java.util.Arrays;

public class InventoryGUI {
    public void OpenUnrankedGUI(Player player){ // Set up Queueing gui.
        Inventory inventory = Bukkit.createInventory(null, 9*3, "§a§lUnranked Queue");

        Potion splashPotion = new Potion(PotionType.INSTANT_HEAL, 2);//poison 1
        splashPotion.setSplash(true);

        ItemStack potion = splashPotion.toItemStack(1);
        ItemMeta meta = potion.getItemMeta();
        meta.setDisplayName("§a§lNoDebuff");
        int CurrentQueueingPlayers = PracticeCore.UnrankedNodebuffQueue.size();
        int CurrentRunningMatches = PracticeCore.UnrankedNodebuffMatches.size();
        meta.setLore(Arrays.asList("§aPlaying: §f" + CurrentRunningMatches, "§aIn Queue: §f" + CurrentQueueingPlayers, "§a", "§aClick to play"));
        potion.setItemMeta(meta);

        inventory.setItem(0, potion);

        player.openInventory(inventory);
    }

    public void LeaveQueue(Player player){
        player.getInventory().clear();
        ItemStack LeaveQueue = new ItemStack(Material.REDSTONE);
        ItemMeta LeaveQueueMeta = LeaveQueue.getItemMeta();
        LeaveQueueMeta.setDisplayName("§cLeave Queue §7(Right Click)");
        LeaveQueue.setItemMeta(LeaveQueueMeta);
        player.getInventory().setItem(8, LeaveQueue);
    }
}

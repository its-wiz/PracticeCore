package org.glory.practicecore.Setup;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoin {

    public void HandleJoin(Player player){


        player.getInventory().clear();
        ItemStack Unranked = new ItemStack(Material.IRON_SWORD);
        ItemMeta UnrankedMeta = Unranked.getItemMeta();
        UnrankedMeta.setDisplayName("§aUnranked Queue §7(Right Click)");
        Unranked.setItemMeta(UnrankedMeta);

        ItemStack Ranked = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta RankedMeta = Ranked.getItemMeta();
        RankedMeta.setDisplayName("§aRanked Queue  §7(Right Click)");
        Ranked.setItemMeta(RankedMeta);

        ItemStack EditKit = new ItemStack(Material.BOOK);
        ItemMeta EditKitMeta = EditKit.getItemMeta();
        EditKitMeta.setDisplayName("§aEdit Kit §7(Right Click)");
        EditKit.setItemMeta(EditKitMeta);

        ItemStack CreateParty = new ItemStack(Material.NAME_TAG);
        ItemMeta CreatePartyMeta = CreateParty.getItemMeta();
        CreatePartyMeta.setDisplayName("§aCreate Party §7(Right Click)");
        CreateParty.setItemMeta(CreatePartyMeta);

        ItemStack HostEvents = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta HostEventsMeta = HostEvents.getItemMeta();
        HostEventsMeta.setDisplayName("§aHost Events §7(Right Click)");
        HostEvents.setItemMeta(HostEventsMeta);

        ItemStack Leaderboards = new ItemStack(Material.EMERALD);
        ItemMeta LeaderboardsMeta = Leaderboards.getItemMeta();
        LeaderboardsMeta.setDisplayName("§aLeaderboards §7(Right Click)");
        Leaderboards.setItemMeta(LeaderboardsMeta);

        ItemStack Settings = new ItemStack(Material.SKULL_ITEM);
        ItemMeta SettingsMeta = Settings.getItemMeta();
        SettingsMeta.setDisplayName("§aEdit Settings §7(Right Click)");
        Settings.setItemMeta(SettingsMeta);

        player.getInventory().setItem(0, Unranked);
        player.getInventory().setItem(1, Ranked);
        player.getInventory().setItem(2, EditKit);
        player.getInventory().setItem(4, CreateParty);
        player.getInventory().setItem(6, HostEvents);
        player.getInventory().setItem(7, Leaderboards);
        player.getInventory().setItem(8, Settings);
    }
}

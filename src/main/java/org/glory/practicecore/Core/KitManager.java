package org.glory.practicecore.Core;

import org.bukkit.entity.Player;

public class KitManager {

    public void NodebuffKit(Player player){
        player.getInventory().clear();
        player.sendMessage("§aYou have received the Nodebuff Kit.");
        // Add Code to give player Nodebuff Kit.
    }
}

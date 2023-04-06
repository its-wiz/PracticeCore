package org.glory.practicecore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.glory.practicecore.Setup.PlayerJoin;

import java.util.Objects;

public final class PracticeCore extends JavaPlugin implements Listener {
    PlayerJoin PracticePlayerJoin = new PlayerJoin();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.broadcastMessage("§7[§aPracticeCore§7] §aSuccessfully loaded.");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()){
                ScoreboardManager SBManager = Bukkit.getScoreboardManager();

                final Scoreboard scoreboard = SBManager.getNewScoreboard();

                final Objective objective = scoreboard.registerNewObjective("test", "dummy");

                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                objective.setDisplayName("§a§lPRACTICE");

                Score Score10 = objective.getScore("§7————————————————————————————————————");
                Score10.setScore(10);

                Score Score9 = objective.getScore("§fOnline: §a" + Bukkit.getOnlinePlayers().size());
                Score9.setScore(9);

                Score Score8 = objective.getScore("§fPlaying: §a0");
                Score8.setScore(8);

                Score Score7 = objective.getScore("");
                Score7.setScore(7);

                Score Score6 = objective.getScore("§aeu.practice.club");
                Score6.setScore(6);

                Score Score5 = objective.getScore("§c§7————————————————————————————————————");
                Score5.setScore(5);

                player.setScoreboard(scoreboard);
            }
        },20, 20);
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PracticePlayerJoin.HandleJoin(player);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Objects.equals(command.getName(), "practicesetspawn")){
            Bukkit.broadcastMessage("Here!");
        }
        return false;
    }
}

package birds.manhuntclone.ManhuntClone.commands;

import birds.manhuntclone.ManhuntClone.modes.PlayerTracker;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UntrackCommand implements CommandExecutor {
    private PlayerTracker playerTracker;

    public UntrackCommand(PlayerTracker playerTracker) {
        this.playerTracker = playerTracker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return false;
        }

        Player player = (Player) sender;

        if (!playerTracker.getTrackers().contains(player)) {
            player.sendMessage(ChatColor.RED + "Only trackers can use this command!");
            return false;
        }

        // check if is already tracking
        if (playerTracker.isTracking(player)) {
            playerTracker.getTrackedPlayer(player).spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("You are no longer being tracked by " + player.getName() + ".").color(net.md_5.bungee.api.ChatColor.GOLD).create());
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("You are no longer tracking " + playerTracker.getTrackedPlayer(player).getName() + ".").color(net.md_5.bungee.api.ChatColor.GOLD).create());


            playerTracker.removeTracker(player);
            playerTracker.setTracking(player, false);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("No longer tracking.").color(net.md_5.bungee.api.ChatColor.GREEN).create());
        } else {
            player.sendMessage(ChatColor.RED + "Nobody is being tracked.");
        }
        return true;

    }
}

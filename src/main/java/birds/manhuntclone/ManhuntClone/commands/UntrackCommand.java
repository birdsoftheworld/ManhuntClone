package birds.manhuntclone.ManhuntClone.commands;

import birds.manhuntclone.ManhuntClone.modes.PlayerTracker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UntrackCommand implements CommandExecutor {
    private PlayerTracker playerTracker;

    public UntrackCommand(PlayerTracker playerTracker) {
        this.playerTracker = playerTracker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        // check if is already tracking
        if (playerTracker.isTracking()) {
            playerTracker.getTracked().sendMessage(ChatColor.GOLD.toString() + "You are no longer being tracked by " + playerTracker.getTracker().getName() + ".");
            playerTracker.getTracker().sendMessage(ChatColor.GOLD.toString() + "You are no longer tracking " + playerTracker.getTracked().getName() + ".");

            playerTracker.setTracker(null);
            playerTracker.setTracked(null);
            playerTracker.setTracking(false);

            sender.sendMessage(ChatColor.GREEN.toString() + "No longer tracking.");
        } else {
            sender.sendMessage("Nobody is being tracked.");
        }
        return true;

    }
}

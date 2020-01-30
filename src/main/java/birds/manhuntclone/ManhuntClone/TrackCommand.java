package birds.manhuntclone.ManhuntClone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrackCommand implements CommandExecutor {
    private PlayerTracker playerTracker;

    TrackCommand(PlayerTracker playerTracker) {
        this.playerTracker = playerTracker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        // make sure there are enough args
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED.toString() + "Not enough arguments!");
            return false;
        }

        // make sure sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED.toString() + "Sender must be a player.");
            return false;
        }

        Player selectedPlayer = Bukkit.getServer().getPlayer(args[0]);

        // make sure player is online
        if (selectedPlayer == null) {
            sender.sendMessage(ChatColor.RED.toString() + "That player is not online.");
            return false;
        }

        playerTracker.setTracker((Player) sender);
        playerTracker.setTracked(selectedPlayer);
        playerTracker.setTracking(true);

        sender.sendMessage(ChatColor.GREEN.toString() + "Now tracking " + selectedPlayer.getName() + ".");
        selectedPlayer.sendMessage(ChatColor.GOLD.toString() + "You are now being tracked by " + sender.getName() + ".");

        // give the tracker a compass
        ((Player) sender).getInventory().addItem(new ItemStack(Material.COMPASS, 1));

        return true;
    }
}

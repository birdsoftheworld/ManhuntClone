package birds.manhuntclone.ManhuntClone.commands;

import birds.manhuntclone.ManhuntClone.modes.PlayerTracker;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
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

    public TrackCommand(PlayerTracker playerTracker) {
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

        Player playerSender = (Player) sender;

        // make sure player is online
        if (selectedPlayer == null) {
            playerSender.sendMessage(ChatColor.RED.toString() + "That player is not online.");
            return false;
        }

        playerTracker.trackPlayer(playerSender, selectedPlayer);

        // give the tracker a compass
        playerTracker.givePlayerCompass(playerSender);

        return true;
    }
}

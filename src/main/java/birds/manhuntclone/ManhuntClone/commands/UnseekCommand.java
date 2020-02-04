package birds.manhuntclone.ManhuntClone.commands;

import birds.manhuntclone.ManhuntClone.ManhuntClone;
import birds.manhuntclone.ManhuntClone.modes.RaytraceCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnseekCommand extends RaytraceCommand implements CommandExecutor {

    public UnseekCommand(ManhuntClone manhuntClone) {
        super(manhuntClone);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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

        final Player selectedPlayer = Bukkit.getServer().getPlayer(args[0]);

        // make sure player is online
        if (selectedPlayer == null) {
            sender.sendMessage(ChatColor.RED.toString() + "That player is not online.");
            return false;
        }

        if (!((Player) sender).getDisplayName().equals(getSeeker().getDisplayName())) {
            sender.sendMessage(ChatColor.RED.toString() + "Sender must be the seeker!" + ChatColor.RESET.toString());
            return false;
        }

        if (!selectedPlayer.getDisplayName().equals(getHider().getDisplayName())) {
            sender.sendMessage(ChatColor.RED.toString() + "Player must be the hider!" + ChatColor.RESET.toString());
            return false;
        }

        stopRunnable();

        setHider(null);
        setSeeker(null);
        setSeeking(false);

        getSeeker().sendMessage(ChatColor.GREEN.toString() + "You have stopped seeking " + getHider().getDisplayName() + ChatColor.RESET.toString());
        getHider().sendMessage(ChatColor.GREEN.toString() + getSeeker().getDisplayName() + " has stopped seeking you" + ChatColor.RESET.toString());

        return true;
    }
}

package birds.manhuntclone.ManhuntClone.commands;

import birds.manhuntclone.ManhuntClone.ManhuntClone;
import birds.manhuntclone.ManhuntClone.modes.DeathSwap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathSwapCommand implements CommandExecutor {

    private ManhuntClone manhuntClone;
    private DeathSwap deathSwap;

    public DeathSwapCommand(ManhuntClone manhuntClone) {
        //this.playerTracker = playerTracker;
        this.manhuntClone = manhuntClone;
        deathSwap = new DeathSwap(manhuntClone);
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

        int interval;

        Bukkit.getLogger().info(command.getName());

        if (command.getName().equals("swap")) {
            interval = Integer.parseInt(args[0]) * 20;
            deathSwap.setRunInterval(interval);
            deathSwap.setInterval(1);
            deathSwap.startInterval();
        } else if (command.getName().equals("unswap")) {
            deathSwap.stopInterval();
        }

        return true;
    }
}

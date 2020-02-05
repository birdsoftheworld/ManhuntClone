package birds.manhuntclone.ManhuntClone.commands;

import birds.manhuntclone.ManhuntClone.ManhuntClone;
import birds.manhuntclone.ManhuntClone.modes.RaytraceCommand;
import birds.manhuntclone.ManhuntClone.util.SeekData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

public class SeekCommand extends RaytraceCommand implements CommandExecutor {

    private ManhuntClone manhuntClone;
    private BukkitRunnable raytraceRunner;
    private SeekData seekData;

    public SeekCommand(ManhuntClone manhuntClone, SeekData seekData) {
        super(manhuntClone, seekData);
        this.manhuntClone = manhuntClone;
        this.seekData = seekData;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command command, String s, String[] args) {

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

        sender.sendMessage(ChatColor.GREEN.toString() + "You are now seeking " + selectedPlayer.getDisplayName() + ChatColor.RESET.toString());
        selectedPlayer.sendMessage(ChatColor.RED.toString() + ((Player) sender).getDisplayName() + " is now seeking you" + ChatColor.RESET.toString());

        seekData.setSeeker((Player) sender);
        seekData.setHider(selectedPlayer);
        seekData.setSeeking(true);

        startRunnable();

        return true;
    }
}

package birds.manhuntclone.ManhuntClone.modes;

import birds.manhuntclone.ManhuntClone.ManhuntClone;
import birds.manhuntclone.ManhuntClone.util.SeekData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.Objects;

public class RaytraceCommand {

    private ManhuntClone manhuntClone;
    private BukkitRunnable runnable;
    private SeekData seekData;

    public RaytraceCommand(ManhuntClone manhuntClone, SeekData seekData) {
        this.manhuntClone = manhuntClone;
        runnable = null;
        this.seekData = seekData;
    }

    private BukkitRunnable getRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                Player seeker = seekData.getSeeker();
                Player hider = seekData.getHider();
                if (seeker == null || hider == null) return;
                RayTraceResult result = hider.getBoundingBox().rayTrace(Objects.requireNonNull(seeker.getPlayer()).getEyeLocation().toVector(), seeker.getPlayer().getEyeLocation().getDirection(), 100);
                if (result == null) return;
                seeker.sendMessage(ChatColor.GOLD.toString() + "You have spotted " + hider.getDisplayName() + ChatColor.RESET.toString());
                hider.sendMessage(ChatColor.RED.toString() + "You have been spotted by " + seeker.getPlayer().getDisplayName() + ChatColor.RESET.toString());
                Bukkit.getLogger().info(String.valueOf(runnable == null));
            }
        };
    }

    public void startRunnable() {
        runnable = getRunnable();
        runnable.runTaskTimer(manhuntClone, 0, 10);
    }

    public void stopRunnable() {
        Bukkit.getLogger().info("STOPPING RUNNABLE!");
        Bukkit.getLogger().info(String.valueOf(runnable == null));
        if (seekData.isSeeking() && runnable != null && !runnable.isCancelled()) {
            runnable.cancel();
            runnable = null;
            Bukkit.getLogger().info("STOPPED RUNNABLE!");
        }
    }
}

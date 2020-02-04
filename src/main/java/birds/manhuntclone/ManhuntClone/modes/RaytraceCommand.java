package birds.manhuntclone.ManhuntClone.modes;

import birds.manhuntclone.ManhuntClone.ManhuntClone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

public class RaytraceCommand {

    private ManhuntClone manhuntClone;
    private BukkitRunnable runnable;
    private Player seeker;
    private Player hider;
    private boolean seeking;

    public RaytraceCommand(ManhuntClone manhuntClone) {
        this.manhuntClone = manhuntClone;
        runnable = null;
    }

    public void setHider(Player hider) {
        this.hider = hider;
    }

    public void setSeeker(Player seeker) {
        this.seeker = seeker;
    }

    public void setSeeking(boolean seeking) {
        this.seeking = seeking;
    }

    public Player getSeeker() {
        return seeker;
    }

    public Player getHider() {
        return hider;
    }

    public boolean getSeeking() {
        return seeking;
    }

    private BukkitRunnable getRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (seeker == null || hider == null) return;
                RayTraceResult result = hider.getBoundingBox().rayTrace(((Player) seeker).getPlayer().getEyeLocation().toVector(), ((Player) seeker).getPlayer().getEyeLocation().getDirection(), 100);
                if (result == null) return;
                seeker.sendMessage(ChatColor.GOLD.toString() + "You have spotted " + hider.getDisplayName() + ChatColor.RESET.toString());
                hider.sendMessage(ChatColor.RED.toString() + "You have been spotted by " + ((Player) seeker).getPlayer().getDisplayName() + ChatColor.RESET.toString());
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
        if (seeking && runnable != null && !runnable.isCancelled()) {
            runnable.cancel();
            runnable = null;
            Bukkit.getLogger().info("STOPPED RUNNABLE!");
        }
    }
}

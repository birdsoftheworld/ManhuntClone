package birds.manhuntclone.ManhuntClone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerTracker implements Listener {

    private Cooldown cooldown = new Cooldown();

    void setTracker(Player tracker) {
        this.tracker = tracker;
    }

    void setTracked(Player tracked) {
        this.tracked = tracked;
    }

    void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    Player getTracker() {
        return tracker;
    }

    Player getTracked() {
        return tracked;
    }

    boolean isTracking() {
        return isTracking;
    }

    private Player tracker;
    private Player tracked;

    private boolean isTracking;

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        // when a player uses an item
        Player player = event.getPlayer();

        if(player.getInventory().getItemInMainHand().getType() == Material.COMPASS && isTracking) {
            if(player.equals(tracker)) {
                if(cooldown.hasTimePassed(2500)) {
                    player.sendMessage(ChatColor.GREEN.toString() + "Compass set to tracked.");
                    player.setCompassTarget(tracked.getLocation());
                    cooldown.setTimeToNow();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // untrack on player quit
        Player player = event.getPlayer();

        if(player.equals(tracked) || player.equals(tracker)) {
            this.tracker = null;
            this.tracked = null;
            isTracking = false;
        }
    }
}

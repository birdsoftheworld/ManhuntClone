package birds.manhuntclone.ManhuntClone.modes;

import birds.manhuntclone.ManhuntClone.util.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerTracker implements Listener {

    private Cooldown cooldown = new Cooldown();

    public void setTracker(Player tracker) {
        this.tracker = tracker;
    }

    public void setTracked(Player tracked) {
        this.tracked = tracked;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    public Player getTracker() {
        return tracker;
    }

    public Player getTracked() {
        return tracked;
    }

    public boolean isTracking() {
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
                if(cooldown.hasTimePassed(3000)) {
                    player.sendMessage(ChatColor.GREEN.toString() + "Compass set to tracked.");
                    cooldown.setTimeToNow();
                    if(player.getWorld().getEnvironment().equals(tracked.getWorld().getEnvironment())) {
                        player.setCompassTarget(tracked.getLocation());
                    }
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

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // give tracker compass on respawn
        Player player = event.getPlayer();

        if(player.equals(tracker)) {
            player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
        }
    }
}

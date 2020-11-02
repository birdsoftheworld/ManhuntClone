package birds.manhuntclone.ManhuntClone.modes;

import birds.manhuntclone.ManhuntClone.ManhuntClone;
import birds.manhuntclone.ManhuntClone.menu.PlayerChooseHandler;
import birds.manhuntclone.ManhuntClone.menu.PlayerPicker;
import birds.manhuntclone.ManhuntClone.util.Cooldown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import sun.security.util.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PlayerTracker implements Listener {

    private Cooldown cooldown = new Cooldown();
    private BukkitRunnable updateRunnable;
    private ManhuntClone plugin;

    public PlayerTracker(ManhuntClone plugin) {
        this.plugin = plugin;
    }

    public void startRunnable(int interval) {
        updateRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player tracker : trackers) {
                    if (tracked.containsKey(tracker)) {
                        tracker.setCompassTarget(tracked.get(tracker).getLocation());
                    }
                }
            }
        };
        updateRunnable.runTaskTimer(plugin, 0, interval);
    }

    public void setTracker(List<Player> tracker) {
        this.trackers = tracker;
    }

    public void removeTracker(Player player) {
        if (getTrackers().contains(player)) {
            getTrackers().remove(player);
        }
        if (getTracked().containsKey(player)) {
            getTracked().remove(player);
        }
    }

    public void addTracker(Player player) {
        getTrackers().add(player);
        cooldown.setTimeToNow(player);
    }

    public void setTracking(Player tracker, Player tracked) {
        this.tracked.put(tracker, tracked);
    }

    public void setTracking(Player player, boolean tracking) {
        isTracking.put(player, tracking);
    }

    public List<Player> getTrackers() {
        return trackers;
    }

    public Map<Player, Player> getTracked() {
        return tracked;
    }

    public Player getTrackedPlayer(Player tracker) {
        return tracked.get(tracker);
    }

    public Player getTrackingPlayer(Player trackedPlayer) {
        for (Player trackingPlayer : tracked.keySet()) {
            if (tracked.get(trackingPlayer).equals(trackedPlayer)) {
                return trackingPlayer;
            }
        }

        return null;
    }

    public void trackPlayer(Player tracker, Player tracked) {
        addTracker(tracker);
        setTracking(tracker, tracked);
        setTracking(tracker, true);

        tracker.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Now tracking " + tracked.getName() + ".").color(net.md_5.bungee.api.ChatColor.GREEN).create());
        tracked.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("You are now being tracked by " + tracker.getName() + ".").color(net.md_5.bungee.api.ChatColor.GOLD).create());
    }

    public boolean isTracking(Player player) {
        return isTracking.get(player);
    }

    public void givePlayerCompass(Player player) {
        if (!player.getInventory().contains(Material.COMPASS)) {
            ItemStack compass = new ItemStack(Material.COMPASS, 1);
            ItemMeta itemMeta = compass.getItemMeta();
            if (itemMeta != null)
                itemMeta.setDisplayName(org.bukkit.ChatColor.RESET + "Player Tracker");
            compass.setItemMeta(itemMeta);
            player.getInventory().addItem(compass);
        }
    }

    private List<Player> trackers = new ArrayList<>();
    private Map<Player, Player> tracked = new HashMap<>();

    private Map<Player, Boolean> isTracking = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        // when a player uses an item
        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;

        if(player.getInventory().getItemInMainHand().getType() == Material.COMPASS && isTracking.containsKey(player) && isTracking.get(player)) {
            if(trackers.contains(player) && tracked.containsKey(player)) {
                if (plugin.config.getBoolean("updateCompassAutomatically")) {
                    // compass updates automatically, so right clicking will trigger a gui to select the player
                    PlayerPicker playerPicker = new PlayerPicker(plugin, null, selected -> trackPlayer(player, selected), player1 -> true);
                    playerPicker.showInventory(player);
                } else {
                    // updates compass manually, must use commands to set the target
                    if (cooldown.hasTimePassed(player, plugin.config.getInt("compassCooldown"))) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Compass set to tracked.").color(ChatColor.GREEN).create());
                        cooldown.setTimeToNow(player);
                        if (player.getWorld().getEnvironment().equals(tracked.get(player).getWorld().getEnvironment())) {
                            player.setCompassTarget(tracked.get(player).getLocation());
                        }
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Compass is on cooldown!").color(ChatColor.RED).create());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // untrack on player quit
        final Player player = event.getPlayer();

        if(tracked.containsValue(player)) {
            Player tracker = getTrackingPlayer(player);
            tracked.remove(tracker);
            isTracking.remove(tracker);
        } else if (trackers.contains(player)) {
            this.trackers.remove(player);
            isTracking.remove(player);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // give tracker compass on respawn
        Player player = event.getPlayer();

        if(trackers.contains(player)) {
            givePlayerCompass(player);
        }
    }
}

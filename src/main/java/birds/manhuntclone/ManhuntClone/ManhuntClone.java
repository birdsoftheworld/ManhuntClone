package birds.manhuntclone.ManhuntClone;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("ConstantConditions")
public class ManhuntClone extends JavaPlugin {
    private PlayerTracker playerTracker = new PlayerTracker();

    @Override
    public void onEnable() {
        // when the plugin is enabled
        getLogger().info("Manhunt enabled.");

        getServer().getPluginManager().registerEvents(playerTracker, this);

        // commands
        this.getCommand("track").setExecutor(new TrackCommand(playerTracker));
        this.getCommand("untrack").setExecutor(new UntrackCommand(playerTracker));
    }

    @Override
    public void onDisable() {
        // when the plugin is disabled
        getLogger().info("Manhunt disabled.");
    }
}
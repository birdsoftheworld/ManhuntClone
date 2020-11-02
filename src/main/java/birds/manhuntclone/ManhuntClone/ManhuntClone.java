package birds.manhuntclone.ManhuntClone;

import birds.manhuntclone.ManhuntClone.commands.*;
import birds.manhuntclone.ManhuntClone.modes.PlayerTracker;
import birds.manhuntclone.ManhuntClone.util.SeekData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("ConstantConditions")
public class ManhuntClone extends JavaPlugin {
    private PlayerTracker playerTracker = new PlayerTracker(this);
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // when the plugin is enabled
        getLogger().info("Manhunt enabled.");

        getServer().getPluginManager().registerEvents(playerTracker, this);

        // config options
        config.addDefault("updateCompassAutomatically", true);
        config.addDefault("compassCooldown", 1000);
        config.addDefault("compassUpdateInterval", 20);
        config.options().copyDefaults(true);
        saveConfig();

        playerTracker.startRunnable(config.getInt("compassUpdateInterval"));

        // commands
        this.getCommand("track").setExecutor(new TrackCommand(playerTracker));
        this.getCommand("untrack").setExecutor(new UntrackCommand(playerTracker));
        SeekData seekData = new SeekData();
        this.getCommand("seek").setExecutor(new SeekCommand(this, seekData));
        this.getCommand("unseek").setExecutor(new UnseekCommand(this, seekData));
        this.getCommand("swap").setExecutor(new DeathSwapCommand(this));
        this.getCommand("unswap").setExecutor(new DeathSwapCommand(this));
    }

    @Override
    public void onDisable() {
        // when the plugin is disabled
        getLogger().info("Manhunt disabled.");
    }
}
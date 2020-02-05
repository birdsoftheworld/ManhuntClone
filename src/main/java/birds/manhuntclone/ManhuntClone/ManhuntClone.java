package birds.manhuntclone.ManhuntClone;

import birds.manhuntclone.ManhuntClone.commands.SeekCommand;
import birds.manhuntclone.ManhuntClone.commands.TrackCommand;
import birds.manhuntclone.ManhuntClone.commands.UnseekCommand;
import birds.manhuntclone.ManhuntClone.commands.UntrackCommand;
import birds.manhuntclone.ManhuntClone.modes.PlayerTracker;
import birds.manhuntclone.ManhuntClone.util.SeekData;
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
        SeekData seekData = new SeekData();
        this.getCommand("seek").setExecutor(new SeekCommand(this, seekData));
        this.getCommand("unseek").setExecutor(new UnseekCommand(this, seekData));
    }

    @Override
    public void onDisable() {
        // when the plugin is disabled
        getLogger().info("Manhunt disabled.");
    }
}
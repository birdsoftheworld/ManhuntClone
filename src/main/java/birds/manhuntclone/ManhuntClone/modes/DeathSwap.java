package birds.manhuntclone.ManhuntClone.modes;

import birds.manhuntclone.ManhuntClone.IntervalMode;
import birds.manhuntclone.ManhuntClone.ManhuntClone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class DeathSwap extends IntervalMode {

    private ManhuntClone manhuntClone;

    private int runInterval;

    private int iterations = 0;

    public int getRunInterval() {
        return runInterval;
    }

    public void setRunInterval(int runInterval) {
        this.runInterval = runInterval;
    }

    public DeathSwap(ManhuntClone manhuntClone) {
        super(manhuntClone, "Death Swap");

        this.manhuntClone = manhuntClone;
    }

    @Override
    public BukkitRunnable getRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                int runInterval = getRunInterval();
                if (iterations >= runInterval) {
                    ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                    if (players.size() > 2) {
                        for (int i = 0; i < players.size(); i++) {
                            Player onlinePlayer = players.get(i);
                            Location thisPlayerLocation = onlinePlayer.getLocation();
                            Location otherPlayerLocation = thisPlayerLocation;

                            int otherPlayerIndex = (int) (Math.floor(Math.random() * players.size()));
                            Player otherPlayer = players.get(otherPlayerIndex);

                            while (players.get(otherPlayerIndex) != onlinePlayer) {
                                otherPlayerIndex = (int) (Math.floor(Math.random() * players.size()));
                                otherPlayer = players.get(otherPlayerIndex);
                                otherPlayerLocation = players.get(otherPlayerIndex).getLocation();
                            }

                            onlinePlayer.teleport(otherPlayerLocation);
                            otherPlayer.teleport(thisPlayerLocation);
                        }
                    } else if (players.size() == 2) {
                        Player firstPlayer = players.get(0);
                        Player secondPlayer = players.get(1);
                        Location firstPlayerLocation = firstPlayer.getLocation();
                        Location secondPlayerLocation = secondPlayer.getLocation();
                        firstPlayer.teleport(secondPlayerLocation);
                        secondPlayer.teleport(firstPlayerLocation);
                    }
                    iterations = 0;
                } else {
                    if (((runInterval - iterations) / 20) <= 5 && iterations % 20 == 0) {
                        Bukkit.broadcastMessage(ChatColor.GOLD.toString() + "Time Left: " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + String.valueOf((runInterval - iterations) / 20) + ChatColor.RESET.toString());
                    }
                    iterations += 1;
                }
            }
        };
    }
}

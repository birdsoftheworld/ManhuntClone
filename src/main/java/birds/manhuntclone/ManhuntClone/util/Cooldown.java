package birds.manhuntclone.ManhuntClone.util;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {
    public long getCurrentSetTime(Player player) {
        return currentSetTime.get(player);
    }

    public void setCurrentSetTime(Player player, long millisecondsSinceLastSet) {
        this.currentSetTime.put(player, millisecondsSinceLastSet);
    }

    private Map<Player, Long> currentSetTime = new HashMap<>();

    public boolean hasTimePassed(Player player, long time) {
        return Math.abs(this.currentSetTime.get(player) - System.currentTimeMillis()) >= time;
    }

    public void setTimeToNow(Player player) {
        this.currentSetTime.put(player, System.currentTimeMillis());
    }
}

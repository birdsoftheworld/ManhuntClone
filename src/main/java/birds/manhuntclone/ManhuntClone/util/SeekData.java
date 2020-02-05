package birds.manhuntclone.ManhuntClone.util;

import org.bukkit.entity.Player;

public class SeekData {

    private Player seeker;
    private Player hider;
    private boolean seeking;

    public Player getSeeker() {
        return seeker;
    }

    public void setSeeker(Player seeker) {
        this.seeker = seeker;
    }

    public Player getHider() {
        return hider;
    }

    public void setHider(Player hider) {
        this.hider = hider;
    }

    public boolean isSeeking() {
        return seeking;
    }

    public void setSeeking(boolean seeking) {
        this.seeking = seeking;
    }
}

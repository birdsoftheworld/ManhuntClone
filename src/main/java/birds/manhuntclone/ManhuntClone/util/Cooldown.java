package birds.manhuntclone.ManhuntClone.util;

public class Cooldown {
    public long getCurrentSetTime() {
        return currentSetTime;
    }

    public void setCurrentSetTime(long millisecondsSinceLastSet) {
        this.currentSetTime = millisecondsSinceLastSet;
    }

    private long currentSetTime;

    public Cooldown() {
        this.currentSetTime = System.currentTimeMillis();
    }

    public boolean hasTimePassed(long time) {
        return this.currentSetTime - System.currentTimeMillis() >= time;
    }

    boolean hasTimePassed(int time) {
        return System.currentTimeMillis() - this.currentSetTime >= (long) time;
    }

    public void setTimeToNow() {
        this.currentSetTime = System.currentTimeMillis();
    }
}

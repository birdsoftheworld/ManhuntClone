package birds.manhuntclone.ManhuntClone;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class IntervalMode extends Mode{

    private BukkitRunnable runnable;
    private int interval;
    private ManhuntClone manhuntClone;

    public IntervalMode(ManhuntClone manhuntClone, String name) {
        super(name);
        this.manhuntClone = manhuntClone;
    }

    @Override
    public void update() {
        if (runnable != null) {
            stopInterval();
            startInterval();
        }
    }

    public abstract BukkitRunnable getRunnable();

    public void startInterval() {
        runnable = getRunnable();
        runnable.runTaskTimer(manhuntClone, 0, this.getInterval());
    }

    public void stopInterval() {
        if (runnable != null && !runnable.isCancelled()) {
            runnable.cancel();
            runnable = null;
        }
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int value) {
        this.interval = value;
        if (runnable != null) {
            stopInterval();
            startInterval();
        }
    }

    @Override
    public void enable() {
        super.enable();
        startInterval();
    }

    @Override
    public void disable() {
        super.disable();
        stopInterval();
    }
}

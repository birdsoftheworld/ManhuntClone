package birds.manhuntclone.ManhuntClone;

public class Mode {

    private boolean enabled;
    private String name;

    Mode(String name) {
        this.enabled = false;
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void update() {}
}

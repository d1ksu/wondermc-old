package pl.wondermc.boxpvp.user.sub;

public class UserCooldown {

    private long lastHelpop;

    public UserCooldown(){
        this.lastHelpop = 0L;
    }

    public long getLastHelpop() {
        return lastHelpop;
    }

    public void setLastHelpop(long lastHelpop) {
        this.lastHelpop = lastHelpop;
    }

    public boolean hasHelpopCooldown(){
        return lastHelpop > System.currentTimeMillis();
    }
}

package pl.wondermc.boxpvp.user.sub;

public class UserFight {

    private long lastFight;
    private String lastAttacker;

    public void setLastAttacker(String lastAttacker) {
        this.lastAttacker = lastAttacker;
    }

    public String getLastAttacker() {
        return lastAttacker;
    }

    public long getLastFight() {
        return lastFight;
    }

    public void setLastFight(long lastFight) {
        this.lastFight = lastFight;
    }

    public boolean has(){
        return this.lastFight > System.currentTimeMillis();
    }
}

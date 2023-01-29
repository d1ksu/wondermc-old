package pl.wondermc.boxpvp.user.sub;

import pl.wondermc.boxpvp.helper.ChatHelper;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserBar {

    private final transient Map<Actionbar, String> userBarMap;

    public UserBar(){
        this.userBarMap = new LinkedHashMap<>();
    }


    public void updateActionBar(Actionbar type, String text) {
        synchronized (this.userBarMap) {
            this.userBarMap.put(type, text);
        }
    }

    public void removeActionBar(Actionbar type) {
        synchronized (this.userBarMap) {
            this.userBarMap.remove(type);
        }
    }

    public void clearActionBars() {
        synchronized (this.userBarMap) {
            this.userBarMap.clear();
        }
    }

    public String collectActiveActionBars() {
        return this.userBarMap.values().toString().replace("[", "").replace("]", "").replace(",", ChatHelper.fixColor(" &8:|:"));
    }

    public enum Actionbar{
        FIGHT, VANISH, GOD
    }
}

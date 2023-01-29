package pl.wondermc.boxpvp.reward;

import java.util.List;

public class DailyReward {

    private final String name;
    private final int day;
    private final List<String> commands;

    public DailyReward(String name, int day, List<String> commands) {
        this.name = name;
        this.day = day;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public List<String> getCommands() {
        return commands;
    }
}

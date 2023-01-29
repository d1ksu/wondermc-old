package pl.wondermc.discordbot.command;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SlashCommandService {

    private final Map<String, SlashCommand> slashCommandMap;

    public SlashCommandService(){
        this.slashCommandMap = new ConcurrentHashMap<>();
    }

    public Optional<SlashCommand> find(String name){
        return Optional.ofNullable(this.slashCommandMap.get(name));
    }

    public SlashCommandService register(String name, SlashCommand slashCommand){
        this.slashCommandMap.put(name, slashCommand);
        return this;
    }

}

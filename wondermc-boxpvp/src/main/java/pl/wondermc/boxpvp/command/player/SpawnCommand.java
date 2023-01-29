package pl.wondermc.boxpvp.command.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;

public class SpawnCommand {

    private final PluginConfiguration pluginConfiguration;

    public SpawnCommand(PluginConfiguration pluginConfiguration){
        this.pluginConfiguration = pluginConfiguration;
    }

    @Command(name = "spawn", permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Bukkit.getServer().dispatchCommand(player, this.pluginConfiguration.getWarpSpawnCommand());


    }



}

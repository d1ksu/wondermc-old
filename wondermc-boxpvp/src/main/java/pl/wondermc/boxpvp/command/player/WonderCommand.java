package pl.wondermc.boxpvp.command.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

public class WonderCommand {

    private final CommandConfiguration commandConfiguration;

    public WonderCommand(CommandConfiguration commandConfiguration){
        this.commandConfiguration = commandConfiguration;
    }

    @Command(name = "wonder",  permission = "wondermc.player",inGameOnly = false)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        commandConfiguration.getWonderCommandMessage().forEach(text ->{
            player.sendMessage(Component.text(ChatHelper.fixColor(text)));
        });
    }
}

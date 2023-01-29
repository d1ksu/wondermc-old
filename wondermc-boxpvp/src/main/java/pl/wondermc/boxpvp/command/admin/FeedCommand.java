package pl.wondermc.boxpvp.command.admin;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

public class FeedCommand {

    private final CommandConfiguration commandConfiguration;

    public FeedCommand(CommandConfiguration commandConfiguration){
        this.commandConfiguration = commandConfiguration;
    }

    @Command(name = "feed", aliases = "nakarm",permission = "wondermc.admin.feed", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        player.setFoodLevel(20);
        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getFeedInfo()));


    }
}

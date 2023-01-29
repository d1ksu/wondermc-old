package pl.wondermc.boxpvp.command.admin;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

public class HealCommand {

    private final CommandConfiguration commandConfiguration;

    public HealCommand(CommandConfiguration commandConfiguration){
        this.commandConfiguration = commandConfiguration;
    }

    @Command(name = "heal", aliases = "ulecz", permission = "wondermc.admin.heal", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        player.setHealth(20);
        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getHealInfo()));


    }
}

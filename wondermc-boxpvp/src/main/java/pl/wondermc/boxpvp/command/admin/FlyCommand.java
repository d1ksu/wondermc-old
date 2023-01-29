package pl.wondermc.boxpvp.command.admin;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

public class FlyCommand {

    private final CommandConfiguration commandConfiguration;

    public FlyCommand(CommandConfiguration commandConfiguration){
        this.commandConfiguration = commandConfiguration;
    }

    @Command(name = "fly", permission = "wondermc.admin.fly", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getFlyInfo()
                .replace("{STATUS}", player.getAllowFlight() ? this.commandConfiguration.getEnableStatus()
                        : this.commandConfiguration.getDisableStatus())));

    }
}

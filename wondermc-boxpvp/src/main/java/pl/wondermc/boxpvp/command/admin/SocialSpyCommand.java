package pl.wondermc.boxpvp.command.admin;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.UserService;

public class SocialSpyCommand {

    private final CommandConfiguration commandConfiguration;
    private final UserService userService;

    public SocialSpyCommand(CommandConfiguration commandConfiguration, UserService userService) {
        this.commandConfiguration = commandConfiguration;
        this.userService = userService;
    }
    @Command(name = "socialspy", inGameOnly = true, permission = "wondermc.admin.socialspy")
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        this.userService.findUser(player.getUniqueId()).ifPresent(user -> {
            user.setSocialSpy(!user.isSocialSpy());
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getSocialSpy().replace("{STATUS}",
                    (user.isSocialSpy() ? this.commandConfiguration.getEnableStatus() :
                            this.commandConfiguration.getDisableStatus()))));
        });
    }

}

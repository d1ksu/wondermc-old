package pl.wondermc.boxpvp.command.admin;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class GodCommand {


    private final CommandConfiguration commandConfiguration;
    private final UserService userService;

    public GodCommand(CommandConfiguration commandConfiguration, UserService userService){
        this.commandConfiguration = commandConfiguration;
        this.userService = userService;
    }

    @Command(name = "god", permission = "wondermc.admin.god", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        Optional<User> optionalUser = this.userService.findUser(player.getUniqueId());
        if(optionalUser.isEmpty()){
            return;
        }
        User user = optionalUser.get();
        user.setGod(!user.isGod());
        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGodInfo()
                .replace("{STATUS}", user.isGod() ? this.commandConfiguration.getEnableStatus() :
        this.commandConfiguration.getDisableStatus())));



    }
}

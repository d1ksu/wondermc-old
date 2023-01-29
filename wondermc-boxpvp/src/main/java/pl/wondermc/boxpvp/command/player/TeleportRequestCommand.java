package pl.wondermc.boxpvp.command.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class TeleportRequestCommand {

    private final CommandConfiguration commandConfiguration;
    private final UserService userService;

    public TeleportRequestCommand(CommandConfiguration commandConfiguration, UserService userService){
        this.commandConfiguration = commandConfiguration;
        this.userService = userService;
    }
    @Command(name = "tpa", aliases = "teleportplayer", permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        if(args.length < 1){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportRequestUsage()));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPlayerIsOffline()));
            return;
        }
        if(target.equals(player)){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportRequestSelfInteract()));
            return;
        }
        Optional<User> targetUserOptional = this.userService.findUser(target.getUniqueId());
        if(targetUserOptional.isEmpty()){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPlayerIsOffline()));
            return;
        }
        User targetUser = targetUserOptional.get();
        if(targetUser.getTpaRequests().contains(player.getName())){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportRequestAlreadyHas()));
            return;
        }
        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportRequestSender()
                .replace("{PLAYER}", targetUser.getNickname())));

        target.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportRequestTarget()
                .replace("{PLAYER}", player.getName())));
        targetUser.getTpaRequests().add(player.getName());
    }
}

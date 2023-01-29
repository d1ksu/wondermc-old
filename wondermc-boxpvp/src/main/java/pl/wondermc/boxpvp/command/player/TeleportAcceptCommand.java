package pl.wondermc.boxpvp.command.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.helper.TeleportHelper;
import pl.wondermc.boxpvp.user.UserService;

public class TeleportAcceptCommand {

    private final CommandConfiguration commandConfiguration;
    private final UserService userService;

    public TeleportAcceptCommand(CommandConfiguration commandConfiguration, UserService userService) {
        this.commandConfiguration = commandConfiguration;
        this.userService = userService;
    }
    @Command(name = "tpaccept", aliases = {"teleportaccept"}, permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs args){
        Player player = args.getPlayer();
        if(args.getArgs().length < 1){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportAcceptUsage()));
            return;
        }
        this.userService.findUser(player.getUniqueId()).ifPresent(user -> {
            if(user.getTpaRequests().isEmpty()){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportAcceptRequestsEmpty()));
                return;
            }
            String option = args.getArgs(0);
            if(option.equalsIgnoreCase("*") || option.equalsIgnoreCase("all")){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportAcceptSenderAll()));
                for (String nickName : user.getTpaRequests()) {
                    Player targetPlayer = Bukkit.getPlayerExact(nickName);
                    if(targetPlayer == null) continue;
                    targetPlayer.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportAcceptTarget()
                            .replace("{PLAYER}", player.getName())));
                    TeleportHelper.teleport(targetPlayer,
                            System.currentTimeMillis() + 1000L * this.commandConfiguration.getTeleportTime(),
                            player.getLocation(), this.commandConfiguration);
                }
                user.getTpaRequests().clear();
                return;
            }
            Player targetPlayer = Bukkit.getPlayerExact(option);
            if(targetPlayer == null){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPlayerIsOffline()));
                return;
            }
            targetPlayer.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportAcceptTarget()
                    .replace("{PLAYER}", player.getName())));
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportAcceptSenderPlayer()
                    .replace("{PLAYER}", targetPlayer.getName())));
            TeleportHelper.teleport(targetPlayer,
                    System.currentTimeMillis() + 1000L * this.commandConfiguration.getTeleportTime(),
                    player.getLocation(), this.commandConfiguration);
            user.getTpaRequests().remove(targetPlayer.getName());
        });
    }
}

package pl.wondermc.boxpvp.command.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class MsgCommand {

    private final UserService userService;
    private final CommandConfiguration commandConfiguration;

    public MsgCommand(UserService userService, CommandConfiguration commandConfiguration) {
        this.userService = userService;
        this.commandConfiguration = commandConfiguration;
    }
    @Command(name = "msg", aliases = {"message", "tell"}, permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs args){
        Player player = args.getPlayer();
        this.userService.findUser(player.getUniqueId()).ifPresent(user -> {
            if(args.getArgs().length < 2){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPrivateMessageUsage()));
                return;
            }
            String nickName = args.getArgs(0);
            Optional<Player> target = Optional.ofNullable(Bukkit.getPlayerExact(nickName));
            if(target.isEmpty()){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPlayerIsOffline()));
                return;
            }
            String message = StringUtils.join(args.getArgs()," ", 1, args.getArgs().length);
            if(message.trim().isEmpty()){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPrivateMessageEmpty()));
                return;
            }
            target.ifPresent(targetPlayer -> {
                if(targetPlayer.equals(player)){
                    player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPrivateMessageSelfInteract()));
                    return;
                }
                this.userService.findUser(targetPlayer.getUniqueId()).ifPresent(targetUser -> {
                    if(user.getIgnoredPlayers().contains(targetPlayer.getName())){
                        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getIgnoringPlayer()
                                .replace("{PLAYER}", player.getName())));
                        return;
                    }
                    if(targetUser.getIgnoredPlayers().contains(player.getName())){
                        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPrivateMessageDeniedByIgnore()
                                .replace("{PLAYER}", player.getName())));
                        return;
                    }
                    targetPlayer.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPrivateMessageTarget()
                            .replace("{PLAYER}", player.getName())
                            .replace("{MESSAGE}", message)));

                    player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPrivateMessageSender()
                            .replace("{PLAYER}", targetPlayer.getName())
                            .replace("{MESSAGE}", message)));

                    targetUser.setLastMessage(player.getName());
                    user.setLastMessage(targetPlayer.getName());

                    for (User value : this.userService.getUserMap().values()) {
                        if(!value.isSocialSpy()) continue;
                        Player valuePlayer = Bukkit.getPlayerExact(value.getNickname());
                        if(valuePlayer == null) continue;
                        valuePlayer.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getSocialSpyMessage()
                                .replace("{PLAYER_SENDER}", player.getName())
                                .replace("{PLAYER_TARGET}", targetPlayer.getName())
                                .replace("{MESSAGE}", message)));
                    }
                });
            });
        });
    }
}

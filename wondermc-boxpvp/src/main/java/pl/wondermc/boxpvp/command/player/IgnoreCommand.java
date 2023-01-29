package pl.wondermc.boxpvp.command.player;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.UserService;

public class IgnoreCommand {

    private final UserService userService;
    private final CommandConfiguration commandConfiguration;

    public IgnoreCommand(UserService userService, CommandConfiguration commandConfiguration) {
        this.userService = userService;
        this.commandConfiguration = commandConfiguration;
    }
    @Command(name = "ignore", aliases = {"ignoruj", "wyjebane"}, permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        if(args.length != 1){
            player.sendMessage(ChatHelper.fixColor(commandConfiguration.getIgnoreCommandUsage()));
            return;
        }
        if(args[0].equalsIgnoreCase(player.getName())){
            player.sendMessage(ChatHelper.fixColor(commandConfiguration.getCannotIngoreUrself()));
            return;
        }
        this.userService.findUser(player.getUniqueId())
                .ifPresent(user -> {
                    if(user.getIgnoredPlayers().contains(args[0])){
                        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getUnIgnoredPlayer()
                                .replace("{PLAYER}", args[0])));
                        user.getIgnoredPlayers().remove(args[0]);
                        return;
                    }
                    user.getIgnoredPlayers().add(args[0]);
                    player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getIgnoredPlayer()
                            .replace("{PLAYER}", args[0])));
                });

    }
}

package pl.wondermc.boxpvp.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

public class GamemodeCommand {

    private final CommandConfiguration commandConfiguration;

    public GamemodeCommand(CommandConfiguration commandConfiguration){
        this.commandConfiguration = commandConfiguration;
    }

    @Command(name = "gamemode", aliases = "gm", permission = "wondermc.admin.gamemode", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        if(args.length < 1){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeUsage()));
            return;
        }
        if(args.length == 1){
            int value = 0;
            try {
                value = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeUsage()));
                return;
            }
            GameMode gameMode = GameMode.getByValue(value);
            if(gameMode == null){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeUsage()));
                return;
            }
            player.setGameMode(gameMode);
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeChange()
                    .replace("{GAMEMODE}", String.valueOf(gameMode.getValue()))));


            return;
        }
        if(args.length == 2){
            int value = 0;
            try {
                value = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeUsage()));
                return;
            }
            GameMode gameMode = GameMode.getByValue(value);
            if(gameMode == null){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeUsage()));
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null){
                player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPlayerIsOffline()));
                return;
            }
            target.setGameMode(gameMode);
            target.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeChangeByPlayer()
                    .replace("{GAMEMODE}", String.valueOf(gameMode.getValue()))
                            .replace("{PLAYER}", player.getName())));
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getGamemodeChangeForPlayer()
                    .replace("{GAMEMODE}", String.valueOf(gameMode.getValue()))
                            .replace("{PLAYER}", target.getName())));
        }
    }
}

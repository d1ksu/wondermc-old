package pl.wondermc.boxpvp.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

import java.util.Optional;

public class InvseeCommand {

    private final CommandConfiguration commandConfiguration;

    public InvseeCommand(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }
    @Command(name = "invsee", permission = "wondermc.admin.invsee", inGameOnly = true)
    public void execute(CommandArgs args){
        Player player = args.getPlayer();
        if(args.getArgs().length < 1){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getInvseeUsage()));
            return;
        }
        String nickName = args.getArgs(0);
        Optional<Player> target = Optional.ofNullable(Bukkit.getPlayerExact(nickName));
        if(target.isEmpty()){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPlayerIsOffline()));
            return;
        }
        player.openInventory(target.get().getInventory());
    }
}

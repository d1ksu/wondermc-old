package pl.wondermc.boxpvp.command.player;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;

public class WorkBenchCommand {
    @Command(name = "wb", aliases = {"workbench"}, permission = "wondermc.player",inGameOnly = true)
    public void execute(CommandArgs args){
        Player player = args.getPlayer();
        player.openWorkbench(player.getLocation(), true);
    }
}

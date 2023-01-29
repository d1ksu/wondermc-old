package pl.wondermc.boxpvp.command.player;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.KitConfiguration;
import pl.wondermc.boxpvp.database.DatabaseProvider;
import pl.wondermc.boxpvp.inventory.KitInventory;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class KitCommand {

    private final KitConfiguration kitConfiguration;
    private final UserService userService;
    private final DatabaseProvider databaseProvider;

    public KitCommand(KitConfiguration kitConfiguration, UserService userService, DatabaseProvider databaseProvider){
        this.kitConfiguration = kitConfiguration;
        this.userService = userService;
        this.databaseProvider = databaseProvider;
    }

    @Command(name = "kit", aliases = {"kits", "zestaw", "zestawy", "kity"}, permission = "wondermc.player",inGameOnly = true)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Optional<User> optionalUser = this.userService.findUser(player.getUniqueId());
        if(optionalUser.isEmpty()){
            return;
        }
        User user = optionalUser.get();
        KitInventory kitInventory = new KitInventory(kitConfiguration, databaseProvider);
        kitInventory.open(player, user);

    }
}

package pl.wondermc.boxpvp.command.player;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.DailyRewardConfiguration;
import pl.wondermc.boxpvp.database.DatabaseProvider;
import pl.wondermc.boxpvp.inventory.DailyRewardInventory;
import pl.wondermc.boxpvp.reward.DailyRewardService;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class DailyRewardCommand {

    private final UserService userService;
    private final DailyRewardConfiguration dailyRewardConfiguration;
    private final DailyRewardService dailyRewardService;
    private final DatabaseProvider databaseProvider;

    public DailyRewardCommand(UserService userService, DailyRewardConfiguration dailyRewardConfiguration,
                              DailyRewardService dailyRewardService,
                              DatabaseProvider databaseProvider) {
        this.userService = userService;
        this.dailyRewardConfiguration = dailyRewardConfiguration;
        this.dailyRewardService = dailyRewardService;
        this.databaseProvider = databaseProvider;
    }

    @Command(name = "dailyreward", aliases = {"dr", "dailyrewards", "codziennanagroda"}, permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        Optional<User> optionalUser = userService.findUser(player.getUniqueId());
        if(optionalUser.isEmpty()){
            return;
        }
        User user = optionalUser.get();
        DailyRewardInventory dailyRewardInventory = new DailyRewardInventory(dailyRewardConfiguration,
                dailyRewardService, databaseProvider);
        dailyRewardInventory.open(player, user);
    }
}

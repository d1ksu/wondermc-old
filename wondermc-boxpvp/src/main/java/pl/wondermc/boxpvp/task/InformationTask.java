package pl.wondermc.boxpvp.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.UserService;
import pl.wondermc.boxpvp.user.sub.UserBar;

public class InformationTask implements Runnable{

    private final UserService userService;
    private final CommandConfiguration commandConfiguration;


    public InformationTask(UserService userService, CommandConfiguration commandConfiguration){
        this.userService = userService;
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public void run() {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            this.userService.findUser(onlinePlayer.getUniqueId())
                    .ifPresent(user -> {
                        UserBar userBar = user.getUserBar();
                        if(user.isVanish()){
                            userBar.updateActionBar(UserBar.Actionbar.VANISH,
                                    ChatHelper.fixColor(this.commandConfiguration.getVanishActionbar()));
                        } else {
                            userBar.removeActionBar(UserBar.Actionbar.VANISH);
                        }
                        if(user.isGod()){
                            userBar.updateActionBar(UserBar.Actionbar.GOD,
                                    ChatHelper.fixColor(this.commandConfiguration.getGodActionbar()));
                        } else {
                            userBar.removeActionBar(UserBar.Actionbar.GOD);
                        }
                    });
        }
    }
}

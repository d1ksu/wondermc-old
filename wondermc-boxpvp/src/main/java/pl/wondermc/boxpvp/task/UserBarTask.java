package pl.wondermc.boxpvp.task;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.sub.UserBar;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class UserBarTask implements Runnable{

    private final UserService userService;

    public UserBarTask(UserService userService){
        this.userService = userService;
    }


    @Override
    public void run() {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            Optional<User> optionalUser = this.userService.findUser(onlinePlayer.getUniqueId());
            if(optionalUser.isEmpty()){
                continue;
            }
            User user = optionalUser.get();
            UserBar userBar = user.getUserBar();
            onlinePlayer.sendActionBar(Component.text(String.join(":", userBar.collectActiveActionBars())));
        }
    }


}

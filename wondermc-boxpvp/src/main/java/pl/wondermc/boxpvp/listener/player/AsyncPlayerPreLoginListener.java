package pl.wondermc.boxpvp.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.UUID;

public class AsyncPlayerPreLoginListener implements Listener {

    private final UserService userService;

    public AsyncPlayerPreLoginListener(UserService userService){
        this.userService = userService;
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event){
        UUID uniqueId = event.getUniqueId();
        String nickname = event.getName();
        User user = this.userService.findUser(uniqueId)
                .orElseGet(() ->
                    this.userService.create(uniqueId, nickname)
                );
    }
}

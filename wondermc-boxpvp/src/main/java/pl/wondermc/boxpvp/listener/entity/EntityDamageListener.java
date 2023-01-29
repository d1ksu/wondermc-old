package pl.wondermc.boxpvp.listener.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

public class EntityDamageListener implements Listener {

    private final UserService userService;

    public EntityDamageListener(UserService userService){
        this.userService = userService;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageEvent event){
        if(event.isCancelled()) return;
        if(event.getEntity() instanceof Player &&
                this.userService.findUser(event.getEntity().getUniqueId()).filter(User::isGod).isPresent()){
            event.setCancelled(true);

        }
    }
}

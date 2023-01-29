package pl.wondermc.boxpvp.listener.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class EntityDamageByEntityListener implements Listener {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public EntityDamageByEntityListener(UserService userService, PluginConfiguration pluginConfiguration) {
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(event.isCancelled()) return;
        if(event.getEntity() instanceof Player victim) {
            this.userService.findUser(victim.getUniqueId()).filter(victimUser -> !victimUser.isGod())
                    .ifPresent(victimUser ->
                            this.findAttacker(event)
                                    .filter(attacker -> !attacker.equals(victim))
                                    .flatMap(attacker -> this.userService.findUser(attacker.getUniqueId()))
                                    .ifPresent(attackerUser -> {
                                        long fightTime = (System.currentTimeMillis()
                                                + TimeHelper.timeFromString(this.pluginConfiguration.getFightTime()));
                                        victimUser.getUserFight().setLastFight(fightTime);
                                        attackerUser.getUserFight().setLastFight(fightTime);
                                        victimUser.getUserFight().setLastAttacker(attackerUser.getNickname());
                                        attackerUser.getUserFight().setLastAttacker(victimUser.getNickname());
                                    }));
        }
    }

    private Optional<Player> findAttacker(final EntityDamageByEntityEvent event) {
        final Entity attacker = event.getDamager();
        if (attacker instanceof Projectile) {
            final ProjectileSource shooter = ((Projectile) attacker).getShooter();
            return Optional.of(shooter).filter(Player.class::isInstance).map(Player.class::cast);
        }

        return Optional.of(attacker).filter(Player.class::isInstance).map(Player.class::cast);
    }
}

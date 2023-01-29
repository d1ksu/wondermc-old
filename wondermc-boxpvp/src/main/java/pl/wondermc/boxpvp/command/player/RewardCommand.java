package pl.wondermc.boxpvp.command.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.messaging.api.packet.MessengerPacketResponse;
import org.pieszku.messaging.nats.NatsMessenger;
import pl.wondermc.api.packet.PlayerCollectedRewardCheckPacket;
import pl.wondermc.api.packet.PlayerCollectedRewardCheckResponsePacket;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class RewardCommand {

    private final NatsMessenger natsMessenger;
    private final PluginConfiguration pluginConfiguration;
    private final UserService userService;

    public RewardCommand(NatsMessenger natsMessenger, PluginConfiguration pluginConfiguration,
                         UserService userService) {
        this.natsMessenger = natsMessenger;
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
    }

    @Command(name = "reward", aliases = {"nagroda", "odbierznagrode"}, permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Optional<User> optionalUser = this.userService.findUser(player.getUniqueId());
        if(optionalUser.isEmpty()){
            return;
        }
        User user = optionalUser.get();
        if(user.isCollectedDiscordReward()){
            player.sendMessage(ChatHelper.fixColor(pluginConfiguration.getAlreadyCollected()));
            return;
        }
        if(!user.isJoinedDiscord()){
            player.sendMessage(ChatHelper.fixColor(pluginConfiguration.getDiscordJoinRequired()));
            return;
        }
        this.natsMessenger.getMessengerConnection().sendRequestPacket("wondermc_discord",
                new PlayerCollectedRewardCheckPacket(user.getNickname()),
                new MessengerPacketResponse<PlayerCollectedRewardCheckResponsePacket>() {
                    @Override
                    public void done(PlayerCollectedRewardCheckResponsePacket packetResponse) { //
                        player.sendMessage(ChatHelper.fixColor(pluginConfiguration.getSuccessfullyCollected()));
                        for(String command : pluginConfiguration.getDiscordRewards()){
                            Bukkit.getScheduler().runTaskLater(BukkitMain.getInstance(), () -> {
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command
                                        .replace("{PLAYER}", player.getName()));
                            }, 1L);
                        }
                        user.setCollectedDiscordReward(true);
                    }

                    @Override
                    public void error(String errorMessage) {

                    }
                });

    }
}

package pl.wondermc.boxpvp.nats;

import org.pieszku.messaging.api.connection.MessengerConnection;
import org.pieszku.messaging.api.handler.MessengerPacketRequestHandler;
import org.pieszku.messaging.api.handler.type.MessengerPacketHandlerInfo;
import pl.wondermc.api.packet.PlayerCollectedRewardPacket;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.user.UserService;

public class PlayerCollectedRewardHandler implements MessengerPacketRequestHandler {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    @MessengerPacketHandlerInfo(listenChannelName = "wondermc_spigot", packetClass = PlayerCollectedRewardPacket.class)
    public void handle(PlayerCollectedRewardPacket packet, long callbackId, MessengerConnection connection) {
        this.userService.findUser(packet.getPlayerName())
                .ifPresent(user ->
                        user.setJoinedDiscord(true));
    }
}

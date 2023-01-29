package pl.wondermc.boxpvp.nats;

import org.pieszku.messaging.api.connection.MessengerConnection;
import org.pieszku.messaging.api.handler.MessengerPacketRequestHandler;
import org.pieszku.messaging.api.handler.type.MessengerPacketHandlerInfo;
import pl.wondermc.api.packet.PlayerCheckRequestPacket;
import pl.wondermc.api.packet.PlayerCheckResponsePacket;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.user.UserService;


public class PlayerCheckHandler implements MessengerPacketRequestHandler {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    @MessengerPacketHandlerInfo(listenChannelName = "wondermc_spigot", packetClass = PlayerCheckRequestPacket.class)
    public void handle(PlayerCheckRequestPacket packet, long callbackId, MessengerConnection connection) {
        PlayerCheckResponsePacket packetResponse =
                new PlayerCheckResponsePacket(this.userService.findUser(packet.getPlayerName()).isPresent());
        packetResponse.setResponse(true);
        packetResponse.setCallbackId(callbackId);
        packetResponse.setDone(true);
        connection.reply(packetResponse);
    }
}

package pl.wondermc.api.packet;

import org.pieszku.messaging.api.packet.MessengerPacketCallback;

public class PlayerCheckRequestPacket extends MessengerPacketCallback {

    private String playerName;

    public PlayerCheckRequestPacket(String nickname){
        this.playerName = nickname;
    }

    public String getPlayerName() {
        return playerName;
    }
}

package pl.wondermc.api.packet;

import org.pieszku.messaging.api.packet.MessengerPacketCallback;

public class PlayerHelpopRequestPacket extends MessengerPacketCallback {

    private String playerName;
    private String message;

    public PlayerHelpopRequestPacket(String playerName, String message){
        this.playerName = playerName;
        this.message = message;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }
}

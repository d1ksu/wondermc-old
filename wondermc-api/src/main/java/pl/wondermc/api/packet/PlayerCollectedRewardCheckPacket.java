package pl.wondermc.api.packet;

import org.pieszku.messaging.api.packet.MessengerPacketCallback;

public class PlayerCollectedRewardCheckPacket extends MessengerPacketCallback {

    private String playerName;

    public PlayerCollectedRewardCheckPacket(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}

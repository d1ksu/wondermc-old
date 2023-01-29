package pl.wondermc.api.packet;

import org.pieszku.messaging.api.packet.MessengerPacketCallback;

public class PlayerCollectedRewardPacket extends MessengerPacketCallback {

    private String playerName;

    public PlayerCollectedRewardPacket(String nickname){
        this.playerName = nickname;
    }

    public String getPlayerName() {
        return playerName;
    }
}

package pl.wondermc.api.packet;

import org.pieszku.messaging.api.packet.MessengerPacketCallback;

public class PlayerCollectedRewardCheckResponsePacket extends MessengerPacketCallback {

    private final boolean collected;

    public PlayerCollectedRewardCheckResponsePacket(boolean collected) {
        this.collected = collected;
    }

    public boolean isCollected() {
        return collected;
    }
}

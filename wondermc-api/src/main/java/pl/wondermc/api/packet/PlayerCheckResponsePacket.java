package pl.wondermc.api.packet;

import org.pieszku.messaging.api.packet.MessengerPacketCallback;

public class PlayerCheckResponsePacket extends MessengerPacketCallback {

    private boolean exists;

    public PlayerCheckResponsePacket(boolean exists){
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }
}

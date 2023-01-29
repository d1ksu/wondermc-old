package org.pieszku.messaging.api.injector;

import org.pieszku.messaging.api.Messenger;
import org.pieszku.messaging.api.injector.type.MessengerPacketInjectorType;

public abstract class MessengerPacketInjector<T extends Messenger> {


    private final MessengerPacketInjectorType packetInjectorType;

    public MessengerPacketInjector(MessengerPacketInjectorType packetInjectorType) {
        this.packetInjectorType = packetInjectorType;
    }
    public MessengerPacketInjectorType getPacketInjectorType() {
        return packetInjectorType;
    }

}

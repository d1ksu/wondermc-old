package org.pieszku.messaging.api;

import org.pieszku.messaging.api.connection.exception.MessengerConnectionException;
import org.pieszku.messaging.api.connection.exception.MessengerSendPacketException;
import org.pieszku.messaging.api.connection.state.MessengerConnectionState;
import org.pieszku.messaging.api.packet.MessengerPacket;
import org.pieszku.messaging.api.packet.MessengerPacketCallback;
import org.pieszku.messaging.api.packet.MessengerPacketResponse;

import java.util.concurrent.CompletableFuture;

public interface Messenger {


     void setConnectionState(MessengerConnectionState connectionState);

    void connect(String host, String password, int port) throws MessengerConnectionException;

    void disconnect();

    <T extends MessengerPacket> void sendPacket(String channelName, T packet);

    <T extends MessengerPacketCallback> CompletableFuture<T> sendRequestPacket(String channelName, MessengerPacketCallback packet, MessengerPacketResponse<T> packetResponse) throws MessengerSendPacketException;

    void reply(MessengerPacket replyPacket);
}

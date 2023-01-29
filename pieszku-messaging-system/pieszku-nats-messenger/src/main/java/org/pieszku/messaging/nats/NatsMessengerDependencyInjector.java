package org.pieszku.messaging.nats;

import org.pieszku.messaging.api.handler.MessengerPacketRequestHandler;
import org.pieszku.messaging.api.handler.type.MessengerPacketHandlerInfo;
import org.pieszku.messaging.api.injector.MessengerPacketHandlerDependencyInjector;
import org.pieszku.messaging.api.packet.MessengerPacket;
import org.pieszku.messaging.api.packet.MessengerPacketCallback;
import org.pieszku.messaging.api.packet.MessengerPacketResponse;
import org.pieszku.messaging.api.packet.MessengerPacketSerializer;
import org.reflections.Reflections;

import java.util.Optional;

public class NatsMessengerDependencyInjector extends MessengerPacketHandlerDependencyInjector<NatsMessengerConnection> {


    public NatsMessengerDependencyInjector(String handlersPackageName){
        super(handlersPackageName);
    }

    public void registerOtherPackage(NatsMessengerConnection messengerConnection, String packageName){
        new Reflections(packageName).getSubTypesOf(MessengerPacketRequestHandler.class).forEach(messengerHandlerInstance -> {
            try {
                NatsMessengerCallbackHandler requestHandler = new NatsMessengerCallbackHandler(messengerHandlerInstance.newInstance());

                for (MessengerPacketHandlerInfo messengerPacketHandlerInfo : requestHandler.getHandlerInfoList()) {
                    messengerConnection.getConnection().createDispatcher().subscribe(messengerPacketHandlerInfo.listenChannelName(), requestHandler);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(NatsMessengerConnection messengerConnection) {
        messengerConnection.getConnection().createDispatcher().subscribe("messenger_response", message -> {
            MessengerPacket packet = MessengerPacketSerializer.deserialize(message.getData());

            if(packet instanceof MessengerPacketCallback){
                MessengerPacketCallback packetRequest = (MessengerPacketCallback) packet;

                Optional<MessengerPacketResponse> callbackPacketOptional = NatsMessenger.getInstance().getCallbackCache().findRequestCallbackPacket(packetRequest.getCallbackId());
                if (!callbackPacketOptional.isPresent()) return;

                callbackPacketOptional.ifPresent(callbackPacket -> {

                    if (!packetRequest.isResponse()) {
                        callbackPacket.error(packetRequest.getErrorMessage());
                        return;
                    }
                    callbackPacket.done(packetRequest);
                    NatsMessenger.getInstance().getCallbackCache().applyCallback(packetRequest.getCallbackId());
                });
            }
        });
        this.getReflections().getSubTypesOf(MessengerPacketRequestHandler.class).forEach(messengerHandlerInstance -> {
            try {
                NatsMessengerCallbackHandler requestHandler = new NatsMessengerCallbackHandler(messengerHandlerInstance.newInstance());

                for (MessengerPacketHandlerInfo messengerPacketHandlerInfo : requestHandler.getHandlerInfoList()) {
                    messengerConnection.getConnection().createDispatcher().subscribe(messengerPacketHandlerInfo.listenChannelName(), requestHandler);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}

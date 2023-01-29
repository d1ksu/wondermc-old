package org.pieszku.messaging.nats;

import org.pieszku.messaging.api.connection.state.MessengerConnectionState;
import org.pieszku.messaging.api.controller.MessengerController;
import org.pieszku.messaging.api.injector.type.MessengerPacketInjectorType;

public class NatsMessenger extends MessengerController<NatsMessengerConnection> {


    private static NatsMessenger instance;
    private final NatsMessengerConnection messengerConnection;
    private final NatsMessengerDependencyInjector dependencyInjector;

    public static NatsMessenger getInstance() {
        return instance;
    }

    public NatsMessenger(String hostName, int port, String password, String handlersPackageName, String... handlerChannels) {
        super(MessengerPacketInjectorType.NATS);
        instance = this;

        this.messengerConnection = new NatsMessengerConnection(hostName, port, password, handlerChannels, handlersPackageName);
        this.messengerConnection.setConnectionState(MessengerConnectionState.TRYING);

        this.dependencyInjector = new NatsMessengerDependencyInjector(handlersPackageName);
        this.dependencyInjector.initialize(this.messengerConnection);
    }

    public NatsMessengerDependencyInjector getDependencyInjector() {
        return dependencyInjector;
    }

    public NatsMessengerConnection getMessengerConnection() {
        return this.messengerConnection;
    }
}

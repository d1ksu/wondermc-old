package pl.wondermc.discordbot.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;
import pl.wondermc.api.database.Nats;

public class NatsSerializer implements ObjectSerializer<Nats> {

    @Override
    public boolean supports(Class<? super Nats> type) {
        return Nats.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull Nats nats, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("hostname", nats.getHostname());
        data.add("port", nats.getPort());
        data.add("password", nats.getPassword());
    }

    @Override
    public Nats deserialize(DeserializationData data, GenericsDeclaration generics) {
        String hostname = data.get("hostname", String.class);
        int port = data.get("port", Integer.class);
        String password = data.get("password", String.class);
        return new Nats(hostname, port, password);

    }
}

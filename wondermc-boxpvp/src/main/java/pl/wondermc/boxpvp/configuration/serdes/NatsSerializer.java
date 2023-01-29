package pl.wondermc.boxpvp.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.checkerframework.checker.units.qual.N;
import pl.wondermc.api.database.Database;
import pl.wondermc.api.database.Nats;

public class NatsSerializer implements ObjectSerializer<Nats> {

    @Override
    public boolean supports(Class<? super Nats> type) {
        return Nats.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Nats nats, SerializationData data) {
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

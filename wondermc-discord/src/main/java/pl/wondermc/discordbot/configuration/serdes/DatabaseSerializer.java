package pl.wondermc.discordbot.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;
import pl.wondermc.api.database.Database;
import pl.wondermc.api.database.Nats;


public class DatabaseSerializer implements ObjectSerializer<Database> {

    @Override
    public boolean supports(Class<? super Database> type) {
        return Database.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull Database database, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
      data.add("hostname", database.getHostname());
      data.add("database", database.getDatabase());
      data.add("port", database.getPort());
      data.add("user", database.getUser());
      data.add("password", database.getPassword());


    }

    @Override
    public Database deserialize(DeserializationData data, GenericsDeclaration generics) {
        String hostname = data.get("hostname", String.class);
        String database = data.get("database", String.class);
        int port = data.get("port", Integer.class);
        String user = data.get("user", String.class);
        String password = data.get("password", String.class);
        return new Database(hostname, database, port, user, password);

    }
}

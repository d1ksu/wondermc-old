package pl.wondermc.discordbot.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.wondermc.api.database.Database;
import pl.wondermc.api.database.Nats;

public class BotConfiguration extends OkaeriConfig {

    private String token = "";
    private String guildId = "913873651416318022";

    @Comment("# Webhooku do kanalu helpopu.")
    private String webhookUrl = "";
    @Comment("# Dane do polaczenia z baza danych MySQL.")
    private Database database = new Database(
            "localhost",
            "wondermc",
            3306,
            "root",
            ""
    );
    @Comment("# Dane do polaczenia z natsem.")
    private Nats natsConnector = new Nats(
            "127.0.0.1",
            4222,
            ""
    );

    @Comment("# ID kanalu do nagrod.")
    private String rewardChannelId = "1011706301115531264";

    @Comment("# Wiadomosc gdy gracz juz odebral nagrode.")
    private String alreadyCollected = "Odebrales juz nagrode!";

    @Comment("# Wiadomosc gdy podany gracz nie bedzie istnial.")
    private String playerDoesntExists = "Podany gracz nie istnieje!";

    @Comment("# Wiadomosc gdy gracz odbierze nagrode.")
    private String successfullyCollected = "Pomyslnie odebrano nagrode!";

    @Comment("# Wiadomosc gdy gracz uzyje komendy na zlym kanale.")
    private String wrongChannel = "Aby odebrac nagrode uzyj tej komendy na kanale #nagroda";

    public String getWrongChannel() {
        return wrongChannel;
    }

    public String getSuccessfullyCollected() {
        return successfullyCollected;
    }

    public String getAlreadyCollected() {
        return alreadyCollected;
    }

    public String getPlayerDoesntExists() {
        return playerDoesntExists;
    }

    public String getRewardChannelId() {
        return rewardChannelId;
    }

    public Nats getNatsConnector() {
        return natsConnector;
    }

    public Database getDatabase() {
        return database;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getToken() {
        return token;
    }

    public String getGuildId() {
        return guildId;
    }
}

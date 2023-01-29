package pl.wondermc.discordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.pieszku.messaging.nats.NatsMessenger;
import pl.wondermc.api.database.Nats;
import pl.wondermc.discordbot.user.RewardCache;
import pl.wondermc.discordbot.command.SlashCommandListener;
import pl.wondermc.discordbot.command.SlashCommandService;
import pl.wondermc.discordbot.command.impl.RewardCommand;
import pl.wondermc.discordbot.configuration.BotConfiguration;
import pl.wondermc.discordbot.configuration.factory.ConfigurationFactory;
import pl.wondermc.discordbot.configuration.serdes.DatabaseSerializer;
import pl.wondermc.discordbot.configuration.serdes.NatsSerializer;
import pl.wondermc.discordbot.database.DatabaseProvider;

import javax.security.auth.login.LoginException;
import java.io.File;

public class BotMain {

    private static BotMain instance;

    private JDA jda;
    private Guild guild;
    private ConfigurationFactory configurationFactory;
    private BotConfiguration botConfiguration;
    private NatsMessenger natsMessenger;
    private RewardCache rewardCache;
    private SlashCommandService slashCommandService;
    private DatabaseProvider databaseProvider;

    public BotMain(){
        this.run();
    }


    public void run(){
        instance = this;
        this.configurationFactory = new ConfigurationFactory(new File("configuration"));
        this.botConfiguration = this.configurationFactory.produceConfig(BotConfiguration.class, "config.yml", new NatsSerializer(), new DatabaseSerializer());
        this.rewardCache = new RewardCache();
        this.databaseProvider = new DatabaseProvider(botConfiguration, rewardCache);
        this.initializeNats(
                "messenger_response",
                "wondermc_discord", "wondermc_spigot");

        try {
            this.initializeBuilder();
        } catch (LoginException | InterruptedException exception){
            throw new ExceptionInInitializerError(exception);
        }
    }



    private void initializeBuilder() throws LoginException, InterruptedException {
        this.jda = JDABuilder.createDefault(botConfiguration.getToken())
                .build()
                .awaitReady();
        this.guild = jda.getGuildById(botConfiguration.getGuildId());

        this.initializeCommands();
        this.jda.addEventListener(new SlashCommandListener(slashCommandService));
    }

    private void initializeCommands(){
        this.guild.upsertCommand("nagroda", "Odbieranie nagrody.")
                .addOption(OptionType.STRING, "nick", "Nick", true)
                .queue();

         this.slashCommandService = new SlashCommandService()
                .register("nagroda", new RewardCommand(botConfiguration, rewardCache, natsMessenger,
                        databaseProvider));
    }

    private void initializeNats(String... listenChannels){
        Nats nats = this.botConfiguration.getNatsConnector();
        this.natsMessenger = new NatsMessenger(nats.getHostname(), nats.getPort(),nats.getPassword(),
                "pl.wondermc.discordbot.nats", listenChannels);
    }

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }

    public static BotMain getInstance() {
        return instance;
    }

    public BotConfiguration getBotConfiguration() {
        return botConfiguration;
    }

    public NatsMessenger getNatsMessenger() {
        return natsMessenger;
    }

    public Guild getGuild() {
        return guild;
    }

    public JDA getJda() {
        return jda;
    }

    public ConfigurationFactory getConfigurationFactory() {
        return configurationFactory;
    }

    public RewardCache getRewardCache() {
        return rewardCache;
    }
}

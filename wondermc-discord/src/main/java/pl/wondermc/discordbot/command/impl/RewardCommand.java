package pl.wondermc.discordbot.command.impl;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.pieszku.messaging.api.packet.MessengerPacketResponse;
import org.pieszku.messaging.nats.NatsMessenger;
import pl.wondermc.api.packet.PlayerCheckRequestPacket;
import pl.wondermc.api.packet.PlayerCheckResponsePacket;
import pl.wondermc.api.packet.PlayerCollectedRewardPacket;
import pl.wondermc.discordbot.BotMain;
import pl.wondermc.discordbot.command.SlashCommand;
import pl.wondermc.discordbot.configuration.BotConfiguration;
import pl.wondermc.discordbot.database.DatabaseProvider;
import pl.wondermc.discordbot.user.RewardCache;
import pl.wondermc.discordbot.user.RewardUser;

import java.util.Objects;

public class RewardCommand implements SlashCommand {

    private final BotConfiguration botConfiguration;
    private final RewardCache rewardCache;
    private final NatsMessenger natsMessenger;
    private final DatabaseProvider databaseProvider;

    public RewardCommand(BotConfiguration botConfiguration, RewardCache rewardCache, NatsMessenger natsMessenger,
                         DatabaseProvider databaseProvider) {
        this.botConfiguration = botConfiguration;
        this.rewardCache = rewardCache;
        this.natsMessenger = natsMessenger;
        this.databaseProvider = databaseProvider;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        MessageChannel messageChannel = event.getMessageChannel();
        if (!messageChannel.getId().equalsIgnoreCase(this.botConfiguration.getRewardChannelId())) {
            event.reply(this.botConfiguration.getWrongChannel())
                    .setEphemeral(true)
                    .queue();
            return;
        }
        String playerName = Objects.requireNonNull(event.getOption("nick")).getAsString();
        natsMessenger.getMessengerConnection().sendRequestPacket("wondermc_spigot",
                new PlayerCheckRequestPacket(playerName),
                new MessengerPacketResponse<PlayerCheckResponsePacket>() {
                    @Override
                    public void done(PlayerCheckResponsePacket packetResponse) {
                        if (!packetResponse.isExists()) {
                            event.reply(botConfiguration.getPlayerDoesntExists())
                                    .setEphemeral(true)
                                    .queue();
                            return;
                        }
                        var rewardUserOptional = rewardCache.findUserByNickName(playerName);
                        RewardUser rewardUser;
                        if (rewardUserOptional.isEmpty()) {
                            rewardUser = rewardCache.add(playerName);
                            BotMain.getInstance().getDatabaseProvider().saveUser(rewardUser);
                        } else {
                            rewardUser = rewardUserOptional.get();
                        }
                        if (rewardUser.isCollect()) {
                            event.reply(botConfiguration.getAlreadyCollected())
                                    .setEphemeral(true)
                                    .queue();
                            return;
                        }
                        event.reply(botConfiguration.getSuccessfullyCollected())
                                .setEphemeral(true)
                                .queue();

                        rewardUser.setCollect(true);
                        natsMessenger.getMessengerConnection().sendPacket("wondermc_spigot",
                                new PlayerCollectedRewardPacket(playerName));
                        databaseProvider.saveUser(rewardUser);
                    }

                    @Override
                    public void error(String errorMessage) {

                    }
                });


    }
}

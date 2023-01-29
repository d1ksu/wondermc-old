package pl.wondermc.discordbot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SlashCommandListener extends ListenerAdapter {

    private final SlashCommandService slashCommandService;

    public SlashCommandListener(final SlashCommandService slashCommandService) {
        this.slashCommandService = slashCommandService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        this.slashCommandService.find(event.getName())
                .ifPresent(slashCommand ->
                        slashCommand.execute(event)
                );
    }
}

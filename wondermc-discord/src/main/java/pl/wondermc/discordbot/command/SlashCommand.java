package pl.wondermc.discordbot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {

    void execute(final SlashCommandInteractionEvent event);


}

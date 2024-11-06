package tw.mahjong.discord.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class UnknownCommandHandler implements CommandHandler {
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        event.reply("Unknown command!").queue();
    }
}

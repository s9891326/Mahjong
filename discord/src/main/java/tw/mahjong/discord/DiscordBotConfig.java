package tw.mahjong.discord;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordBotConfig {
    private final String token = System.getenv("DISCORD_TOKEN");

    @Bean
    public JDABuilder jdaBuilder(ListenerAdapter listenerAdapter) {
        return JDABuilder.createDefault(token)
                .addEventListeners(listenerAdapter);
    }

    @Bean
    public CommandData createCommand() {
        return Commands.slash("create", "Join a Mahjong game");
    }

    @Bean
    public CommandData joinCommand() {
        return Commands.slash("join", "Join a Mahjong game")
                .addOption(OptionType.STRING, "gameid", "Game Id", true);
    }

    @Bean
    public CommandData startCommand() {
        return Commands.slash("start", "Start a new Mahjong game");
    }

    @Bean
    public CommandData statusCommand() {
        return Commands.slash("status", "Get current game status");
    }

    @Bean
    public CommandData drawCommand() {
        return Commands.slash("draw", "Draw a tile");
    }

    @Bean
    public CommandData[] allCommands() {
        return new CommandData[]{
                createCommand(),
                startCommand(),
                joinCommand(),
                drawCommand(),
                statusCommand(),
        };
    }
}

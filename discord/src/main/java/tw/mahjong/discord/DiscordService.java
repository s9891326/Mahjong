package tw.mahjong.discord;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscordService {
    private final JDABuilder jdaBuilder;

    private final CommandData[] allCommands;

    @Autowired
    public DiscordService(JDABuilder jdaBuilder, CommandData[] allCommands) {
        this.jdaBuilder = jdaBuilder;
        this.allCommands = allCommands;
    }

    @PostConstruct
    public void startBot() {
        try {
            jdaBuilder.build()
                    .updateCommands()
                    .addCommands(allCommands)
                    .queue();
            System.out.println("Discord Bot is up and running.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

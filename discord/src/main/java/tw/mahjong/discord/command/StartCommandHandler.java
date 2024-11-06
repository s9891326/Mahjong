package tw.mahjong.discord.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.app.usecases.StartGameUsecase;
import tw.mahjong.discord.presenter.StartGamePresenter;

import java.util.Map;

@AllArgsConstructor
public class StartCommandHandler implements CommandHandler {
    private final Repository repository;
    private final Map<String, String> userWithGameId;

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String gameId = userWithGameId.get(event.getUser().getName());
        StartGameUsecase startGameUsecase = new StartGameUsecase(repository);
        Presenter presenter = new StartGamePresenter();
        startGameUsecase.execute(startGameUsecase.input(gameId), presenter);
        event.reply(presenter.asBotModel().toString()).queue();
    }
}

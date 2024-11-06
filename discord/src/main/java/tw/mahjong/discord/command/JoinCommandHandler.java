package tw.mahjong.discord.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.app.usecases.JoinGameUsecase;
import tw.mahjong.discord.presenter.JoinGamePresenter;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class JoinCommandHandler implements CommandHandler {
    private final Repository repository;
    private final Map<String, String> userWithGameId;

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String gameId = Objects.requireNonNull(event.getOption("gameid")).getAsString();
        if (gameId.isEmpty()) {
            event.reply("Invalid Game ID").queue();
            return;
        }

        JoinGameUsecase joinGameUsecase = new JoinGameUsecase(repository);
        Presenter presenter = new JoinGamePresenter();
        joinGameUsecase.execute(joinGameUsecase.input(gameId, event.getUser().getName()), presenter);
        event.reply(presenter.asBotModel().toString()).queue();

        userWithGameId.put(event.getUser().getName(), gameId);
    }
}

package tw.mahjong.discord.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.app.usecases.CreateGameUsecase;
import tw.mahjong.discord.components.UserInfoComponent;
import tw.mahjong.discord.presenter.CreateGamePresenter;

@AllArgsConstructor
public class CreateCommandHandler implements CommandHandler {
    private final Repository repository;
    private final UserInfoComponent userInfoComponent;

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        CreateGameUsecase createGameUsecase = new CreateGameUsecase(repository);
        Presenter presenter = new CreateGamePresenter();
        createGameUsecase.execute(createGameUsecase.input(event.getUser().getName()), presenter);

        String gameId = presenter.asBotModel().toString();
        event.reply(gameId).queue();
        userInfoComponent.addUserAndGameId(event.getUser(), gameId);
    }
}

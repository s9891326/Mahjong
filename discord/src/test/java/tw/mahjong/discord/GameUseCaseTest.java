package tw.mahjong.discord;

import org.junit.jupiter.api.Test;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.output.Common;
import tw.mahjong.app.output.Repository;
import tw.mahjong.app.usecases.CreateGameUsecase;
import tw.mahjong.app.usecases.GetStatusUsecase;
import tw.mahjong.app.usecases.JoinGameUsecase;
import tw.mahjong.discord.presenter.CreateGamePresenter;
import tw.mahjong.discord.presenter.GetStatusPresenter;
import tw.mahjong.discord.presenter.JoinGamePresenter;

import static org.junit.jupiter.api.Assertions.*;


public class GameUseCaseTest {
    private final Repository repository = Common.getRepository();

    @Test
    void testCreateAndJoinGame() {
        String gameId = createGame("1");
        System.out.println("discord: " + gameId);
        assertNotNull(gameId);

        assertTrue(joinGame(gameId, "2"));
        assertTrue(joinGame(gameId, "3"));
        assertTrue(joinGame(gameId, "4"));

        assertTrue(startGame(gameId));

        GetStatusPresenter.GetStatusBotModel botModel = get_status(gameId, "1");
        System.out.println(botModel.gameId + botModel.handTile + botModel.doorFront);
        assertEquals(botModel.gameId, gameId);

    }

    private boolean startGame(String gameId) {

        return false;
    }

    private GetStatusPresenter.GetStatusBotModel get_status(String gameId, String playerName) {
        GetStatusUsecase getStatusUsecase = new GetStatusUsecase(repository);
        Presenter presenter = new GetStatusPresenter();
        getStatusUsecase.execute(getStatusUsecase.input(gameId, playerName), presenter);
        return (GetStatusPresenter.GetStatusBotModel) presenter.asBotModel(playerName);
    }

    private boolean joinGame(String gameId, String playerName) {
        JoinGameUsecase joinGameUsecase = new JoinGameUsecase(repository);
        Presenter presenter = new JoinGamePresenter();
        joinGameUsecase.execute(joinGameUsecase.input(gameId, playerName), presenter);
        return (boolean) presenter.asBotModel();
    }

    private String createGame(String playerName) {
        CreateGameUsecase createGameUsecase = new CreateGameUsecase(repository);
        Presenter presenter = new CreateGamePresenter();
        createGameUsecase.execute(createGameUsecase.input(playerName), presenter);
        return presenter.asBotModel().toString();
    }
}

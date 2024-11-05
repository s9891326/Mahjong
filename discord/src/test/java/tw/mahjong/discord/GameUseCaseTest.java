package tw.mahjong.discord;

import org.junit.jupiter.api.Test;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.output.Common;
import tw.mahjong.app.output.Repository;
import tw.mahjong.app.usecases.CreateGameUsecase;
import tw.mahjong.app.usecases.GetStatusUsecase;
import tw.mahjong.app.usecases.JoinGameUsecase;
import tw.mahjong.app.usecases.StartGameUsecase;
import tw.mahjong.discord.presenter.CreateGamePresenter;
import tw.mahjong.discord.presenter.GetStatusPresenter;
import tw.mahjong.discord.presenter.JoinGamePresenter;
import tw.mahjong.discord.presenter.StartGamePresenter;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.Round;

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

        GetStatusPresenter.GetStatusBotModel botModel1 = get_status(gameId, "1");
        System.out.println(botModel1.getGameId() + botModel1.getHandTile() + botModel1.getDoorFront());
        assertEquals(botModel1.getGameId(), gameId);
        assertEquals(botModel1.getHandTile().size(), 17);
        checkEveryoneHandTileSize(botModel1.getLastRound());

        GetStatusPresenter.GetStatusBotModel botModel2 = get_status(gameId, "2");
        checkEveryoneHandTileSize(botModel2.getLastRound());
    }

    private void checkEveryoneHandTileSize(Round lastRound) {
        for (Player player : lastRound.getPlayers()) {
            assertTrue(player.getHandTile().size() >= 16);
        }
    }

    private boolean startGame(String gameId) {
        StartGameUsecase startGameUsecase = new StartGameUsecase(repository);
        Presenter presenter = new StartGamePresenter();
        startGameUsecase.execute(startGameUsecase.input(gameId), presenter);
        return (boolean) presenter.asBotModel();

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

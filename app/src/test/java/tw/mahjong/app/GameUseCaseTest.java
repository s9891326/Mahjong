package tw.mahjong.app;

import org.junit.jupiter.api.Test;
import tw.mahjong.app.output.Common;
import tw.mahjong.app.output.Repository;
import tw.mahjong.app.presenter.CreateGamePresenter;
import tw.mahjong.app.presenter.Presenter;
import tw.mahjong.app.usecases.CreateGameUsecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GameUseCaseTest {
    private final Repository repository = Common.getRepository();

    @Test
    void testCreateAndJoinGame() {
        String gameId = createGame();
        System.out.println(gameId);
        assertNotNull(gameId);

        assertTrue(joinGame(gameId));

//        GameStatus status = get_status(gameId, "1");
    }

//    private GameStatus get_status(String gameId, String s) {
//    }

    private boolean joinGame(String gameId) {

        return false;
    }

    private String createGame() {
        CreateGameUsecase createGameUsecase = new CreateGameUsecase(repository);
        Presenter presenter = new CreateGamePresenter();
        createGameUsecase.execute(createGameUsecase.input("1"), presenter);
        return presenter.asBotModel().toString();
    }
}

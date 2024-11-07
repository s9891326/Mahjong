package tw.mahjong.app.usecases;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.domain.MahjongGame;
import tw.mahjong.domain.events.GameStatusEvent;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.List;

@RequiredArgsConstructor
public class GetStatusUsecase {
    private final Repository repository;

    public GameStatusInput input(String gameId, String playerName) {
        return new GameStatusInput(gameId, playerName);
    }

    public void execute(GameStatusInput input, Presenter presenter) {
        // 查
        MahjongGame game = repository.get(input.gameId);
        if (game == null) {
            throw new MahjongException("找不到對應的game");
        }

        // 推
        presenter.present(List.of(new GameStatusEvent(game)));
    }

    @AllArgsConstructor
    public static class GameStatusInput {
        public String gameId;
        public String playerName;
    }
}

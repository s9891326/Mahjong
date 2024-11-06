package tw.mahjong.app.usecases;

import lombok.RequiredArgsConstructor;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.input.GameStatusInput;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.domain.MahjongGame;
import tw.mahjong.domain.events.GameStatusEvent;

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

        // 推
        presenter.present(List.of(new GameStatusEvent(game)));
    }
}

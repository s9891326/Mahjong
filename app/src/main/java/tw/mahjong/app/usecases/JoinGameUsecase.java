package tw.mahjong.app.usecases;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import tw.mahjong.app.output.Repository;
import tw.mahjong.app.presenter.Presenter;
import tw.mahjong.domain.MahjongGame;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.events.DomainEvent;

import java.util.List;

@RequiredArgsConstructor
public class JoinGameUsecase {
    private final Repository repository;

    public JoinGameInput input(String gameId, String playerName) {
        return new JoinGameInput(gameId, playerName);
    }

    public void execute(JoinGameInput input, Presenter presenter) {
        // 查
        MahjongGame game = repository.get(input.gameId);

        // 改
        Player player = new Player();
        player.setName(input.playerName);
        List<DomainEvent> events = game.join(player);

        // 存
        repository.save(game);

        // 推
        presenter.present(events);
    }

    @AllArgsConstructor
    static class JoinGameInput {
        private String gameId;
        private String playerName;
    }
}

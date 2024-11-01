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
public class CreateGameUsecase {
    private final Repository repository;

    public CreateGameInput input(String playerName) {
        return new CreateGameInput(playerName);
    }

    public void execute(CreateGameInput input, Presenter presenter) {
        // 查改存推
        MahjongGame game = new MahjongGame();
        Player player = new Player();
        player.setName(input.playerName);

        List<DomainEvent> events = game.join(player);

        // 存
        repository.save(game);

        // 推
        presenter.present(events);
    }

    @AllArgsConstructor
    static class CreateGameInput {
        private String playerName;
    }
}

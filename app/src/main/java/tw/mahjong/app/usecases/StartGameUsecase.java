package tw.mahjong.app.usecases;

import lombok.RequiredArgsConstructor;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.input.StartGameInput;
import tw.mahjong.app.output.Repository;
import tw.mahjong.domain.MahjongGame;
import tw.mahjong.domain.events.DomainEvent;

import java.util.List;

@RequiredArgsConstructor
public class StartGameUsecase {
    private final Repository repository;

    public StartGameInput input(String gameId) {
        return new StartGameInput(gameId);
    }

    public void execute(StartGameInput input, Presenter presenter) {
        MahjongGame game = repository.get(input.gameId);
        List<DomainEvent> events = game.start();
        repository.save(game);
        presenter.present(events);
    }
}

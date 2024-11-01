package tw.mahjong.app.output;

import tw.mahjong.domain.MahjongGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class GameRepositoryInMemory implements Repository {
    private final Map<String, MahjongGame> inMemoryData = new HashMap<>();

    @Override
    public void save(MahjongGame game) {
        inMemoryData.put(game.getId(), game);
    }

    @Override
    public Optional<MahjongGame> get(String gameId) {
        return ofNullable(inMemoryData.get(gameId));
    }
}

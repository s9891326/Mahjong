package tw.mahjong.discord.repository;

import tw.mahjong.app.repository.Repository;
import tw.mahjong.domain.MahjongGame;

import java.util.HashMap;
import java.util.Map;

public class GameRepositoryInMemory implements Repository {
    private final Map<String, MahjongGame> inMemoryData = new HashMap<>();

    @Override
    public void save(MahjongGame game) {
        inMemoryData.put(game.getId(), game);
    }

    @Override
    public MahjongGame get(String gameId) {
        return inMemoryData.get(gameId);
    }
}

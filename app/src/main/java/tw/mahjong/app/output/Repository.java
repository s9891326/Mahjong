package tw.mahjong.app.output;

import tw.mahjong.domain.MahjongGame;

import java.util.Optional;

public interface Repository {
    void save(MahjongGame game);

    Optional<MahjongGame> get(String gameId);
}

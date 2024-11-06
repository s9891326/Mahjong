package tw.mahjong.app.repository;

import tw.mahjong.domain.MahjongGame;

public interface Repository {
    void save(MahjongGame game);

    MahjongGame get(String gameId);
}

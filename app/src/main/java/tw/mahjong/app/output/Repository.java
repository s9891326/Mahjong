package tw.mahjong.app.output;

import tw.mahjong.domain.MahjongGame;

public interface Repository {
    void save(MahjongGame game);

    MahjongGame get(String gameId);
}

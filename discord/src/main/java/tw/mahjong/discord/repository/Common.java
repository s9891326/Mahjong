package tw.mahjong.discord.repository;

import tw.mahjong.app.repository.Repository;

public class Common {
    public static Repository getRepository() {
        return new GameRepositoryInMemory();
    }
}

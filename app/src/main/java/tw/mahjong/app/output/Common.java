package tw.mahjong.app.output;

public class Common {
    public static Repository getRepository() {
        return new GameRepositoryInMemory();
    }
}

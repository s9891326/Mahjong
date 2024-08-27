package tw.mahjong.domain;

public class WindsTile extends Tile {
    public static String typeName = "風";

    @Override
    public String toString() {
        return super.value + typeName;
    }
}

package tw.mahjong.domain;

public class WindsCard extends Card {
    public static String typeName = "風";

    @Override
    public String toString() {
        return super.value + typeName;
    }
}

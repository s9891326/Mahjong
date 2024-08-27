package tw.mahjong.domain;

import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Setter
public class SuitTile extends Tile {
    //    筒（Circle）條（Bamboo）萬（Characters）
    private int number;
    private String type;
    public static List<String> typeName = Arrays.asList("萬", "筒", "條");

    public SuitTile(String type) {
        this.type = type;
    }

    @Override
    public void setTileValue(String value) {
        this.number = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return this.number + this.type;
    }
}

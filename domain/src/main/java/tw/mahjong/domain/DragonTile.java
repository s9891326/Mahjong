package tw.mahjong.domain;

import java.util.Arrays;
import java.util.List;

public class DragonTile extends Tile {

    public static List<String> typeName = Arrays.asList("紅中", "發財", "白板");

    public DragonTile(String value) {
        super.value = value;
    }

    @Override
    public String toString() {
        return super.value;
    }
}

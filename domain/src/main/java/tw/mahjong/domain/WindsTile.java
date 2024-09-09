package tw.mahjong.domain;

import java.util.Arrays;
import java.util.List;

public class WindsTile extends Tile {
    public static List<String> typeName = Arrays.asList("東風", "南風", "西風", "北風");

    public WindsTile(String value) {
        this.value = value;
    }
}

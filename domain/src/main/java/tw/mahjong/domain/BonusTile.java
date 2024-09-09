package tw.mahjong.domain;

import java.util.Arrays;
import java.util.List;

public class BonusTile extends Tile {
    public static List<String> flowers = Arrays.asList("梅", "蘭", "竹", "菊");
    public static List<String> seasons = Arrays.asList("春", "夏", "秋", "冬");

    public BonusTile(String value) {
        super.value = value;
    }
}

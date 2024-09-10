package tw.mahjong.domain;

import java.util.Map;

public class BonusTile extends Tile {
    public static final Map<String, Integer> BONUS_TILE_SEAT = Map.of(
            "梅", 1,
            "蘭", 2,
            "竹", 3,
            "菊", 4,
            "春", 1,
            "夏", 2,
            "秋", 3,
            "冬", 4
    );

    private int number;

    public BonusTile(String value) {
        super.value = value;
    }

    @Override
    public void setTileValue(String value) {
        this.number = BONUS_TILE_SEAT.get(value);
    }
}

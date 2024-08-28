package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Setter
public class SuitTile extends Tile implements Cloneable {
    //    筒（Circle）條（Bamboo）萬（Characters）
    @Getter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SuitTile suitTile = (SuitTile) o;
        return number == suitTile.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number);
    }
}

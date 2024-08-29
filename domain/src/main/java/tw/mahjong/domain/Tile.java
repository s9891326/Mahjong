package tw.mahjong.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Tile {
    public String value;

    public static Tile findTileByName(String name) {
        String[] parts = name.split("");
        String pre = parts[0];
        String suf = parts[1];

        Tile tile = null;
        if (SuitTile.typeName.contains(suf)) {
            tile = new SuitTile(suf);
        } else if (WindsTile.typeName.equals(suf)) {
            tile = new WindsTile();
        } else if (DragonTile.typeName.contains(name)) {
            tile = new DragonTile(name);
            pre = name;
        }
        tile.setTileValue(pre);
        return tile;
    }

    public static List<Tile> getChiOption(List<Tile> handTile, String name) {
        List<Tile> chiOption = new ArrayList<>();

        SuitTile suitTile = (SuitTile) findTileByName(name);
        int[] values = {suitTile.getNumber() - 2, suitTile.getNumber() - 1, suitTile.getNumber() + 1, suitTile.getNumber() + 2};
        List<Tile> possibleOption = new ArrayList<>();

        for (int value : values) {
            if (value >= 1 && value <= 9) {
                try {
                    SuitTile tempTile = (SuitTile) suitTile.clone();
                    tempTile.setTileValue(String.valueOf(value));
                    possibleOption.add(tempTile);
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        possibleOption.removeIf(t -> !handTile.contains(t));
        if (possibleOption.size() >= 2) {
            chiOption.add(possibleOption.get(0));
            chiOption.add(suitTile);
            chiOption.add(possibleOption.get(1));
        }
        return chiOption;
    }

    public void setTileValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tile tile = (Tile) obj;
        return Objects.equals(value, tile.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getClass());
    }

    @Override
    public String toString() {
        return "Tile{" +
                "value='" + value + '\'' +
                '}';
    }
}

package tw.mahjong.domain;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Tile {
    @Getter
    public String value;
    public static final int QUANTITY = 4;

    public static Tile findTileByName(String name) {
        String[] parts;
        String pre;
        String suf = "";
        try {
            parts = name.split("");
            pre = parts[0];
            suf = parts[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            pre = name;
        }


        Tile tile;
        if (SuitTile.typeName.contains(suf)) {
            tile = new SuitTile(suf);
        } else if (WindsTile.typeName.contains(name)) {
            tile = new WindsTile(name);
            pre = name;
        } else if (DragonTile.typeName.contains(name)) {
            tile = new DragonTile(name);
            pre = name;
        } else {
            tile = new BonusTile(name);
        }
        tile.setTileValue(pre);
        return tile;
    }

    public static List<Tile> getChiOption(List<Tile> handTiles, SuitTile suitTile) {
        List<Tile> chiOption = new ArrayList<>();

        int[] values = {suitTile.getNumber() - 2, suitTile.getNumber() - 1, suitTile.getNumber() + 1, suitTile.getNumber() + 2};
        List<Tile> possibleOption = new ArrayList<>();

        for (int value : values) {
            if (value >= SuitTile.MIN_NUMBER && value <= SuitTile.MAX_NUMBER) {
                try {
                    SuitTile tempTile = (SuitTile) suitTile.clone();
                    tempTile.setTileValue(String.valueOf(value));
                    possibleOption.add(tempTile);
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        possibleOption.removeIf(t -> !handTiles.contains(t));
        if (possibleOption.size() >= 2) {
            chiOption.add(possibleOption.get(0));
            chiOption.add(suitTile);
            chiOption.add(possibleOption.get(1));
        }
        return chiOption;
    }

    public static List<Tile> getPongOption(List<Tile> handTiles, Tile tile) {
        List<Tile> pongOption = new ArrayList<>();

        for (Tile handTile : handTiles) {
            if (handTile.equals(tile) && pongOption.size() < 2) {
                pongOption.add(handTile);
            }
        }

        if (pongOption.size() > 0) {
            pongOption.add(tile);
        }

        return pongOption;
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
        return this.value;
    }
}

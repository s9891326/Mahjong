package tw.mahjong.domain;


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

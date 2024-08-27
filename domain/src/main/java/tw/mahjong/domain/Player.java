package tw.mahjong.domain;

import lombok.Getter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.List;


public class Player {
    @Getter
    public List<Tile> handTile = new ArrayList<>();

    public void setHandTile(List<String> handTiles) {
        for (String tile : handTiles) {
            this.addHandTile(Tile.findTileByName(tile));
        }
    }

    public void addHandTile(Tile tile) {
        this.handTile.add(tile);
    }

    public void playTile(String tile) throws MahjongException {
        Tile playedTile = Tile.findTileByName(tile);
        if (this.handTile.contains(playedTile)) {
            this.handTile.remove(playedTile);
        } else {
            throw new MahjongException("沒這張卡");
        }
    }

    public boolean isFinish() {
        return this.handTile.size() == 16;
    }
}

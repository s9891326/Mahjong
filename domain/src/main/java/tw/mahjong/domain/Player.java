package tw.mahjong.domain;

import lombok.Getter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.List;


public class Player {
    @Getter
    public List<Tile> handTile = new ArrayList<>();
    @Getter
    public List<Tile> doorFront = new ArrayList<>();

    public void setHandTile(List<String> handTiles) {
        for (String tile : handTiles) {
            this.addHandTile(tile);
        }
    }

    public void addHandTile(String tile) {
        this.handTile.add(Tile.findTileByName(tile));
    }

    public void playTile(String tile) throws MahjongException {
        Tile playedTile = Tile.findTileByName(tile);
        if (this.handTile.contains(playedTile)) {
            this.handTile.remove(playedTile);
        } else {
            throw new MahjongException("沒這張卡");
        }
    }

    public boolean isSuccessPlayTile() {
        return this.handTile.size() == 16;
    }

    public boolean isSuccessDrawTile() {
        return this.handTile.size() == 17;
    }

    public void chi(String tile) {
        List<Tile> chiOption = Tile.getChiOption(this.handTile, tile);
        if (chiOption.isEmpty()) {
            System.out.println("沒牌可以吃");
            return;
        }

        for (Tile option : chiOption) {
            this.handTile.remove(option);
            this.doorFront.add(option);
        }
    }
}

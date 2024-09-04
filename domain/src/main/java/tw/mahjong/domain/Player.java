package tw.mahjong.domain;

import lombok.Getter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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

    public void chi(String otherPlayer, String tileName, String action) {
        if (!otherPlayer.equals("上家")) {
            System.out.println("只能吃上家打的牌");
            return;
        }

        Set<String> validActions = Set.of("碰", "胡");
        if (validActions.contains(action)) {
            System.out.println("其他玩家做了: " + action + "所以不能吃");
            return;
        }

        Tile tile = Tile.findTileByName(tileName);

        if (!(tile instanceof SuitTile suitTile)) {
            System.out.println("只有敘數牌能吃");
            return;
        }

        List<Tile> chiOption = Tile.getChiOption(this.handTile, suitTile);
        if (chiOption.isEmpty()) {
            System.out.println("沒牌可以吃");
            return;
        }

        for (Tile option : chiOption) {
            if (option != suitTile) {
                this.handTile.remove(option);
            }
            this.doorFront.add(option);
        }
    }

    public void pong(String tileName) {
        Tile tile = Tile.findTileByName(tileName);
        List<Tile> pongOption = Tile.getPongOption(this.handTile, tile);
        if (pongOption.isEmpty()) {
            System.out.println("沒牌可以碰");
            return;
        }

        for (Tile option : pongOption) {
            if (option != tile) {
                this.handTile.remove(option);
            }
            this.doorFront.add(option);
        }
    }
}

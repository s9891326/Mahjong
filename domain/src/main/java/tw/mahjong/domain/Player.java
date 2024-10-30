package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Player {
    @Getter
    private final List<Tile> handTile = new ArrayList<>();
    @Getter
    private final List<Tile> doorFront = new ArrayList<>();

    @Getter
    @Setter
    private String name = "";

    @Getter
    public int point = 0;

    public void addHandTile(Tile tile) {
        handTile.add(tile);
    }

    public void playTile(Tile tile) throws MahjongException {
        if (handTile.contains(tile)) {
            handTile.remove(tile);
        } else {
            throw new MahjongException("沒這張卡");
        }
    }

    public boolean isSuccessPlayTile() {
        return handTile.size() == 16;
    }

    public boolean isSuccessDrawTile() {
        return handTile.size() == 17;
    }

    public void chi(Tile tile) {
        if (!(tile instanceof SuitTile suitTile)) {
            System.out.println("只有敘數牌能吃");
            return;
        }

        List<Tile> chiOption = Tile.getChiOption(handTile, suitTile);
        if (chiOption.isEmpty()) {
            System.out.println("沒牌可以吃");
            return;
        }

        for (Tile option : chiOption) {
            option.setDisplay(true);
            if (option != suitTile) {
                handTile.remove(option);
            }
            doorFront.add(option);
        }
    }

    public void pong(Tile tile) {
        List<Tile> pongOption = Tile.getPongOption(handTile, tile);
        if (pongOption == null) {
            throw new MahjongException("沒牌可以碰");
        }

        for (Tile option : pongOption) {
            option.setDisplay(true);
            handTile.remove(option);
            doorFront.add(option);
        }
        tile.setDisplay(true);
        doorFront.add(tile);
    }

    public void kong(Tile tile, boolean isExposedKong) {
        List<Tile> kongOption = Tile.getKongOption(handTile, tile);
        List<Tile> mendKongOption = Tile.getKongOption(doorFront, tile);
        if (kongOption == null && mendKongOption == null) {
            throw new MahjongException("沒牌可以槓");
        }

        if (mendKongOption != null) {
            Tile mendKong = mendKongOption.get(0);
            mendKong.setDisplay(true);
            handTile.remove(mendKong);
            doorFront.add(mendKong);
            return;
        }

        for (Tile option : kongOption) {
            if (isExposedKong) {
                option.setDisplay(true);
            }
            handTile.remove(option);
            doorFront.add(option);
        }

        if (isExposedKong) {
            tile.setDisplay(true);
            doorFront.add(tile);
        }
    }

    public AtomicInteger foulHand() {
        AtomicInteger foulHandNum = new AtomicInteger(0);
        handTile.removeIf(tile -> {
            if (tile instanceof BonusTile) {
                doorFront.add(tile);
                foulHandNum.getAndIncrement();
                return true;
            }
            return false;
        });
        return foulHandNum;
    }

    public boolean hasBonusTile() {
        return handTile.stream().anyMatch(tile -> tile instanceof BonusTile);
    }

    public boolean hasHandTileOrDoorFront() {
        return handTile.size() > 0 || doorFront.size() > 0;
    }

    public void sortTile() {
        handTile.sort(Comparator.comparingInt(Tile::getTilePriority));
    }

    @Override
    public String toString() {
        return "Player{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Player otherPlayer = (Player) obj;
        return name.equals(otherPlayer.getName());
    }
}

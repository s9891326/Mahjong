package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class Player {
    @Getter
    private final List<Tile> handTile = new ArrayList<>();
    @Getter
    private final List<Tile> doorFront = new ArrayList<>();

    @Getter
    @Setter
    public String name = "";

    public void addHandTile(Tile tile) {
        this.handTile.add(tile);
    }

    public void playTile(Tile tile) throws MahjongException {
        if (this.handTile.contains(tile)) {
            this.handTile.remove(tile);
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

    public void chi(String otherPlayer, Tile tile, String action) {
        if (!otherPlayer.equals("上家")) {
            System.out.println("只能吃上家打的牌");
            return;
        }

        Set<String> validActions = Set.of("碰", "胡", "槓");
        if (validActions.contains(action)) {
            System.out.println("其他玩家做了: " + action + "所以不能吃");
            return;
        }

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

    public void pong(Tile tile) {
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

    public AtomicInteger foulHand() {
        AtomicInteger foulHandNum = new AtomicInteger(0);
        this.handTile.removeIf(tile -> {
            if (tile instanceof BonusTile) {
                this.doorFront.add(tile);
                foulHandNum.getAndIncrement();
                return true;
            }
            return false;
        });
        return foulHandNum;
    }

    public boolean hasBonusTile() {
        return this.handTile.stream().anyMatch(tile -> tile instanceof BonusTile);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}

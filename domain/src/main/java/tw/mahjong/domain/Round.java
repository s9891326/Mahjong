package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Round {
    @Getter
    @Setter
    public Deck deck;

    public List<Player> players;

    @Getter
    private Player winner;

    @Getter
    @Setter
    private Player turnPlayer;

    @Getter
    private Player dealer;

    private static final int TILE_GROUP = 3;

    public Round(List<Player> playerList) {
        this.players = playerList;
        this.deck = deckFactory();
    }

    public void setup(int dealerIndex) {
        deck.shuffle();
        drawTile();

        // 第一位玩家開門
        dealer = turnPlayer = players.get(dealerIndex);
        deck.drawTile(turnPlayer);
        foulHand();
        sortTile();
    }

    private void sortTile() {
        // 把手排排序好(萬條統東南西北中發白花)
        players.forEach(Player::sortTile);
    }

    private void drawTile() {
        // 依照東->南->西->北 的玩家進行摸4張牌
        if (players.stream().anyMatch(Player::hasHandTileOrDoorFront)) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            players.forEach(player -> {
                for (int j = 0; j < 4; j++) {
                    deck.drawTile(player);
                }
            });
        }
    }

    private void foulHand() {
        /**
         * 進行補花的動作
         * 若摸到花牌，則需等到四位玩家該輪都補花完成，再換自己補花
         */
        while (players.stream().anyMatch(Player::hasBonusTile)) {
            AtomicInteger foulHandNum;
            for (Player player : players) {
                foulHandNum = player.foulHand();

                for (int i = 0; i < foulHandNum.get(); i++) {
                    deck.drawTile(player);
                }
            }
        }
    }

    public static Deck deckFactory() {
        return new Deck();
    }

    public boolean findWinner(Player player, Tile tile) {
        List<Tile> tempHandTile = new ArrayList<>(player.getHandTile());
        if (tile != null) {
            tempHandTile.add(tile);
        }
        tempHandTile.sort(Comparator.comparingInt(Tile::getTilePriority));

        for (int i = 0; i < tempHandTile.size(); i++) {
            List<Tile> remainingTiles = new ArrayList<>(tempHandTile);

            if (i + 1 < tempHandTile.size() && tempHandTile.get(i).equals(tempHandTile.get(i + 1))) {
                remainingTiles.remove(tempHandTile.get(i));
                remainingTiles.remove(tempHandTile.get(i + 1));
                boolean result = true;

                for (int j = 0; j < remainingTiles.size(); j += TILE_GROUP) {
                    List<Tile> remainTile = remainingTiles.subList(j, Math.min(j + TILE_GROUP, remainingTiles.size()));
                    result = canFormSets(remainTile);

                    if (!result) {
                        break;
                    }
                }
                if (result) {
                    winner = player;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canFormSets(List<Tile> remainTile) {
        // 確認三張牌是否為同樣的類型(都是數牌或字牌)
        if (!areAllSameClass(remainTile)) {
            return false;
        }

        // 判斷是否為刻子
        if (isTriplet(remainTile)) {
            return true;
        }

        // 判斷是否為順子（僅適用於 SuitTile 類型）
        if (remainTile.get(0) instanceof SuitTile) {
            return isSequence(remainTile);
        }

        return false;
    }

    private boolean areAllSameClass(List<Tile> remainTile) {
        Class<?> firstTileClass = remainTile.get(0).getClass();
        return remainTile.stream().allMatch(tile -> tile.getClass().equals(firstTileClass));
    }

    private boolean isTriplet(List<Tile> remainTile) {
        return Objects.equals(remainTile.get(0), remainTile.get(1)) && Objects.equals(remainTile.get(1), remainTile.get(2));
    }

    private static boolean isSequence(List<Tile> remainTile) {
        List<Integer> remainSuitTile = remainTile.stream().map(tile -> ((SuitTile) tile).getNumber()).toList();
        return remainSuitTile.get(0) + 1 == remainSuitTile.get(1) && remainSuitTile.get(1) + 1 == remainSuitTile.get(2);
    }
}

package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

                if (canFormSets(remainingTiles)) {
                    winner = player;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canFormSets(List<Tile> tiles) {
        // 如果沒有牌了，表示順利組成順子或刻子
        if (tiles.isEmpty()) {
            return true;
        }

        // 判斷是否為刻子
        if (tiles.size() >= 3 && tiles.get(0).equals(tiles.get(1)) && tiles.get(1).equals(tiles.get(2))) {
            List<Tile> remainingTiles = new ArrayList<>(tiles);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            return canFormSets(remainingTiles);
        }

        // 判斷是否為順子（僅適用於 SuitTile 類型）
        if (tiles.get(0) instanceof SuitTile firstTile) {
            SuitTile secondTile = findSequenceTile(tiles, firstTile.getType(), firstTile.getNumber() + 1);
            SuitTile thirdTile = findSequenceTile(tiles, firstTile.getType(), firstTile.getNumber() + 2);

            if (secondTile != null && thirdTile != null) {
                tiles.remove(firstTile);
                tiles.remove(secondTile);
                tiles.remove(thirdTile);
                return canFormSets(tiles);
            }
        }
        return false;
    }

    private SuitTile findSequenceTile(List<Tile> tiles, String type, int number) {
        // 從牌中找到某個數字的下一張牌
        for (Tile tile : tiles) {
            if (tile instanceof SuitTile t && t.getType().equals(type) && t.getNumber() == number) {
                return t;
            }
        }
        return null;
    }
}

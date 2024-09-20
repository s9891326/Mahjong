package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Round {
    @Getter
    @Setter
    public Deck deck;

    public List<Player> players;

    public Round(List<Player> playerList) {
        this.players = playerList;
        this.deck = deckFactory();
        setup();
    }

    public void setup() {
        this.deck.shuffle();
        drawTile();
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
                    this.deck.drawTile(player);
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
                    this.deck.drawTile(player);
                }
            }
        }
    }

    public static Deck deckFactory() {
        return new Deck();
    }
}

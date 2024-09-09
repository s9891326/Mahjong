package tw.mahjong.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    @Test
    public void initTileQuantityAndDrawTile() {
        /**
         * 初始化麻將數量144張牌
         * 每位玩家能抽麻將
         */
        Deck deck = new Deck();
        deck.shuffle();
        assertEquals(deck.getTile().size(), 144);

        List<String> playerNames = Arrays.asList("1", "2", "3", "4");
        List<Player> players = playerNames.stream()
                .map(playerName -> {
                    Player player = new Player();
                    player.setName(playerName);
                    return player;
                })
                .toList();

        for (Player player : players) {
            deck.drawTile(player);
            assertEquals(player.getHandTile().size(), 1);
        }
    }
}

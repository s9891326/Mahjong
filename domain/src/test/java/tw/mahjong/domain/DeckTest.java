package tw.mahjong.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    @Test
    public void initTileQuantity() {
        /**
         * 初始化麻將數量144張牌
         */
        Deck deck = new Deck();
        assertEquals(deck.getTile().size(), 144);
    }
}

package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class MahjongGameTest {
    public static class TestDeck extends Deck {
        @Getter
        @Setter
        public List<Tile> tile = new ArrayList<>();

        public TestDeck(List<String> tileNames) {
            for (String tile : tileNames) {
                this.tile.add(Tile.findTileByName(tile));
            }
        }

        public void shuffle() {
        }
    }

    @Test
    void testRoundStart() {
        /**
         * 局 TDD
         * 1. 局開始、局結束
         * 2. 4位玩家每人手牌有16張麻將,此時牌墩剩餘144-16*4 = 80張麻將
         */
        MahjongGame game = new MahjongGame();
        game.addPlayer(new Player());
        game.addPlayer(new Player());
        game.addPlayer(new Player());
        game.addPlayer(new Player());
        game.start();

        assertEquals(game.getPlayers().get(0).getHandTile().size(), 16);
        assertEquals(game.getPlayers().get(1).getHandTile().size(), 16);
        assertEquals(game.getPlayers().get(2).getHandTile().size(), 16);
        assertEquals(game.getPlayers().get(3).getHandTile().size(), 16);
        int allPlayerDoorFront = game.getPlayers().stream()
                .mapToInt(p -> p.getDoorFront().size())
                .sum();
        assertEquals(game.getLastRound().getDeck().getTile().size(), 144 - 64 - allPlayerDoorFront);
    }

    @Test
    void testTileSort() {
        /**
         * 測試每位玩家抽完牌後是否會把手排排序好(萬條統東南西北中發白)
         */
        // given the arranged deck and four player in game
        MahjongGame game = createGameSample(Arrays.asList(
                Arrays.asList("1萬", "2萬", "8萬", "9萬", "4萬", "3萬", "5萬", "4萬", "1萬", "6萬", "5萬", "7萬", "西風", "東風", "南風", "北風"),
                Arrays.asList("1條", "2條", "8條", "9條", "4條", "3條", "5條", "4條", "1條", "6條", "5條", "7條", "北風", "北風", "東風", "東風"),
                Arrays.asList("1筒", "2筒", "8筒", "9筒", "4筒", "3筒", "5筒", "4筒", "1筒", "6筒", "5筒", "7筒", "紅中", "白板", "發財", "紅中"),
                Arrays.asList("白板", "白板", "紅中", "紅中", "發財", "發財", "北風", "北風", "南風", "1萬", "2條", "3筒", "東風", "東風", "西風", "西風")
        ), List.of());

        assertHandTiles(game.getPlayers().get(0), Arrays.asList("1萬", "1萬", "2萬", "3萬", "4萬", "4萬", "5萬", "5萬", "6萬", "7萬", "8萬", "9萬", "東風", "南風", "西風", "北風"));
        assertHandTiles(game.getPlayers().get(1), Arrays.asList("1條", "1條", "2條", "3條", "4條", "4條", "5條", "5條", "6條", "7條", "8條", "9條", "東風", "東風", "北風", "北風"));
        assertHandTiles(game.getPlayers().get(2), Arrays.asList("1筒", "1筒", "2筒", "3筒", "4筒", "4筒", "5筒", "5筒", "6筒", "7筒", "8筒", "9筒", "紅中", "紅中", "發財", "白板"));
        assertHandTiles(game.getPlayers().get(3), Arrays.asList("1萬", "2條", "3筒", "東風", "東風", "南風", "西風", "西風", "北風", "北風", "紅中", "紅中", "發財", "發財", "白板", "白板"));
    }

    @Test
    void testFoulHand() {
        // given the arranged deck and four player in game
        MahjongGame game = createGameSample(Arrays.asList(
                Arrays.asList("1萬", "1萬", "2萬", "2萬", "3萬", "3萬", "4萬", "4萬", "5萬", "6萬", "7萬", "7萬", "1筒", "蘭", "竹", "菊"),
                Arrays.asList("1萬", "1萬", "2萬", "2萬", "3萬", "3萬", "4萬", "4萬", "5萬", "6萬", "7萬", "7萬", "4筒", "夏", "秋", "冬"),
                Arrays.asList("1條", "1條", "2條", "2條", "3條", "3條", "4條", "4條", "5條", "6條", "7條", "7條", "東風", "東風", "西風", "西風"),
                Arrays.asList("1條", "1條", "2條", "2條", "3條", "3條", "4條", "4條", "5條", "6條", "7條", "7條", "東風", "東風", "西風", "西風")
        ), Arrays.asList(
                "梅", "2筒", "3筒", "春", "5筒", "6筒", "7筒", "8筒"
        ));

        // then first and second player have 16 hand tiles and 4 door front tiles
        System.out.println(game.getPlayers().get(0).getHandTile());
        assertEquals(game.getPlayers().size(), 4);
        assertEquals(game.getPlayers().get(0).getHandTile().size(), 16);
        assertEquals(game.getPlayers().get(0).getDoorFront().size(), 4);
        assertEquals(game.getPlayers().get(1).getHandTile().size(), 16);
        assertEquals(game.getPlayers().get(1).getDoorFront().size(), 4);
        assertHandTiles(game.getPlayers().get(0), Arrays.asList("2筒", "3筒", "7筒"), Arrays.asList(13, 14, 15));
        assertHandTiles(game.getPlayers().get(1), Arrays.asList("5筒", "6筒", "8筒"), Arrays.asList(13, 14, 15));
    }

    private void assertHandTiles(Player player, List<String> expectedTiles) {
        for (int i = 0; i < player.getHandTile().size(); i++) {
            assertEquals(player.getHandTile().get(i), Tile.findTileByName(expectedTiles.get(i)));
        }
    }

    private void assertHandTiles(Player player, List<String> expectedTiles, List<Integer> tileIndices) {
        for (int i = 0; i < tileIndices.size(); i++) {
            assertEquals(player.getHandTile().get(tileIndices.get(i)), Tile.findTileByName(expectedTiles.get(i)));
        }
    }

    private static MahjongGame createGameSample(List<List<String>> playerTiles, List<String> testDeckTiles) {
        MahjongGame game = new MahjongGame();
        try (MockedStatic<Round> mocked = mockStatic(Round.class)) {
            Deck testDeck = new TestDeck(testDeckTiles);
            mocked.when(Round::deckFactory).thenReturn(testDeck);

            List<String> playerNames = Arrays.asList("1", "2", "3", "4");
            List<Player> players = IntStream.range(0, playerTiles.size())
                    .mapToObj(i -> {
                        Player player = new Player();
                        player.setName(playerNames.get(i));
                        playerTiles.get(i).stream()
                                .map(Tile::findTileByName)
                                .forEach(player::addHandTile);
                        return player;
                    }).toList();

            for (Player player : players) {
                game.addPlayer(player);
            }

            // when start the arranged game
            game.start();
        }
        return game;
    }
}

package tw.mahjong.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Deck {
    public static final int MAX_HAND_TILE = 17;
    @Getter
    public List<Tile> tile = null;

    private final Map<List<String>, Function<String, Tile>> deckTile = Map.of(DragonTile.typeName, DragonTile::new, WindsTile.typeName, WindsTile::new);

    public Deck() {
    }

    public void shuffle() {
        /**
         * Shuffle all tile and random them
         */
        this.tile = new ArrayList<>();
        for (int j = 0; j < Tile.QUANTITY; j++) {
            // 生成敘數牌
            for (String type : SuitTile.typeName) {
                for (int i = SuitTile.MIN_NUMBER; i <= SuitTile.MAX_NUMBER; i++) {
                    this.tile.add(new SuitTile(type, i));
                }
            }

            this.deckTile.forEach((typeNames, tileConstructor) -> {
                for (String type : typeNames) {
                    this.tile.add(tileConstructor.apply(type));
                }
            });
        }

        for (String type : BonusTile.BONUS_TILE_SEAT.keySet()) {
            this.tile.add(new BonusTile(type));
        }
        Collections.shuffle(this.tile);
    }

    public void drawTile(Player player) {
        if (player.getHandTile().size() >= MAX_HAND_TILE) {
            return;
        }

        player.addHandTile(this.tile.remove(0));
    }
}

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
    public List<Tile> tile;

    @Getter
    public List<Tile> discardTile = new ArrayList<>();

    private final Map<List<String>, Function<String, Tile>> tileFactoriesByType = Map.of(DragonTile.typeName, DragonTile::new, WindsTile.typeName, WindsTile::new);

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

            this.tileFactoriesByType.forEach((typeNames, tileConstructor) -> {
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

        player.addHandTile(this.getTile().remove(0));
    }

    public void drawEndOfTile(Player player) {
        player.addHandTile(this.getTile().remove(this.getTile().size() - 1));
    }

    public void addDiscardTile(Tile tile) {
        discardTile.add(tile);
    }

    public Tile getLastDiscardTile() {
        return discardTile.get(discardTile.size() - 1);
    }

    public void removeLastDiscardTile() {
        discardTile.remove(discardTile.size() - 1);
    }
}

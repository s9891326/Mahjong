package tw.mahjong.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Deck {
    @Getter
    public List<Tile> tile = new ArrayList<>();

    private final Map<List<String>, Function<String, Tile>> deckTile = Map.of(DragonTile.typeName, DragonTile::new, WindsTile.typeName, WindsTile::new);

    public Deck() {
        this.initDeck();
    }

    private void initDeck() {
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

        for (String type : BonusTile.flowers) {
            this.tile.add(new BonusTile(type));
        }

        for (String type : BonusTile.seasons) {
            this.tile.add(new BonusTile(type));
        }
    }
}

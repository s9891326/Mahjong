package tw.mahjong.discord.presenter;

import lombok.AllArgsConstructor;
import tw.mahjong.app.Presenter;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.Tile;
import tw.mahjong.domain.events.DomainEvent;
import tw.mahjong.domain.events.GameStatusEvent;

import java.util.List;

public class GameStatusPresenter extends Presenter {
    private GameStatusEvent event;

    @Override
    public void present(List<DomainEvent> events) {
        event = getEvent(events, GameStatusEvent.class).orElse(null);
    }

    @Override
    public Object asBotModel() {
        return null;
    }

    @Override
    public Object asBotModel(Object object) {
        String playerName = object.toString();
        Player player = event.game.findPlayerByName(playerName);
        return new GameStatusBotModel(event.game.getId(), player.getHandTile(), player.getDoorFront());
    }

    @AllArgsConstructor
    public static class GameStatusBotModel {
        public String gameId;
        public List<Tile> handTile;
        public List<Tile> doorFront;
    }
}

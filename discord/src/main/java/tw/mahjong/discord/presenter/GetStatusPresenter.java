package tw.mahjong.discord.presenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import tw.mahjong.app.Presenter;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.Round;
import tw.mahjong.domain.Tile;
import tw.mahjong.domain.events.DomainEvent;
import tw.mahjong.domain.events.GameStatusEvent;

import java.util.Deque;
import java.util.List;

public class GetStatusPresenter extends Presenter {
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
        return new GetStatusBotModel(event.game.getId(), player.getHandTile(), player.getDoorFront(), event.game.getRounds());
    }

    @Data
    @AllArgsConstructor
    public static class GetStatusBotModel {
        private String gameId;
        private List<Tile> handTile;
        private List<Tile> doorFront;
        private Deque<Round> rounds;

        public Round getLastRound() {
            return rounds.peekLast();
        }
    }
}

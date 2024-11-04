package tw.mahjong.domain.events;

import lombok.AllArgsConstructor;
import tw.mahjong.domain.MahjongGame;

@AllArgsConstructor
public class GameStatusEvent implements DomainEvent {
    public MahjongGame game;
}

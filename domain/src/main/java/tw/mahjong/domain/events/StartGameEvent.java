package tw.mahjong.domain.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartGameEvent implements DomainEvent {
    public boolean success;
}

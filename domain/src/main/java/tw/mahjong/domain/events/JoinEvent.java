package tw.mahjong.domain.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JoinEvent implements DomainEvent {
    public String gameId;
    public boolean success;
}

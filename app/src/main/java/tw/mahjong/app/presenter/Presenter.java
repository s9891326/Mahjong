package tw.mahjong.app.presenter;

import tw.mahjong.domain.events.DomainEvent;

import java.util.List;
import java.util.Optional;

public abstract class Presenter {
    public abstract void present(List<DomainEvent> events);

    public abstract Object asBotModel();

    @SuppressWarnings("unchecked")
    protected static <T extends DomainEvent> Optional<T> getEvent(List<DomainEvent> events,
                                                                  Class<T> type) {
        return events.stream()
                .filter(e -> type.isAssignableFrom(e.getClass()))
                .map(e -> (T) e)
                .findFirst();
    }
}

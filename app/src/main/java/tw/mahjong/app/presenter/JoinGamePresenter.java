package tw.mahjong.app.presenter;

import tw.mahjong.domain.events.DomainEvent;
import tw.mahjong.domain.events.JoinEvent;

import java.util.List;

public class JoinGamePresenter extends Presenter {
    private JoinEvent event;

    @Override
    public void present(List<DomainEvent> events) {
        event = getEvent(events, JoinEvent.class).orElse(null);

    }

    @Override
    public Object asBotModel() {
        return event.success;
    }
}

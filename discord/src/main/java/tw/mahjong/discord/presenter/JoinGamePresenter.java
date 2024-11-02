package tw.mahjong.discord.presenter;

import tw.mahjong.app.presenter.Presenter;
import tw.mahjong.domain.events.DomainEvent;
import tw.mahjong.domain.events.JoinEvent;

import java.util.List;

public class JoinGamePresenter extends Presenter {
    private JoinEvent event;

    @Override
    public void present(List<DomainEvent> events) {
        event = Presenter.getEvent(events, JoinEvent.class).orElse(null);

    }

    @Override
    public Object asBotModel() {
        return event.success;
    }
}

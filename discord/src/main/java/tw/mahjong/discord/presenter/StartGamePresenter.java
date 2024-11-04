package tw.mahjong.discord.presenter;

import tw.mahjong.app.Presenter;
import tw.mahjong.domain.events.DomainEvent;
import tw.mahjong.domain.events.StartGameEvent;

import java.util.List;

public class StartGamePresenter extends Presenter {
    private StartGameEvent event;

    @Override
    public void present(List<DomainEvent> events) {
        event = getEvent(events, StartGameEvent.class).orElse(null);
    }

    @Override
    public Object asBotModel() {
        return event.success;
    }
}

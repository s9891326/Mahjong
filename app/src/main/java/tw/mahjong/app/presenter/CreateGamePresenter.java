package tw.mahjong.app.presenter;

import tw.mahjong.domain.events.DomainEvent;
import tw.mahjong.domain.events.JoinEvent;

import java.util.List;

public class CreateGamePresenter extends Presenter {
    private JoinEvent event;

    @Override
    public void present(List<DomainEvent> events) {
        // 一個 command 可以有一個以上的事件
        event = getEvent(events, JoinEvent.class).orElse(null);
    }

    @Override
    public Object asBotModel() {
        return event.gameId;
    }
}

package tw.mahjong.discord.presenter;

import tw.mahjong.app.Presenter;
import tw.mahjong.domain.events.DomainEvent;
import tw.mahjong.domain.events.JoinEvent;

import java.util.List;

public class CreateGamePresenter extends Presenter {
    private JoinEvent event;

    @Override
    public void present(List<DomainEvent> events) {
        // 一個 command 可以有一個以上的事件
        event = Presenter.getEvent(events, JoinEvent.class).orElse(null);
    }

    @Override
    public Object asBotModel() {
        return event.gameId;
    }
}

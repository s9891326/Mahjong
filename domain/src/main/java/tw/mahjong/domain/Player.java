package tw.mahjong.domain;

import lombok.Getter;
import lombok.Setter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.List;


public class Player {
    @Getter
    @Setter
    public List<String> handCard = new ArrayList<>();

    public void addHandCard(String card) {
        this.handCard.add(card);
    }

    public void playCard(String card) throws MahjongException {
        if (this.handCard.contains(card)) {
            this.handCard.remove(card);
        } else {
            throw new MahjongException("沒這張卡");
        }
    }

    public boolean isFinish() {
        return this.handCard.size() == 16;
    }
}

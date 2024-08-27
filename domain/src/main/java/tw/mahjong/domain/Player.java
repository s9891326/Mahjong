package tw.mahjong.domain;

import lombok.Getter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.List;


public class Player {
    @Getter
    public List<Card> handCard = new ArrayList<>();

    public void setHandCard(List<String> handCards) {
        for (String card : handCards) {
            this.addHandCard(Card.findCardByName(card));
        }
    }

    public void addHandCard(Card card) {
        this.handCard.add(card);
    }

    public void playCard(String card) throws MahjongException {
        Card playedCard = Card.findCardByName(card);
        if (this.handCard.contains(playedCard)) {
            this.handCard.remove(playedCard);
        } else {
            throw new MahjongException("沒這張卡");
        }
    }

    public boolean isFinish() {
        return this.handCard.size() == 16;
    }
}

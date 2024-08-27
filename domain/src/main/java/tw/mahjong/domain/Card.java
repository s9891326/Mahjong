package tw.mahjong.domain;


import java.util.Objects;

public abstract class Card {
    public String value;

    public static Card findCardByName(String name) {
        String[] parts = name.split("");
        String pre = parts[0];
        String suf = parts[1];

        Card card = null;
        if (SuitCard.typeName.contains(suf)) {
            card = new SuitCard(suf);
        } else if (WindsCard.typeName.equals(suf)) {
            card = new WindsCard();
        } else if (DragonCard.typeName.contains(name)) {
            card = new DragonCard(name);
            pre = name;
        }
        card.setCardValue(pre);
        return card;
    }

    public void setCardValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(value, card.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getClass());
    }

    @Override
    public String toString() {
        return "Card{" +
                "value='" + value + '\'' +
                '}';
    }
}

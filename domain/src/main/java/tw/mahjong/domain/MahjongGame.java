package tw.mahjong.domain;

import lombok.Getter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.*;

public class MahjongGame {
    public static final int PLAYER_NUMS = 4;
    @Getter
    private final List<Player> players = new ArrayList<>();
    @Getter
    private final Deque<Round> rounds = new LinkedList<>();

    private int dealerIndex = 0;

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void start() {
        // todo: 玩家抓位子並依照東南西北把玩家排序
        if (this.players.size() < PLAYER_NUMS) {
            throw new MahjongException("遊玩人數不足4人");
        }
        next_round();
    }

    private void next_round() {
        Round round = new Round(this.players);
        round.setup(dealerIndex);
        rounds.add(round);
    }

    public Round getLastRound() {
        return rounds.peekLast();
    }

    public Round getSecondRound() {
        Iterator<Round> iterator = rounds.descendingIterator();
        if (iterator.hasNext()) { // 取得最後一個元素
            iterator.next();
        }
        Round secondLastRound = null;
        if (iterator.hasNext()) { // 取得倒數第二個元素
            secondLastRound = iterator.next();
        }
        return secondLastRound;
    }

    public void play(String playerName, Tile tile) {
        Player player = findPlayerByName(playerName);
        if (player != getLastRound().getTurnPlayer()) {
            throw new MahjongException("Player is not turn player");
        }

        player.playTile(tile);
        getLastRound().getDeck().addDiscardTile(tile);
    }

    private Player findPlayerByName(String playerName) {
        return players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + playerName));
    }

    public void win(String playerName, Tile tile) {
        Player turnPlayer = getLastRound().getTurnPlayer();
        Player player = findPlayerByName(playerName);

        if (getLastRound().findWinner(player, tile)) {
            // fixme: 之後根據排型來決定獲得多少point
            // 自摸(其他玩家都 -point)
            if (turnPlayer == player) {
                players.stream().filter(p -> p != turnPlayer).forEach(p -> p.point -= 1);
            } else {
                turnPlayer.point -= 1;
            }
            player.point += 1;

            System.out.println("winner: " + player);

            if (getLastRound().getDealer() != player) {
                dealerIndex += 1;
            }
            next_round();
        } else {
            throw new MahjongException("no winner");
        }
    }

    public void drawTile(String playerName) {
        Player player = findPlayerByName(playerName);
        getLastRound().deck.drawTile(player);
        getLastRound().setTurnPlayer(player);
    }

    public void chi(String playerName) {
        Player player = findPlayerByName(playerName);
        if (!isNextPlayer(player)) {
            throw new MahjongException("只能吃上家的牌");
        }

        player.chi(getLastRound().getDeck().getLastDiscardTile());
        getLastRound().setTurnPlayer(player);
        getLastRound().getDeck().removeLastDiscardTile();
    }

    private boolean isNextPlayer(Player player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == getLastRound().getTurnPlayer()) {
                return players.get((i + 1) % 4) == player;
            }
        }
        return false;
    }

    public void pong(String playerName) {
        Player player = findPlayerByName(playerName);
        player.pong(getLastRound().getDeck().getLastDiscardTile());
        getLastRound().setTurnPlayer(player);
        getLastRound().getDeck().removeLastDiscardTile();
    }

    public void kong(String playerName, Tile tile) {
        Player player = findPlayerByName(playerName);
        if (tile != null) {
            // 暗槓
            player.kong(tile, false);
        } else {
            if (isNextPlayer(player)) {
                throw new MahjongException("不能槓上家的牌");
            }
            player.kong(getLastRound().getDeck().getLastDiscardTile(), true);
            getLastRound().getDeck().removeLastDiscardTile();
        }

        // 從牌尾抽牌
        getLastRound().deck.drawEndOfTile(player);
        getLastRound().setTurnPlayer(player);
    }
}

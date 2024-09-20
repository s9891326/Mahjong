package tw.mahjong.domain;

import lombok.Getter;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MahjongGame {
    public static final int PLAYER_NUMS = 4;
    @Getter
    private final List<Player> players = new ArrayList<>();
    @Getter
    private final Deque<Round> rounds = new LinkedList<>();

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void start() {
        if (this.players.size() < PLAYER_NUMS) {
            throw new MahjongException("遊玩人數不足4人");
        }
        next_round();
    }

    private void next_round() {
        rounds.add(new Round(this.players));
    }

    public Round getLastRound() {
        return rounds.peekLast();
    }
}

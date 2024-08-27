package tw.mahjong.domain.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {
    private final Player player;

    public StepDefinitions(Player player) {
        this.player = player;
    }

    @Given("自己手牌有 {string}")
    public void setupHands(String handCards) {
        List<String> handCard = Arrays.stream(handCards.split("、")).collect(Collectors.toList());
        player.setHandCard(handCard);
        System.out.println("hand cards:" + player.getHandCard());
    }

    @When("出一張 {string}")
    public void playCard(String card) {
        try {
            player.playCard(card);
            System.out.println("play: " + card);
        } catch (MahjongException e) {
            System.out.println(e.getMessage());
        }
    }

    @Then("出牌成功")
    public void successPlayCard() {
        assertTrue(player.isFinish());
        System.out.println("hand cards:" + player.getHandCard());
    }
}

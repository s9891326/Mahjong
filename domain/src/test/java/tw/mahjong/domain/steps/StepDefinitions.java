package tw.mahjong.domain.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {
    private final Player player;

    public StepDefinitions(Player player) {
        this.player = player;
    }

    @Given("自己手牌有 {string}")
    public void setupHands(String handTiles) {
        List<String> handTile = Arrays.stream(handTiles.split("、")).collect(Collectors.toList());
        player.setHandTile(handTile);
        System.out.println("hand tiles:" + player.getHandTile());
    }

    @When("出一張 {string}")
    public void playTile(String tile) {
        try {
            player.playTile(tile);
            System.out.println("play: " + tile);
        } catch (MahjongException e) {
            System.out.println(e.getMessage());
        }
    }

    @Then("出牌成功")
    public void successPlayTile() {
        assertTrue(player.isSuccessPlayTile());
        System.out.println("hand tiles:" + player.getHandTile());
    }

    @When("系統發了一張 {string}")
    public void drawTile(String tile) {
        player.addHandTile(tile);
    }

    @Then("摸牌成功")
    public void successDrawTile() {
        assertTrue(player.isSuccessDrawTile());
        System.out.println("hand tiles:" + player.getHandTile());
    }

    @When("{string} 打了 {string} 自己喊吃")
    public void chiTile(String otherPlayer, String tile) {
        if (otherPlayer.equals("上家")) {
            player.chi(tile);
        } else {
            System.out.println("只能吃上家打的牌");
        }
    }

    @Then("吃牌成功")
    public void successChiTile() {
        /*從手牌-3張，亮出來的為順子(系統亮牌132萬)*/
        System.out.println("hand titles: " + player.getHandTile());
        System.out.println("door front: " + player.getDoorFront());
        assertEquals(13, player.getHandTile().size());
        assertEquals(3, player.getDoorFront().size());
    }

    @Then("吃牌失敗")
    public void failChiTile() {
        assertEquals(16, player.getHandTile().size());
        assertEquals(0, player.getDoorFront().size());
    }
}

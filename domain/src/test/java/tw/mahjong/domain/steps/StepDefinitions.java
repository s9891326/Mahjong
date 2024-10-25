package tw.mahjong.domain.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.SuitTile;
import tw.mahjong.domain.Tile;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {
    private final Player player;

    public StepDefinitions(Player player) {
        this.player = player;
    }

    @Given("自己手牌有 {string}")
    public void setupHands(String handTiles) {
        List<String> handTile = Arrays.stream(handTiles.split("、")).toList();
        for (String tile : handTile) {
            player.addHandTile(Tile.findTileByName(tile));
        }
        System.out.println("hand tiles:" + player.getHandTile());
    }

    @When("出一張 {string}")
    @Then("自己打了一張 {string}")
    public void playTile(String tile) {
        try {
            player.playTile(Tile.findTileByName(tile));
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
        player.addHandTile(Tile.findTileByName(tile));
    }

    @Then("摸牌成功")
    public void successDrawTile() {
        assertTrue(player.isSuccessDrawTile());
        System.out.println("hand tiles:" + player.getHandTile());
    }

    @When("上家打了 {string} 自己喊吃")
    public void chiTile(String tile) {
        player.chi(Tile.findTileByName(tile));
    }

    @Then("吃牌成功")
    public void successChiTile() {
        /*從手牌-3張，亮出來的為順子(系統亮牌132萬)*/
        System.out.println("hand titles: " + player.getHandTile());
        System.out.println("door front: " + player.getDoorFront());
        assertEquals(13, player.getHandTile().size());
        assertEquals(3, player.getDoorFront().size());
        SuitTile firstDoorFront = (SuitTile) player.getDoorFront().get(0);
        int[] optionNum = {firstDoorFront.getNumber() + 1, firstDoorFront.getNumber() + 2, firstDoorFront.getNumber() - 1, firstDoorFront.getNumber() - 2};
//        for (int i = 1; i < player.getDoorFront().size(); i++) {
//            int num = ((SuitTile) player.getDoorFront().get(i)).getNumber();
//            assertTrue(Arrays.stream(optionNum).anyMatch(n -> n == num));
//        }

        player.getDoorFront().stream()
                .skip(1)
                .map(tile -> ((SuitTile) tile).getNumber())
                .forEach(num -> assertTrue(Arrays.stream(optionNum).anyMatch(n -> n == num)));
    }

    @Then("吃牌失敗")
    public void failChiTile() {
        assertEquals(16, player.getHandTile().size());
        assertEquals(0, player.getDoorFront().size());
    }

    @When("{string} 打了 {string} 自己喊碰")
    public void pongTile(String otherPlayer, String tile) {
        player.pong(Tile.findTileByName(tile));
    }

    @Then("碰牌成功")
    public void successPongTile() {
        /*從手牌-3張，亮出來的為刻子(系統亮牌111萬)*/
        System.out.println("hand titles: " + player.getHandTile());
        System.out.println("door front: " + player.getDoorFront());
        assertEquals(13, player.getHandTile().size());
        assertEquals(3, player.getDoorFront().size());

        Tile firstDoorFront = player.getDoorFront().get(0);
        if (firstDoorFront instanceof SuitTile suitTile) {
            player.getDoorFront().stream()
                    .skip(1)
                    .map(tile -> (SuitTile) tile)
                    .forEach(tile -> {
                        assertEquals(tile.getNumber(), suitTile.getNumber());
                        assertEquals(tile.getType(), suitTile.getType());
                    });
        } else {
            player.getDoorFront().stream()
                    .skip(1)
                    .forEach(tile -> assertEquals(tile.getValue(), firstDoorFront.getValue()));
        }
    }

//    @When("{string} 打了 {string} 自己喊吃但其他玩家喊了 {string}")
//    public void chiTileButHappenSomething(String otherPlayer, String tile, String action) {
//        player.chi(otherPlayer, Tile.findTileByName(tile), action);
//    }
}

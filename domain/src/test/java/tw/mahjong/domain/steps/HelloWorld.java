package tw.mahjong.domain.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorld {
    private int hour;

    @Given("I have {int} kidney in my belly")
    public void I_have_kidney_in_my_belly(int kidney) {
        System.out.println("I_have_kidney_in_my_belly:" + kidney);
    }

    @When("I wait {int} hour")
    public void ttt(int hour) {
        this.hour = hour;
    }

    @Then("my belly should grow {int}")
    public void then(int hour) {
        assertEquals(this.hour, hour);
    }
}

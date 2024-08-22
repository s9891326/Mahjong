package mahjong;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MahjongApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("spring test 123");
        assertEquals(1, 1);
    }
}

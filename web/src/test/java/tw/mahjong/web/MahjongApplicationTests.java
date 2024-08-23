package tw.mahjong.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MahjongApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("spring test 123456");
        assertEquals(1, 1);
    }
}

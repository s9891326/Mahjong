package tw.mahjong.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tw.mahjong.domain.MyUtils;

@SpringBootApplication
public class MahjongApplication {
    public static void main(String[] args) {
        MyUtils.printName("eeddddy");
        SpringApplication.run(MahjongApplication.class, args);
    }
}

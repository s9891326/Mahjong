package tw.mahjong.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import tw.mahjong.domain.MyUtils;

@RestController
public class MyController {
    private int count = 0;

    @RequestMapping("/test")
    public String test() {
        count++;
//        System.out.println("count: " + MyUtils.printName("22222222222"));
        System.out.println("2: " + count);
        return "123456";
    }
}

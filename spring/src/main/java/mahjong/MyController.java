package mahjong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @RequestMapping("/test")
    public String test() {
        System.out.println("hello");
        return "Hello";
    }
}

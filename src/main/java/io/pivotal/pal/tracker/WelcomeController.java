package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class WelcomeController {
    @GetMapping("/")
    public String sayHello() {
        return "hello";
    }
}

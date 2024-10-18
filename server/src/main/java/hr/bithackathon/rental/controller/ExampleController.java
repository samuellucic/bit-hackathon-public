package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.aspect.HasAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExampleController {

    @GetMapping
    public String hello() {
        return "Hello World!";
    }

    @HasAuthority(AuthoritiesConstants.ADMIN)
    @GetMapping("/1")
    public String hello2() {
        return "Check authority";
    }
}

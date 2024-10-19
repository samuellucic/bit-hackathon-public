package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final MailService mailService;

    @GetMapping("/mail")
    public void test() {
        mailService.sendSimpleMessage("filip.pankretic@gmail.com", "Test", "Test");
    }

}

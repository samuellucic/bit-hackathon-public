package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.config.FreeReservationConfiguration;
import hr.bithackathon.rental.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final FreeReservationConfiguration freeReservationConfiguration;
    private final ContractService contractService;

    @GetMapping
    public Long test() {
        return contractService.dummy();
    }

}

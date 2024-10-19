package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.Contract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankService {

    public boolean isPaid() {
        return (new Random()).nextDouble() > 0.5;
    }

    public void returnDownPayment(Contract contract) {
        log.info("Returning down payment for contract with id {}", contract.getId());
    }
}

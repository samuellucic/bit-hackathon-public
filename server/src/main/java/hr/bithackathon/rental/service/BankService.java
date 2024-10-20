package hr.bithackathon.rental.service;

import java.util.Random;

import hr.bithackathon.rental.domain.Contract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankService {

    public void returnDownPayment(Contract contract) {
        log.info("Returning down payment for contract with id {}", contract.getId());
    }

}

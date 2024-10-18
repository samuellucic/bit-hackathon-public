package hr.bithackathon.rental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankService {

    public boolean isPaid() {
        return (new Random()).nextDouble() > 0.5;
    }

}

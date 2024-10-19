package hr.bithackathon.rental.service;

import java.util.Random;

import hr.bithackathon.rental.domain.ContractStatus;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {

    public boolean isPaid() {
        return (new Random()).nextDouble() > 0.5;
    }

}

package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.config.FreeReservationConfiguration;
import hr.bithackathon.rental.domain.ReservationType;
import hr.bithackathon.rental.domain.dto.PayContractRequest;
import hr.bithackathon.rental.domain.dto.RecordBookAddRequest;
import hr.bithackathon.rental.domain.dto.SignContractRequest;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.aspect.HasAuthority;
import hr.bithackathon.rental.service.ContractService;
import hr.bithackathon.rental.service.RecordBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action/contract")
@RequiredArgsConstructor
public class ContractActionController {

    private final ContractService contractService;
    private final RecordBookService recordBookService;
    private final FreeReservationConfiguration freeReservationConfiguration;

    @PostMapping("/sign-user")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public void signContractByUser(@Valid @RequestBody SignContractRequest request) {
        contractService.signContractByCustomer(request.contractId());
    }

    @PostMapping("/sign-mayor")
    @HasAuthority(AuthoritiesConstants.MAYOR)
    public void signContractByMajor(@Valid @RequestBody SignContractRequest request) {
        contractService.signContractByMajor(request.contractId());

        var contract = contractService.getContract(request.contractId());
        var reservationType = contract.getReservation().getType();
        if (freeReservationConfiguration.isFreeReservationType(reservationType)) {
            payContract(new PayContractRequest(request.contractId()));
        }
    }

    @PostMapping("/pay")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public void payContract(@Valid @RequestBody PayContractRequest request) {
        contractService.finalizeContract(request.contractId());
        var recordBookRequest = new RecordBookAddRequest(request.contractId(), null);
        recordBookService.createRecordBook(recordBookRequest);
    }

}

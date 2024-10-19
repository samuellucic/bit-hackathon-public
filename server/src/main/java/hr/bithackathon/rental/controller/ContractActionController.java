package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.dto.PayContractRequest;
import hr.bithackathon.rental.domain.dto.SignContractRequest;
import hr.bithackathon.rental.service.BankService;
import hr.bithackathon.rental.service.ContractService;
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
    private final BankService bankService;

    @PostMapping("/sign-user")
    public void signContractByUser(@RequestBody SignContractRequest request) {
        contractService.signContractByCustomer(request.contractId());
    }

    @PostMapping("/sign-mayor")
    public void signContractByMajor(@RequestBody SignContractRequest request) {
        contractService.signContractByMajor(request.contractId());
    }

    @PostMapping("/pay")
    public void payContract(@RequestBody PayContractRequest request) {
        contractService.finalizeContract(request.contractId());
    }

}

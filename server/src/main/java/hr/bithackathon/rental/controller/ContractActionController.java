package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.dto.PayContractRequest;
import hr.bithackathon.rental.domain.dto.SignContractRequest;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.aspect.HasAuthority;
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

    @PostMapping("/sign-user")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public void signContractByUser(@RequestBody SignContractRequest request) {
        contractService.signContractByCustomer(request.contractId());
    }

    @PostMapping("/sign-mayor")
    @HasAuthority(AuthoritiesConstants.MAYOR)
    public void signContractByMajor(@RequestBody SignContractRequest request) {
        contractService.signContractByMajor(request.contractId());
    }

    @PostMapping("/pay")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public void payContract(@RequestBody PayContractRequest request) {
        contractService.finalizeContract(request.contractId());
    }

}

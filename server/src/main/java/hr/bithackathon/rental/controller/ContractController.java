package hr.bithackathon.rental.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import hr.bithackathon.rental.domain.ContractStatus;
import hr.bithackathon.rental.domain.dto.ContractResponse;
import hr.bithackathon.rental.domain.dto.PaginationResponse;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.SecurityUtils;
import hr.bithackathon.rental.security.aspect.HasAuthority;
import hr.bithackathon.rental.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/{contractId}")
    @HasAuthority({ AuthoritiesConstants.CUSTOMER, AuthoritiesConstants.MAYOR, AuthoritiesConstants.OFFICIAL })
    public ContractResponse getContract(@PathVariable Long contractId) {
        if (SecurityUtils.isLoggedInCustomer()) {
            return ContractResponse.of(contractService.getContractAndCheckUserId(contractId, SecurityUtils.getCurrentUserDetails().getId()));
        }
        return ContractResponse.of(contractService.getContract(contractId));
    }

    @GetMapping()
    @HasAuthority({ AuthoritiesConstants.CUSTOMER, AuthoritiesConstants.MAYOR, AuthoritiesConstants.OFFICIAL })
    public PaginationResponse<ContractResponse> getAllContract(@RequestParam(required = false) ContractStatus status, Pageable page) {
        if (SecurityUtils.isLoggedInCustomer()) {
            if (status == null) {
                return PaginationResponse.fromPage(contractService.findAllContractsByCustomerIdAndPage(SecurityUtils.getCurrentUserDetails().getId(), page),
                                                   ContractResponse::of);
            } else {
                var customerId = SecurityUtils.getCurrentUserDetails().getId();
                return PaginationResponse.fromPage(contractService.findAllContractsByCustomerIdAndStatusAndPage(status, customerId, page),
                                                   ContractResponse::of);
            }
        }

        if (status == null) {
            return PaginationResponse.fromPage(contractService.findAllContractsByPage(page), ContractResponse::of);
        } else {
            return PaginationResponse.fromPage(contractService.findAllContractsByStatus(status, page), ContractResponse::of);
        }
    }

    @GetMapping("/doc")
    public ResponseEntity<InputStreamResource> file() {
        var docFile = contractService.getContractDocument();

        try {
            FileInputStream fis = new FileInputStream(docFile);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template_ugovor.doc");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            InputStreamResource resource = new InputStreamResource(fis);

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .contentLength(docFile.length())
                                 .contentType(MediaType.parseMediaType("application/msword"))
                                 .body(resource);
        } catch (FileNotFoundException e) {
            throw new RentalException(ErrorCode.CANT_CREATE_CONTRACT);
        }
    }

}

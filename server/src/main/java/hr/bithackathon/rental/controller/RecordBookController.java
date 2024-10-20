package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.RecordBookStatus;
import hr.bithackathon.rental.domain.dto.*;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.SecurityUtils;
import hr.bithackathon.rental.security.aspect.HasAuthority;
import hr.bithackathon.rental.service.RecordBookService;
import hr.bithackathon.rental.util.Util;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RecordBookController {

    private RecordBookService recordBookService;

    @PostMapping("/record-books")
    @ResponseStatus(HttpStatus.CREATED)
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public ResponseEntity<Void> createRecordBook(@Valid @RequestBody RecordBookAddRequest recordBookAddRequest) {
        var id = recordBookService.createRecordBook(recordBookAddRequest);
        return Util.getCreateResponse(id);
    }

    @PatchMapping("/record-books/{id}/before")
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public RecordBookResponse updateRecordBookBefore(@PathVariable("id") Long id,
            @Valid @RequestBody RecordBookBeforeEditRequest recordBookBeforeEditRequest) {
        return RecordBookResponse.from(recordBookService.updateRecordBookBefore(id, recordBookBeforeEditRequest));
    }

    @PatchMapping(value = "/record-books/{id}/after")
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public RecordBookResponse updateRecordBook(@PathVariable("id") Long id,
            @Valid @RequestBody RecordBookAfterEditRequest recordBookAfterEditRequest) {
        return RecordBookResponse.from(recordBookService.updateRecordBookAfter(id, recordBookAfterEditRequest));
    }

    @GetMapping(value = "/record-books/{id}")
    public RecordBookResponse getRecordBook(@PathVariable("id") Long id) {
        if (SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.CUSTODIAN)) {
            return RecordBookResponse.from(recordBookService.getRecordBookForCustodian(id));
        } else {
            return RecordBookResponse.from(recordBookService.getRecordBookForCustomer(id));
        }
    }

    @GetMapping(value = "/record-books")
    @HasAuthority({ AuthoritiesConstants.CUSTODIAN, AuthoritiesConstants.CUSTOMER })
    public PaginationResponse<RecordBookResponse> getAllRecordBooks(
            @RequestParam(value = "status", required = false) RecordBookStatus status,
            Pageable pageable) {
        var appUser = SecurityUtils.getCurrentUserDetails();
        if (SecurityUtils.isLoggedInCustomer()) {
            if (status == null) {
                return PaginationResponse.fromPage(recordBookService.findAllByCustomerId(appUser.getId(), pageable),
                        RecordBookResponse::from);
            } else {
                return PaginationResponse.fromPage(
                        recordBookService.findAllByCustomerIdAndStatus(appUser.getId(), status, pageable),
                        RecordBookResponse::from);
            }
        }
        if (status == null) {
            return PaginationResponse.fromPage(recordBookService.findAllByCustodianId(appUser.getId(), pageable),
                    RecordBookResponse::from);
        } else {
            return PaginationResponse.fromPage(
                    recordBookService.findAllByCustodianIdAndStatus(appUser.getId(), status, pageable),
                    RecordBookResponse::from);
        }
    }

    @PostMapping("/action/record-books/sign")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public void signRecordBook(@Valid @RequestBody SignRecordBookRequest signRecordBookRequest) {
        recordBookService.signRecordBook(signRecordBookRequest);
    }

    @PostMapping(value = "/action/record-books/down-payment")
    @HasAuthority(AuthoritiesConstants.OFFICIAL)
    public void handleDownPayment(@Valid @RequestBody HandleDownPaymentRequest handleDownPaymentRequest) {
        recordBookService.handleDownPayment(handleDownPaymentRequest);
    }

}

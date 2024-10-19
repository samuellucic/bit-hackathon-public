package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.dto.*;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.SecurityUtils;
import hr.bithackathon.rental.security.aspect.HasAuthority;
import hr.bithackathon.rental.service.RecordBookService;
import hr.bithackathon.rental.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RecordBookController {

    private RecordBookService recordBookService;

    @PostMapping("/record-books")
    @ResponseStatus(HttpStatus.CREATED)
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public ResponseEntity<Void> createRecordBook(@RequestBody RecordBookAddRequest recordBookAddRequest) {
        var id = recordBookService.createRecordBook(recordBookAddRequest);
        return Util.getCreateResponse(id);
    }

    @PatchMapping(value = "/record-books/{id}")
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public RecordBookResponse updateRecordBook(@PathVariable("id") Long id, @RequestBody RecordBookEditRequest recordBookEditRequest) {
        return RecordBookResponse.fromRecordBook(recordBookService.updateRecordBook(id, recordBookEditRequest));
    }

    @GetMapping(value = "/record-books/{id}")
    @HasAuthority({AuthoritiesConstants.CUSTODIAN, AuthoritiesConstants.CUSTOMER})
    public RecordBookResponse getRecordBook(@PathVariable("id") Long id) {
        if (SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.CUSTODIAN)){
            return RecordBookResponse.fromRecordBook(recordBookService.getRecordBookForCustodian(id));
        } else {
            return RecordBookResponse.fromRecordBook(recordBookService.getRecordBookForCustomer(id));
        }
    }

    @GetMapping(value = "/record-books", params = {"page", "size"})
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public PaginationResponse<RecordBookResponse> getAllRecordBooks(Pageable pageable) {
        return PaginationResponse.fromPage(recordBookService.getAllRecordBooks(pageable), RecordBookResponse::fromRecordBook);
    }

    @PostMapping("/action/record-books/sign")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public void signRecordBook(@RequestBody SignRecordBookRequest signRecordBookRequest) {
        recordBookService.signRecordBook(signRecordBookRequest);
    }

    @PostMapping(value = "/action/record-books/down-payment")
    @HasAuthority(AuthoritiesConstants.OFFICIAL)
    public void handleDownPayment(@RequestBody HandleDownPaymentRequest handleDownPaymentRequest) {
        recordBookService.handleDownPayment(handleDownPaymentRequest);
    }
}

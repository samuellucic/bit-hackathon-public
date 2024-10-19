package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.dto.HandleDownPaymentRequest;
import hr.bithackathon.rental.domain.dto.PaginationResponse;
import hr.bithackathon.rental.domain.dto.RecordBookAddRequest;
import hr.bithackathon.rental.domain.dto.RecordBookEditRequest;
import hr.bithackathon.rental.domain.dto.RecordBookResponse;
import hr.bithackathon.rental.domain.dto.SignRecordBookRequest;
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

    @PatchMapping(value = "/record-books/{id}")
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public RecordBookResponse updateRecordBook(@PathVariable("id") Long id, @Valid  @RequestBody RecordBookEditRequest recordBookEditRequest) {
        return RecordBookResponse.fromRecordBook(recordBookService.updateRecordBook(id, recordBookEditRequest));
    }

    @GetMapping(value = "/record-books/{id}")
    @HasAuthority({ AuthoritiesConstants.CUSTODIAN, AuthoritiesConstants.CUSTOMER })
    public RecordBookResponse getRecordBook(@PathVariable("id") Long id) {
        if (SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.CUSTODIAN)) {
            return RecordBookResponse.fromRecordBook(recordBookService.getRecordBookForCustodian(id));
        } else {
            return RecordBookResponse.fromRecordBook(recordBookService.getRecordBookForCustomer(id));
        }
    }

    @GetMapping(value = "/record-books", params = { "page", "size" })
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public PaginationResponse<RecordBookResponse> getAllRecordBooks(Pageable pageable) {
        return PaginationResponse.fromPage(recordBookService.getAllRecordBooks(pageable), RecordBookResponse::fromRecordBook);
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

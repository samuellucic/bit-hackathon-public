package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.dto.*;
import hr.bithackathon.rental.security.AuthoritiesConstants;
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

    @PostMapping("/record-book")
    @ResponseStatus(HttpStatus.CREATED)
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public ResponseEntity<Void> createRecordBook(@RequestBody RecordBookAddRequest recordBookAddRequest) {
        var id = recordBookService.createRecordBook(recordBookAddRequest);
        return Util.getCreateResponse(id);
    }

    @PatchMapping(value = "/record-book/{id}")
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public RecordBookResponse updateRecordBook(@PathVariable("id") Long id, @RequestBody RecordBookEditRequest recordBookEditRequest) {
        return RecordBookResponse.fromRecordBook(recordBookService.updateRecordBook(id, recordBookEditRequest));
    }

    @GetMapping(value = "/record-book", params = {"page", "size"})
    @HasAuthority(AuthoritiesConstants.CUSTODIAN)
    public PaginationResponse<RecordBookResponse> getAllReservations(Pageable pageable) {
        return PaginationResponse.fromPage(recordBookService.getAllRecordBooks(pageable), RecordBookResponse::fromRecordBook);
    }

    @PostMapping("/action/record-book/sign")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public void signRecordBook(@RequestBody SignRecordBookRequest signRecordBookRequest) {
        recordBookService.signRecordBook(signRecordBookRequest);
    }
}

package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.RecordBook;
import hr.bithackathon.rental.domain.RecordBookStatus;
import hr.bithackathon.rental.domain.dto.RecordBookAddRequest;
import hr.bithackathon.rental.domain.dto.RecordBookEditRequest;
import hr.bithackathon.rental.domain.dto.SignRecordBookRequest;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.RecordBookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecordBookService {
    private RecordBookRepository recordBookRepository;
    private AppUserService appUserService;
    private ContractService contractService;

    public Long createRecordBook(RecordBookAddRequest recordBookAddRequest) {
        var contract = contractService.getContract(recordBookAddRequest.contractId());
        var custodian = appUserService.getCurrentAppUser();
        var recordBook = RecordBookAddRequest.toRecordBook(recordBookAddRequest, contract, custodian);
        recordBook = recordBookRepository.save(recordBook);
        return recordBook.getId();
    }

    public RecordBook getRecordBook(Long id) {
        return recordBookRepository.findById(id).orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
    }

    public RecordBook updateRecordBook(Long recordBookId, RecordBookEditRequest recordBookEditRequest) {
        var recordBook = getRecordBook(recordBookId);
        if (!recordBookEditRequest.stateAfter().isBlank()) {
            recordBook.setStateAfter(recordBookEditRequest.stateAfter());
        }
        if (!recordBookEditRequest.damage().isBlank()) {
            recordBook.setDamage(recordBookEditRequest.damage());
        }
        recordBook.setInspectionDate(LocalDate.now());
        return recordBookRepository.save(recordBook);
    }

    public Page<RecordBook> getAllRecordBooks(Pageable pageable) {
        return recordBookRepository.findAll(pageable);
    }

    public void signRecordBook(SignRecordBookRequest signRecordBookRequest) {
        RecordBook recordBook = recordBookRepository.findByIdAndCustomerId(signRecordBookRequest.recordBookId(), appUserService.getCurrentAppUser().getId())
                .orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
        recordBook.setStatus(RecordBookStatus.SIGNED);
    }

}

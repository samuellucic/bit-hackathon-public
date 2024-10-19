package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.RecordBook;
import hr.bithackathon.rental.domain.RecordBookStatus;
import hr.bithackathon.rental.domain.dto.HandleDownPaymentRequest;
import hr.bithackathon.rental.domain.dto.RecordBookAddRequest;
import hr.bithackathon.rental.domain.dto.RecordBookEditRequest;
import hr.bithackathon.rental.domain.dto.SignRecordBookRequest;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.RecordBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordBookService {
    private final BankService bankService;
    private final RecordBookRepository recordBookRepository;
    private final AppUserService appUserService;
    private final ContractService contractService;

    public Long createRecordBook(RecordBookAddRequest recordBookAddRequest) {
        var contract = contractService.getContractForCustodian(recordBookAddRequest.contractId());
        var custodian = appUserService.getCurrentAppUser();
        var recordBook = RecordBookAddRequest.toRecordBook(recordBookAddRequest, contract, custodian);
        recordBook = recordBookRepository.save(recordBook);
        return recordBook.getId();
    }

    public RecordBook getRecordBook(Long recordBookId) {
        return recordBookRepository.findByIdAndCustodianId(recordBookId, appUserService.getCurrentAppUser().getId()
                ).orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
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

    public Page<RecordBook> getAllRecordBooksByCustodian(Pageable pageable) {
        return recordBookRepository.findAllByCustodianId(appUserService.getCurrentAppUser().getId(), pageable);
    }


    public void signRecordBook(SignRecordBookRequest signRecordBookRequest) {
        var recordBook = recordBookRepository.findByIdAndCustomerIdAndStatus(signRecordBookRequest.recordBookId(), appUserService.getCurrentAppUser().getId(), RecordBookStatus.CREATED)
                .orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
        recordBook.setStatus(RecordBookStatus.SIGNED);
        recordBookRepository.save(recordBook);
    }

    public void handleDownPayment(HandleDownPaymentRequest handleDownPaymentRequest) {
        var recordBook = recordBookRepository.findByIdAndStatus(handleDownPaymentRequest.recordBookId(), RecordBookStatus.SIGNED)
                .orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
        if (handleDownPaymentRequest.returnDownPayment()) {
            recordBook.setStatus(RecordBookStatus.DOWN_PAYMENT_RETURNED);
            bankService.returnDownPayment(recordBook.getContract());
        } else {
            recordBook.setStatus(RecordBookStatus.DOWN_PAYMENT_FORFEITED);
        }
        recordBookRepository.save(recordBook);
    }
}

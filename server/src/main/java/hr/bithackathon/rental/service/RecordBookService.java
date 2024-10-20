package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.RecordBook;
import hr.bithackathon.rental.domain.RecordBookStatus;
import hr.bithackathon.rental.domain.dto.*;
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
    private final NotificationService notificationService;

    public Long createRecordBook(RecordBookAddRequest recordBookAddRequest) {
        var contract = contractService.getContract(recordBookAddRequest.contractId());
        var custodian = contract.getReservation().getCommunityHomePlan().getCommunityHome().getCustodians().get(0);
        var recordBook = RecordBookAddRequest.toRecordBook(recordBookAddRequest, contract, custodian);
        recordBook = recordBookRepository.save(recordBook);
        return recordBook.getId();
    }

    public RecordBook getRecordBookForCustodian(Long recordBookId) {
        return recordBookRepository.findByIdAndCustodianId(recordBookId, appUserService.getCurrentAppUser().getId()
                                                          ).orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
    }

    public RecordBook getRecordBookForCustomer(Long recordBookId) {
        return recordBookRepository.findByIdAndCustomerId(recordBookId, appUserService.getCurrentAppUser().getId()
                                                         ).orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
    }

    public RecordBook getRecordBook(Long recordBookId) {
        return recordBookRepository.findById(recordBookId).orElseThrow(() -> new RentalException(ErrorCode.RECORD_BOOK_NOT_FOUND));
    }

    public RecordBook updateRecordBookAfter(Long recordBookId, RecordBookAfterEditRequest recordBookAfterEditRequest) {
        var recordBook = getRecordBookForCustodian(recordBookId);
        recordBook.setStateAfter(recordBookAfterEditRequest.stateAfter());
        recordBook.setDamage(recordBookAfterEditRequest.damage());
        recordBook.setStatus(RecordBookStatus.FILLED_AFTER);
        recordBook.setInspectionDate(LocalDate.now());
        var updatedRecordBook = recordBookRepository.save(recordBook);
        notificationService.notifyCustomerForRecord(recordBook.getId());
        return updatedRecordBook;
    }

    public RecordBook updateRecordBookBefore(Long recordBookId, RecordBookBeforeEditRequest recordBookBeforeEditRequest) {
        var recordBook = getRecordBookForCustodian(recordBookId);
        recordBook.setStateBefore(recordBookBeforeEditRequest.stateBefore());
        recordBook.setStatus(RecordBookStatus.FILLED_BEFORE);
        var updatedRecordBook = recordBookRepository.save(recordBook);
        notificationService.notifyCustomerForRecord(recordBook.getId());
        return updatedRecordBook;
    }


    public Page<RecordBook> getAllRecordBooks(RecordBookStatus status, Pageable pageable) {
        return recordBookRepository.findAllByStatus(status, pageable);
    }

    public Page<RecordBook> findAllByCustomerId(Long customerId, Pageable pageable) {
        return recordBookRepository.findAllByCustomerId(customerId, pageable);
    }

    public Page<RecordBook> findAllByCustomerIdAndStatus(Long customerId, RecordBookStatus status, Pageable pageable) {
        return recordBookRepository.findAllByCustomerIdAndStatus(customerId, status, pageable);
    }

    public Page<RecordBook> findAllByCustodianId(Long custodianId, Pageable pageable) {
        return recordBookRepository.findAllByCustodianId(custodianId, pageable);
    }

    public Page<RecordBook> findAllByCustodianIdAndStatus(Long custodianId, RecordBookStatus status, Pageable pageable) {
        return recordBookRepository.findAllByCustodianIdAndStatus(custodianId, status, pageable);
    }

    public void signRecordBook(SignRecordBookRequest signRecordBookRequest) {
        var recordBook = recordBookRepository.findByIdAndCustomerIdAndStatus(signRecordBookRequest.recordBookId(),
                                                                             appUserService.getCurrentAppUser().getId(),
                                                                             RecordBookStatus.FILLED_AFTER)
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

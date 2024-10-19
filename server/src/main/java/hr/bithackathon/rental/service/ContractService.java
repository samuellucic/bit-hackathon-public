package hr.bithackathon.rental.service;

import hr.bithackathon.rental.config.FreeReservationConfiguration;
import hr.bithackathon.rental.domain.Contract;
import hr.bithackathon.rental.domain.ContractStatus;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final FreeReservationConfiguration freeReservationConfiguration;
    private final ContractRepository contractRepository;
    private final NotificationService notificationService;
    private final BankService bankService;
    private final ResourceLoader resourceLoader;
    private final ReservationService reservationService;

    static final Double VAT = 0.25;

    public Contract getContract(Long contractId) {
        return contractRepository.findById(contractId).orElseThrow(() -> new RentalException(ErrorCode.CONTRACT_NOT_FOUND));
    }

    public List<Contract> findAllContractsByCustomerId(Long customerId) {
        return contractRepository.findAllByCustomerId(customerId);
    }

    public List<Contract> findAllContractsByCustomerIdAndPage(Long customerId, int page, int size) {
        return contractRepository.findAllByCustomerId(customerId, PageRequest.of(page, size));
    }

    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    // TODO: Later when you are ready to implement this logic, extract to its own service
    public File getContractDocument() {
        var document = resourceLoader.getResource("classpath:template_ugovor.doc");
        try {
            return document.getFile();
        } catch (IOException e) {
            log.error("Couldn't load file");
            throw new RentalException(ErrorCode.CANT_CREATE_CONTRACT);
        }
    }

    public Long createContract(Long reservationId) {
        var reservation = reservationService.getReservation(reservationId);

        if (freeReservationConfiguration.isFreeReservationType(reservation.getType())) {
            var contractId = createFreeContract(reservation);
            notificationService.notifyMajor(contractId);
            return contractId;
        }

        var communityHomePlan = reservation.getCommunityHomePlan();
        var numberOfHours = Duration.between(reservation.getDatetimeFrom(), reservation.getDatetimeTo()).toHours();

        var lease = communityHomePlan.getLeaseCostPerHour() * numberOfHours;
        var downPayment = communityHomePlan.getDownPayment();
        var utilities = communityHomePlan.getUtilitiesCostPerHour() * numberOfHours;
        var vat = (lease + utilities + downPayment) * VAT;
        var total = lease + utilities + downPayment + vat;

        var contract = Contract.builder()
                               .reservation(reservation)
                               .lease(lease)
                               .downPayment(downPayment)
                               .utilities(utilities)
                               .total(total)
                               .vat(vat)
                               .status(ContractStatus.CREATED)
                               .build();

        var contractId = contractRepository.save(contract).getId();
        notificationService.notifyMajor(contractId);

        return contractId;
    }

    // TODO: Call this when fetching contracts, and later add scheduled task
    public boolean isContractPaid(Long contractId) {
        var contract = getContract(contractId);

        return switch (contract.getStatus()) {
            case PAYMENT_PENDING -> bankService.isPaid();
            case FINALIZED -> true;
            default -> false;
        };
    }

    public void signContractByMajor(Long contractId) {
        var contract = getContract(contractId);
        // TODO: Add real validation
        //        if (!Objects.equals(contract.getReservation().getCustomer().getId(), customerId)) {
        //            throw new RentalException(ErrorCode.CONTRACT_CUSTOMER_MISMATCH);
        //        }

        contract.setStatus(ContractStatus.MAJOR_SIGNED);
        notificationService.notifyCustomer(contractId, contract.getReservation().getCustomer().getEmail());
    }

    public void signContractByCustomer(Long contractId) {
        var contract = getContract(contractId);
        //        if (!Objects.equals(contract.getReservation().getCustomer().getId(), customerId)) {
        //            throw new RentalException(ErrorCode.CONTRACT_CUSTOMER_MISMATCH);
        //        }

        notificationService.notifyFinancesAndGospodarstvo(contractId);

        contract.setStatus(ContractStatus.PAYMENT_PENDING);
    }

    public void finalizeContract(Long contractId) {
        var contract = getContract(contractId);
        if (contract.getStatus() != ContractStatus.PAYMENT_PENDING) {
            throw new RentalException(ErrorCode.CONTRACT_NOT_PENDING_PAYMENT);
        }

        contract.setStatus(ContractStatus.FINALIZED);
    }

    private Long createFreeContract(Reservation reservation) {
        var contract = Contract.builder()
                               .reservation(reservation)
                               .lease(0.0)
                               .downPayment(0.0)
                               .utilities(0.0)
                               .total(0.0)
                               .vat(0.0)
                               .status(ContractStatus.CREATED)
                               .build();

        return contractRepository.save(contract).getId();
    }

}

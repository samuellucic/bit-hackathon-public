package hr.bithackathon.rental.service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import hr.bithackathon.rental.config.FreeReservationConfiguration;
import hr.bithackathon.rental.domain.Contract;
import hr.bithackathon.rental.domain.ContractStatus;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.ContractRepository;
import hr.bithackathon.rental.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final FreeReservationConfiguration freeReservationConfiguration;
    private final ContractRepository contractRepository;
    private final NotificationService notificationService;
    private final ResourceLoader resourceLoader;
    private final ReservationService reservationService;
    private final AppUserService appUserService;

    static final Double VAT = 0.25;

    public Contract getContract(Long contractId) {
        return contractRepository.findById(contractId).orElseThrow(() -> new RentalException(ErrorCode.CONTRACT_NOT_FOUND));
    }

    public Contract getContractAndCheckUserId(Long contractId, Long userId) {
        var contract = contractRepository.findById(contractId)
                                         .orElseThrow(() -> new RentalException(ErrorCode.CONTRACT_NOT_FOUND));

        if (!contract.getReservation().getCustomer().getId().equals(userId)) {
            throw new RentalException(ErrorCode.UNAUTHORIZED);
        }

        return contract;
    }

    public Contract getContractForCustodian(Long contractId) {
        return contractRepository.findByContractIdAndCustodianIdForCommunityHome(contractId, appUserService.getCurrentAppUser())
                                 .orElseThrow(() -> new RentalException(ErrorCode.CONTRACT_NOT_FOUND));
    }

    public Page<Contract> findAllContractsByCustomerIdAndPage(Long customerId, Pageable page) {
        return contractRepository.findAllByCustomerId(customerId, page);
    }

    public Page<Contract> findAllContractsByCustomerIdAndStatusAndPage(ContractStatus status, Long customerId, Pageable page) {
        return contractRepository.findAllByCustomerIdAndStatus(status, customerId, page);
    }

    public Page<Contract> findAllContractsByPage(Pageable page) {
        return contractRepository.findAll(page);
    }

    public Page<Contract> findAllContractsByStatus(ContractStatus status, Pageable page) {
        return contractRepository.findAllByStatus(status, page);
    }

    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public List<Contract> findAllBetweenStartAndEnd(Long communityHomeId, Instant start, Instant end) {
        return contractRepository.findAllBetweenStartAndEnd(communityHomeId, start, end);
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

    public void createContract(Long reservationId) {
        if (contractRepository.existsContractByReservationId(reservationId)) {
            throw new RentalException(ErrorCode.CONTRACT_ALREADY_EXISTS);
        }

        var reservation = reservationService.getReservation(reservationId);

        if (freeReservationConfiguration.isFreeReservationType(reservation.getType())) {
            var contractId = createFreeContract(reservation);
            notificationService.notifyMajor(contractId);
            return;
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

        var contractId = saveContract(contract).getId();

        notificationService.notifyMajor(contractId);

    }

    public void signContractByMajor(Long contractId) {
        var contract = getContract(contractId);
        if (contract.getStatus() != ContractStatus.CREATED) {
            throw new RentalException(ErrorCode.CONTRACT_ALREADY_SIGNED);
        }

        contract.setStatus(ContractStatus.MAYOR_SIGNED);
        saveContract(contract);

        notificationService.notifyCustomerForContract(contract.getId());
    }

    public void signContractByCustomer(Long contractId) {
        var contract = getContract(contractId);
        var loggedInUserId = SecurityUtils.getCurrentUserDetails().getId();

        if (contract.getReservation().getCustomer().getId() != loggedInUserId) {
            throw new RentalException(ErrorCode.UNAUTHORIZED);
        }

        if (contract.getStatus() != ContractStatus.MAYOR_SIGNED) {
            throw new RentalException(ErrorCode.CONTRACT_NOT_SIGNED_BY_MAYOR);
        }

        contract.setStatus(ContractStatus.PAYMENT_PENDING);
        saveContract(contract);

        notificationService.notifyMinistryAndFinances(contractId);
    }

    public void finalizeContract(Long contractId) {
        var contract = getContract(contractId);

        if (contract.getStatus() == ContractStatus.FINALIZED) {
            throw new RentalException(ErrorCode.CONTRACT_ALREADY_FINALIZED);
        }

        if (contract.getStatus() != ContractStatus.PAYMENT_PENDING) {
            throw new RentalException(ErrorCode.CONTRACT_NOT_SIGNED_BY_CUSTOMER);
        }

        contract.setStatus(ContractStatus.FINALIZED);
        saveContract(contract);
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

        return saveContract(contract).getId();
    }

}

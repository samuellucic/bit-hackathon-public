package hr.bithackathon.rental.service;

import java.util.UUID;

import hr.bithackathon.rental.repository.ContractRepository;
import hr.bithackathon.rental.repository.RecordBookRepository;
import hr.bithackathon.rental.repository.ReservationRepository;
import hr.bithackathon.rental.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${email.enabled}")
    private Boolean enabled;

    private final MailService mailService;
    private final SmsService smsService;
    private final ContractRepository contractRepository;
    private final RecordBookRepository recordBookRepository;
    private final ReservationRepository reservationRepository;

    public void notifyMajor(Long contractId) {
        Util.runAsync(enabled, () -> {
            var contract = contractRepository.findById(contractId).get();
            var communityHomeLocation = contract.getReservation().getCommunityHomePlan().getCommunityHome().getAddress();

            //var customerEmail = contract.getReservation().getCustomer().getEmail();
            var customerEmail = "dotne.dotne@gmail.com";

            mailService.sendSimpleMessage(customerEmail,
                                          "Contract needs to be signed",
                                          "You need to sign a contract for a community home at " + communityHomeLocation + ".");
        });
    }

    public void notifyCustomerReservationApproval(Long reservationId) {
        Util.runAsync(enabled, () -> {
            var reservation = reservationRepository.findById(reservationId).get();
            var text = "Your reservation has been approved for a community home at " + reservation.getCommunityHomePlan().getCommunityHome().getAddress() + ".";

            smsService.sendSms(reservation.getCustomer().getPhone(), text);
        });
    }

    public void notifyCustomerForContract(Long contractId) {
        Util.runAsync(enabled, () -> {
            var contract = contractRepository.findById(contractId).get();
            var communityHomeLocation = contract.getReservation().getCommunityHomePlan().getCommunityHome().getAddress();

            //var customerEmail = contract.getReservation().getCustomer().getEmail();
            var customerEmail = "dotne.dotne@gmail.com";

            mailService.sendSimpleMessage(customerEmail,
                                          "Contract needs to be signed",
                                          "You need to sign a contract for a community home at " + communityHomeLocation + ".");
        });
    }

    public void notifyCustomerForRecord(Long recordBookId) {

        Util.runAsync(enabled, () -> {
            var recordBook = recordBookRepository.findById(recordBookId).get();
            var communityHomeLocation = recordBook.getContract().getReservation().getCommunityHomePlan().getCommunityHome().getAddress();

            //var customerEmail = recordBook.getContract().getReservation().getCustomer().getEmail();
            var customerEmail = "dotne.dotne@gmail.com";

            mailService.sendSimpleMessage(customerEmail,
                                          "Contract needs to be signed",
                                          "Your need to sign a record for a community home at " + communityHomeLocation + ".");
        });
    }

    public void notifyReservationDeclinedEmail(Long reservationId) {
        Util.runAsync(enabled, () -> {
            var reservation = reservationRepository.findById(reservationId).get();
            var communityHomeLocation = reservation.getCommunityHomePlan().getCommunityHome().getAddress();

            //var customerEmail = reservation.getCustomer().getEmail();
            var customerEmail = "dotne.dotne@gmail.com";

            mailService.sendSimpleMessage(customerEmail,
                                          "Reservation declined",
                                          "Your reservation has been declined for a community home at " + communityHomeLocation + ".");
        });
    }

    public void sendUUIDReservationEmail(String email, UUID uuid) {
        Util.runAsync(enabled, () -> {
            var customerEmail = "dotne.dotne@gmail.com";

            mailService.sendSimpleMessage(customerEmail,
                                          "Reservation created",
                                          "Your can find your reservation at localhost:3000/uuid/" + uuid.toString() + ".");
        });
    }

    public void notifyMinistryAndFinances(Long contractId) {
        Util.runAsync(enabled, () -> {
            var contract = contractRepository.findById(contractId).get();
            var communityHomeLocation = contract.getReservation().getCommunityHomePlan().getCommunityHome().getAddress();

            //var customerEmail = contract.getReservation().getCustomer().getEmail();
            var customerEmail = "dotne.dotne@gmail.com";

            mailService.sendSimpleMessage(customerEmail,
                                          "Contract for community home",
                                          "Contract for a community home at " + communityHomeLocation + ".");
        });
    }

}

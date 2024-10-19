package hr.bithackathon.rental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    public void notifyMajor(Long contractId) {
        mockCall();
    }

    public void notifyCustomerForContract(Long contractId, String email) {
        mockCall();
    }
    public void notifyCustomerForRecord(Long recordId, String email) {
        mockCall();
    }

    public void notifyReservationDeclinedEmail(Long ReservationId, String email) {
        mockCall();
    }

    public void notifyMinistry(Long contractId) {
        mockCall();
    }

    public void notifyFinances(Long contractId) {
        mockCall();
    }

    public void sendUUIDReservationEmail(String email, UUID uuid) {
        mockCall();
    }

    public void notifyFinancesAndMinistry(Long contractId) {
        notifyFinances(contractId);
        notifyMinistry(contractId);
    }

    private void mockCall() {
        try (var httpClient = HttpClient.newHttpClient()) {
            var mockRequest = HttpRequest.newBuilder()
                                         .uri(new URI("https://www.example.com"))
                                         .GET()
                                         .build();
            httpClient.send(mockRequest, HttpResponse.BodyHandlers.ofString());
            log.info("Mock call success!");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Mock call failed!");
        }
    }

}

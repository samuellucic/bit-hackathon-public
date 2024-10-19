package hr.bithackathon.rental.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    public void notifyMajor(Long contractId) {
        mockCall();
    }

    public void notifyCustomer(Long contractId, String email) {
        mockCall();
    }

    public void notifyGospodarstvo(Long contractId) {
        mockCall();
    }

    public void notifyFinances(Long contractId) {
        mockCall();
    }

    public void notifyFinancesAndGospodarstvo(Long contractId) {
        notifyFinances(contractId);
        notifyGospodarstvo(contractId);
    }

    private void mockCall() {
        try (var httpClient = HttpClient.newHttpClient()) {
            var mockRequest = HttpRequest.newBuilder()
                                         .uri(new URI("www.example.com"))
                                         .GET()
                                         .build();
            httpClient.send(mockRequest, HttpResponse.BodyHandlers.ofString());
            log.info("Mock call success!");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Mock call failed!");
        }
    }

}

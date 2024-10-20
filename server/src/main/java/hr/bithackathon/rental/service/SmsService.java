package hr.bithackathon.rental.service;

import java.util.List;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${sms.api-key}")
    private String apiKey;

    @Value("${sms.base-url}")
    private String baseUrl;

    @Value("${sms.recipient}")
    private String recipient;

    @Value("${sms.enabled}")
    private Boolean enabled;

    public void sendSms(String phoneNumber, String text) {
        if (!enabled) {
            return;
        }

        var apiClient = ApiClient.forApiKey(ApiKey.from(apiKey)).withBaseUrl(BaseUrl.from(baseUrl)).build();
        var smsApi = new SmsApi(apiClient);

        var smsMessage = new SmsTextualMessage()
            .from("InfoSMS")
            .addDestinationsItem(new SmsDestination().to(recipient))
            .text(text);

        var smsMessageRequest = new SmsAdvancedTextualRequest()
            .messages(List.of(smsMessage));

        try {
            smsApi.sendSmsMessage(smsMessageRequest).execute();
        } catch (ApiException apiException) {
            log.error("Failed to send SMS");
        }
    }

}

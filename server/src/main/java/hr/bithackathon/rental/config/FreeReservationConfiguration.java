package hr.bithackathon.rental.config;

import java.util.LinkedList;
import java.util.List;

import hr.bithackathon.rental.domain.ReservationType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("service.free-reservation")
public class FreeReservationConfiguration {

    private List<ReservationType> freeReservationTypes = new LinkedList<>();

    public boolean isFreeReservationType(ReservationType reservationType) {
        return freeReservationTypes.contains(reservationType);
    }

}

package hr.bithackathon.rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RentalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentalAppApplication.class, args);
    }

}

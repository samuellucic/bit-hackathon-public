package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String OIB;
    private String phone;
    private String city;
    private String address;
    private String postalCode;

    static AppUser dummy() {
        return AppUser.builder()
                .id(0L)
                .firstName("Sam")
                .lastName("Amuel")
                .email("sam@amuel.com")
                .password("ashbdasgeduyqa213.")
                .city("San Francisco")
                .address("123 Main St")
                .postalCode("10430")
                .OIB("97123123")
                .phone("+7(978)123")
                .build();
    }
}

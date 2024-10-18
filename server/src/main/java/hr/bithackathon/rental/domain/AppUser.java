package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    private Authority authority;

    static AppUser dummy() {
        return AppUser.builder()
                .id(0L)
                .firstName("Sam")
                .lastName("Amuel")
                .email("sam@amuel.com")
                .password("admin.")
                .city("San Francisco")
                .address("123 Main St")
                .postalCode("10430")
                .OIB("97123123")
                .phone("+7(978)123")
                .build();
    }
}

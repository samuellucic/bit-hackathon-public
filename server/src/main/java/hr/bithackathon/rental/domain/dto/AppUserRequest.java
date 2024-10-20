package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.AppUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record AppUserRequest(

    @Size(min = 1, max = 255)
    @NotNull
    String firstName,

    @Size(min = 1, max = 255)
    @NotNull
    String lastName,

    @Email
    @Size(min = 1, max = 255)
    String email,

    @Size(min = 8, max = 255)
    String password,

    @Size(min = 11, max = 11)
    @NotNull
    String OIB,

    @Size(min = 1, max = 20)
    String phone,

    @Size(min = 1, max = 255)
    String city,

    @Size(min = 1, max = 255)
    String address,

    @Size(min = 1, max = 255)
    String postalCode
) {

    public static AppUser toAppUser(AppUserRequest dto) {
        return AppUser.builder()
                      .firstName(dto.firstName())
                      .lastName(dto.lastName())
                      .email(dto.email())
                      .password(dto.password())
                      .OIB(dto.OIB())
                      .city(dto.city())
                      .address(dto.address())
                      .postalCode(dto.postalCode())
                      .build();
    }

}

package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.AppUser;

public record AppUserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String OIB,
        String phone,
        String city,
        String address,
        String postalCode) {

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

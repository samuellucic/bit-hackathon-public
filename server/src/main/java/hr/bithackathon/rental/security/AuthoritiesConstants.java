package hr.bithackathon.rental.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthoritiesConstants {

    public static final String ADMIN = "ADMIN";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String CUSTODIAN = "CUSTODIAN";
    public static final String OFFICIAL = "OFFICIAL";
    public static final String MAYOR = "MAYOR";
}

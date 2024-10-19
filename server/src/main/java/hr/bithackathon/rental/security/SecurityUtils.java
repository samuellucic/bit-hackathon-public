package hr.bithackathon.rental.security;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    public static final String AUTHORITIES_KEY = "auth";

    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (
            authentication != null && getAuthorities(authentication).anyMatch(authority -> Arrays.asList(authorities).contains(authority))
        );
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }

    public static AppUserDetails getCurrentUserDetails() {
        return AppUserDetails.fromJwtToken((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public static boolean isUserLoggedOut() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }

    public static boolean isLoggedInCustomer() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("CUSTOMER"));
    }

}

package hr.bithackathon.rental.security;

import static hr.bithackathon.rental.security.SecurityUtils.AUTHORITIES_KEY;
import static hr.bithackathon.rental.security.SecurityUtils.JWT_ALGORITHM;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final JwtEncoder jwtEncoder;

    private final long tokenValidityInMilliseconds = 3_600_000; //1h = 3600s and 3600*1000 = 3600000 milliseconds

    public TokenProvider(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Instant now = Instant.now();
        Instant validity = now.plus(this.tokenValidityInMilliseconds, ChronoUnit.MILLIS);
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuedAt(now)
                                          .expiresAt(validity)
                                          .subject(authentication.getName())
                                          .claim("id", appUserDetails.getId())
                                          .claim(AUTHORITIES_KEY, authorities)
                                          .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

}

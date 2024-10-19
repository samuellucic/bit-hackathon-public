package hr.bithackathon.rental.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class AppUserDetails extends User {

    private final long id;
    private final String email;

    public AppUserDetails(long id, String email, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
    }

    public static AppUserDetails fromJwtToken(Jwt token) {
        GrantedAuthority authority = new SimpleGrantedAuthority((String) token.getClaims().get("auth"));
        return new AppUserDetails(
                (Long) token.getClaims().get("id"),
                (String) token.getClaims().get("sub"),
                (String) token.getClaims().get("sub"),
                "",
                List.of(authority)
        );
    }
}

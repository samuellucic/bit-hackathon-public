package hr.bithackathon.rental.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class AppUserDetails extends User {

    private final long id;
    private final String email;

    public AppUserDetails(long id, String email,  String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
    }
}

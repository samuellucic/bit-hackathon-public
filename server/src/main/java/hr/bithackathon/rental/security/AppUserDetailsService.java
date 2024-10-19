package hr.bithackathon.rental.security;

import java.util.List;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public AppUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findOneWithAuthorityByEmail(email)
                                .map(this::createAppUserDetails)
                                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the database"));
    }

    private AppUserDetails createAppUserDetails(AppUser user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuthority().getAuthority());
        return new AppUserDetails(
            user.getId(),
            user.getEmail(),
            user.getEmail(),
            user.getPassword(),
            List.of(authority));
    }

}

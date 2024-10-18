package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.dto.AppUserRequest;
import hr.bithackathon.rental.domain.dto.LoginRequest;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private PasswordEncoder passwordEncoder;

    public Authentication authenticate(LoginRequest loginRequest) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
        return authenticationManagerBuilder.getObject().authenticate(authentication);
    }

    public Long createUser(AppUserRequest appUserRequest) {
        appUserRepository
                .findOneByEmail(appUserRequest.email())
                .ifPresent(existingUser -> {
                    throw new RentalException(ErrorCode.CONFLICT);
                });

        AppUser appUser = AppUserRequest.toAppUser(appUserRequest);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser).getId();
    }
}

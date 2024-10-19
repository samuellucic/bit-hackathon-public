package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.dto.AppUserRequest;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.AppUserRepository;
import hr.bithackathon.rental.repository.AuthorityRepository;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {

    private AppUserRepository appUserRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    public Long createUser(AppUserRequest appUserRequest) {
        appUserRepository
            .findOneByEmail(appUserRequest.email())
            .ifPresent(existingUser -> {
                throw new RentalException(ErrorCode.CONFLICT);
            });

        AppUser appUser = AppUserRequest.toAppUser(appUserRequest);
        appUser.setAuthority(authorityRepository.findByName(AuthoritiesConstants.CUSTOMER));
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser).getId();
    }

    public AppUser getUserById(Long id) {
        return appUserRepository.findById(id)
                                .orElseThrow(() -> new RentalException(ErrorCode.USER_NOT_FOUND));
    }

    public AppUser registerUser(AppUserRequest appUserRequest) {
        var appUserId = createUser(appUserRequest);
        return getUserById(appUserId);
    }

    public AppUser getCurrentAppUser() {
        return appUserRepository.findById(SecurityUtils.getCurrentUserDetails().getId())
                                .orElseThrow(() -> new RentalException(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}

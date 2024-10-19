package hr.bithackathon.rental.controller;

import java.net.URI;

import hr.bithackathon.rental.domain.dto.AppUserRequest;
import hr.bithackathon.rental.service.AppUserService;
import hr.bithackathon.rental.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<Void> createAppUser(@RequestBody AppUserRequest appUserRequest) {
        Long id = appUserService.createUser(appUserRequest);
        return Util.getCreateResponse(id);
    }

}

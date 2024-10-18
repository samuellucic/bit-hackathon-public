package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.dto.AppUserRequest;
import hr.bithackathon.rental.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<Void> createAppUser(@RequestBody AppUserRequest appUserRequest) {
        Long id = appUserService.createUser(appUserRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}

package hr.bithackathon.rental.common;

import hr.bithackathon.rental.domain.dto.LoginRequest;
import hr.bithackathon.rental.security.JwtToken;
import hr.bithackathon.rental.security.TokenProvider;
import hr.bithackathon.rental.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authService.authenticate(loginRequest);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);
        return ResponseEntity.ok().headers(httpHeaders).body(new JwtToken(jwt));
    }

}

package hr.bithackathon.rental.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Util {
    public static URI getCreateURI(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public static ResponseEntity<Void> getCreateResponse(Long id) {
        return ResponseEntity.created(Util.getCreateURI(id)).build();
    }
}

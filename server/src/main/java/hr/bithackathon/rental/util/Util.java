package hr.bithackathon.rental.util;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public static void runAsync(boolean enabled, Runnable runnable) {
        if (enabled) {
            Thread.startVirtualThread(runnable);
        }
    }

}

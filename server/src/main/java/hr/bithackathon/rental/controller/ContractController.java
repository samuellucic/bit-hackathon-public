package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping("/sign")
    public String createContract(@RequestBody String body) {
        return body;
    }

    @GetMapping("/api/doc")
    public ResponseEntity<InputStreamResource> file() {
        var docFile = contractService.getContractDocument();

        try {
            FileInputStream fis = new FileInputStream(docFile);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template_ugovor.doc");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            InputStreamResource resource = new InputStreamResource(fis);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(docFile.length())
                    .contentType(MediaType.parseMediaType("application/msword"))
                    .body(resource);
        } catch (FileNotFoundException e) {
            throw new RentalException(ErrorCode.CANT_CREATE_CONTRACT);
        }
    }

}

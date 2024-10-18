package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.exception.RentalException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RentalException.class)
    public final ResponseEntity<ExceptionResponse> handleAddToFlowException(RentalException ex) {
        var errorCode = ex.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
                             .body(ExceptionResponse.of(errorCode));
    }

}
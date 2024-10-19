package hr.bithackathon.rental.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RentalException.class)
    public final ResponseEntity<ExceptionResponse> handleAddToFlowException(RentalException ex) {
        var errorCode = ex.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
                             .body(ExceptionResponse.of(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        logger.error(ex.getStackTrace());
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                             .body(ExceptionResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    ResponseStatus findResponseStatus(Exception ex) {
        return null;
    }
}
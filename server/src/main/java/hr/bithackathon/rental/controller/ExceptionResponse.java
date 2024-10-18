package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private int errorCode;
    private String errorMessage;

    public static ExceptionResponse of(ErrorCode errorCode) {
        return new ExceptionResponse(errorCode.getCode(), errorCode.getMessage());
    }

}

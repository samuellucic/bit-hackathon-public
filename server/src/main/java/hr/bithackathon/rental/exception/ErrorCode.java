package hr.bithackathon.rental.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    API_CALL_FAILED(400, 40001, "An API call failed."),

    UNAUTHORIZED(401, 40101, "Unauthorized"),

    INTERNAL_SERVER_ERROR(500, 50001, "Internal server error."),

    SERVICE_UNAVAILABLE(503, 50301, "Service Unavailable.");

    private final int httpStatus;
    private final int code;
    private final String message;

    ErrorCode(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

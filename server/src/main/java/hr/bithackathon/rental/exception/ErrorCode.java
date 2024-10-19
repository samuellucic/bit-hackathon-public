package hr.bithackathon.rental.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    API_CALL_FAILED(400, 40001, "An API call failed."),
    CONTRACT_NOT_FOUND(400, 40002, "Contract not found."),
    CONTRACT_CUSTOMER_MISMATCH(400, 40003, "Contract customer mismatch."),
    RESERVATION_NOT_FOUND(400, 40004, "Reservation not found."),
    CONTRACT_NOT_PENDING_PAYMENT(400, 40005, "Contract is not pending payment."),

    UNAUTHORIZED(401, 40101, "Unauthorized"),

    CONFLICT(409, 40901, "Conflict"),

    INTERNAL_SERVER_ERROR(500, 50001, "Internal server error."),
    CANT_CREATE_CONTRACT(500, 50002, "Couldn't crate contract."),

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

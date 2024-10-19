package hr.bithackathon.rental.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    API_CALL_FAILED(400, 40001, "An API call failed."),
    CONTRACT_NOT_FOUND(400, 40002, "Contract not found."),
    CONTRACT_CUSTOMER_MISMATCH(400, 40003, "Contract customer mismatch."),
    RESERVATION_NOT_FOUND(400, 40004, "Reservation not found."),
    CONTRACT_NOT_PENDING_PAYMENT(400, 40005, "Contract is not pending payment."),
    COMMUNITY_HOME_NOT_FOUND(400, 40006, "Community home not found."),
    CONTRACT_ALREADY_EXISTS(400, 40007, "Contract already exists."),
    CONTRACT_ALREADY_SIGNED(400, 40008, "Contract already signed."),
    CONTRACT_NOT_SIGNED_BY_MAYOR(400, 40009, "Contract not signed by mayor."),
    CONTRACT_NOT_SIGNED_BY_CUSTOMER(400, 40010, "Contract not signed by customer."),
    RECORD_BOOK_NOT_FOUND(400, 40011, "Record Book not found."),
    RESERVATION_HAS_APPROVAL_STATUS(400, 40012, "Reservation has approval status."),
    USER_NOT_FOUND(400, 40013, "User not found."),
    PASSWORD_TOO_WEAK(400, 40014, "Password too weak."),
    RESERVATION_PAST_DATE(400, 40015, "Reservation date is to late."),
    INVALID_DATE_RANGE(400, 40016, "Invalid date range."),
    COMMUNITY_HOME_PLAN_NOT_FOUND(400, 40017, "Community home plan not found."),

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

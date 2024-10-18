package hr.bithackathon.rental.exception;

import lombok.Getter;

@Getter
public class RentalException extends UncheckedBusinessException {

    private final ErrorCode errorCode;

    public RentalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public RentalException(ErrorCode errorCode, String message, Object... messageArgs) {
        super(message);
        this.errorCode = errorCode;
    }

    public RentalException(ErrorCode errorCode, Throwable cause, String message) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public RentalException(ErrorCode errorCode, Throwable cause, String messageFormat, Object... messageArgs) {
        super(format(messageFormat, messageArgs), cause);
        this.errorCode = errorCode;
    }

    //@Nullable
    private static String format(String messageFormat, Object[] messageArgs) {
        return messageFormat != null ? String.format(messageFormat, messageArgs) : null;
    }

}



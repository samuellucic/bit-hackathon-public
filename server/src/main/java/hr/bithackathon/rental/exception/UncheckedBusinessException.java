package hr.bithackathon.rental.exception;

public class UncheckedBusinessException extends RuntimeException {
    public UncheckedBusinessException() {
    }

    public UncheckedBusinessException(String message) {
        super(message);
    }

    public UncheckedBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncheckedBusinessException(Throwable cause) {
        super(cause);
    }

    public UncheckedBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

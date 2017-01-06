package cl.bennu.reports.commons.exception;

public class DriverException extends Exception {

    public DriverException() {
        super();
    }

    public DriverException(String message) {
        super(message);
    }

    public DriverException(Throwable cause) {
        super(cause);
    }

    public DriverException(String message, Throwable cause) {
        super(message, cause);
    }
}

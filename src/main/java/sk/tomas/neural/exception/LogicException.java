package sk.tomas.neural.exception;

public class LogicException extends Exception {

    LogicException(String message) {
        super(message);
    }

    LogicException(Throwable cause) {
        super(cause);
    }
}

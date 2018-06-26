package sk.tomas.neural;

public class FileException extends LogicException {
    public FileException(String message) {
        super(message);
    }

    public FileException(Exception e) {
        super(e);
    }
}

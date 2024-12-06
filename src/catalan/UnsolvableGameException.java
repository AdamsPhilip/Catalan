package catalan;

public class UnsolvableGameException extends Exception {
    public UnsolvableGameException() {}

    public UnsolvableGameException(String msg) {
        super(msg);
    }
}
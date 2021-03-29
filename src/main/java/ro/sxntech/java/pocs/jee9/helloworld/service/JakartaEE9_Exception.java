package ro.sxntech.java.pocs.jee9.helloworld.service;

public class JakartaEE9_Exception extends RuntimeException {

    public JakartaEE9_Exception() {
    }

    public JakartaEE9_Exception(String message) {
        super(message);
    }

    public JakartaEE9_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public JakartaEE9_Exception(Throwable cause) {
        super(cause);
    }
}

package br.com.cefet.activehub.exception;

public class ClienteAlreadyExistsException extends RuntimeException {
    public ClienteAlreadyExistsException(String message) {
        super(message);
    }
}

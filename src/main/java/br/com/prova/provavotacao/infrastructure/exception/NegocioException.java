package br.com.prova.provavotacao.infrastructure.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String exception) {
        super(exception);
    }
}

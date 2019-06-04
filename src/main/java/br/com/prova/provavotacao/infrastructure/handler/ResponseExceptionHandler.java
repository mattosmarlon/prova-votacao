package br.com.prova.provavotacao.infrastructure.handler;

import br.com.prova.provavotacao.domain.dto.exception.ErroExceptionDto;
import br.com.prova.provavotacao.infrastructure.exception.IntegridadeException;
import br.com.prova.provavotacao.infrastructure.exception.NegocioException;
import br.com.prova.provavotacao.infrastructure.exception.ObjetoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ObjetoNotFoundException.class)
    public final ResponseEntity<ErroExceptionDto> handleUserNotFoundException(ObjetoNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(getErrorException(ex, request, HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IntegridadeException.class)
    public final ResponseEntity<ErroExceptionDto> handleUserNotFoundException(IntegridadeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(getErrorException(ex, request, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegocioException.class)
    public final ResponseEntity<ErroExceptionDto> handleUserNotFoundException(NegocioException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(getErrorException(ex, request, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErroExceptionDto> handleUserNotFoundException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(getErrorException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.BAD_REQUEST);
    }

    private ErroExceptionDto getErrorException(Exception exception, WebRequest request, Integer statusCodigo) {
        String mensagem = statusCodigo == 500 ? "Problema inesperado." : exception.getMessage();
        return new ErroExceptionDto(new Date(), mensagem, request.getDescription(false), statusCodigo);
    }
}

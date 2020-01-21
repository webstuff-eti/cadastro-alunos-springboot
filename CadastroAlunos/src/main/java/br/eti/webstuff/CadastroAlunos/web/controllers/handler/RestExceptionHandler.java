package br.eti.webstuff.CadastroAlunos.web.controllers.handler;

import br.eti.webstuff.CadastroAlunos.web.controllers.error.ResourceNotFoundDetails;
import br.eti.webstuff.CadastroAlunos.web.controllers.error.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException){


        ResourceNotFoundDetails rfnDetails =  ResourceNotFoundDetails.Builder
                                                                     .newBuilder()
                                                                     .timestamp(new Date().getTime() )
                                                                     .status( HttpStatus.NOT_FOUND.value() )
                                                                     .title( "Recurso não encontrado" )
                                                                     .detail(  rfnException.getMessage())
                                                                     .developerMessage( rfnException.getClass().getName() )
                                                                     .build();

        return new ResponseEntity<>( rfnDetails, HttpStatus.ACCEPTED.NOT_FOUND );
    }

}

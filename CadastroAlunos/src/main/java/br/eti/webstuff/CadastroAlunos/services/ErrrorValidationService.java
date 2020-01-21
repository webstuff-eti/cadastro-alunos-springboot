package br.eti.webstuff.CadastroAlunos.services;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface ErrrorValidationService {

    ResponseEntity<Map<String, String>> validateInputData(BindingResult result);
}

package br.eti.webstuff.CadastroAlunos.services;

import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

public interface ErrrorValidationService {

    Optional<Map<String, String>> validateInputData(BindingResult result);
}

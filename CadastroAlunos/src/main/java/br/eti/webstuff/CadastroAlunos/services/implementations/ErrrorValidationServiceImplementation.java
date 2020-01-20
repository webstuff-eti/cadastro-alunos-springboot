package br.eti.webstuff.CadastroAlunos.services.implementations;

import br.eti.webstuff.CadastroAlunos.services.ErrrorValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ErrrorValidationServiceImplementation implements ErrrorValidationService {

    @Override
    public Optional<Map<String, String>> validateInputData(BindingResult result) {
        if (result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors())
                errorMap.put(error.getField(), error.getDefaultMessage());
            return Optional.of(errorMap);
        }
        return Optional.empty();
    }
}

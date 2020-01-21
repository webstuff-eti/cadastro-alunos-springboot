package br.eti.webstuff.CadastroAlunos.services.implementations;

import br.eti.webstuff.CadastroAlunos.services.ErrrorValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ErrrorValidationServiceImplementation implements ErrrorValidationService {

    public ResponseEntity<Map<String, String>> validateInputData(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        if (result.hasErrors()){
            for (FieldError error : result.getFieldErrors())
                errorMap.put(error.getField(), error.getDefaultMessage());
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.OK);
    }

}

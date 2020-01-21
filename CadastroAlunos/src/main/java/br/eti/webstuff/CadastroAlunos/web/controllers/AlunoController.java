package br.eti.webstuff.CadastroAlunos.web.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.eti.webstuff.CadastroAlunos.web.dto.request.AlunoRequestDto;
import org.springframework.web.bind.annotation.RequestParam;

//import java.security.NoSuchAlgorithmException;

public interface AlunoController {
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AlunoRequestDto alunoRequestDto, BindingResult result);
    public ResponseEntity<?> buscarTodosAlunos();
    public ResponseEntity<?> buscarTodosAlunosPorPaginacao(@RequestParam(value = "pag", defaultValue = "0") int pagina, @RequestParam(value = "size", defaultValue = "1") int tamanho);;
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id);
    public ResponseEntity<?> buscarPorEmail(@PathVariable("email") String email);
    public ResponseEntity<?> buscarPorCpf(@PathVariable("cpf") String documento);
    public ResponseEntity<?> atualizar( @RequestBody @Valid AlunoRequestDto alunoRequestDto, BindingResult result);
    public ResponseEntity<?> removerPorId(@PathVariable("id") Long id);
    public ResponseEntity<?> removerPorEmail(@PathVariable("email") String email);
    public ResponseEntity<?> removerPorCpf(@PathVariable("cpf") String cpf);
}

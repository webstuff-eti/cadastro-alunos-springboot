package br.eti.webstuff.CadastroAlunos.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.eti.webstuff.CadastroAlunos.web.dto.response.AlunoResponseDto;
import br.eti.webstuff.CadastroAlunos.web.dto.request.AlunoRequestDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface AlunoController {
    public ResponseEntity<?> cadastrar(@Valid @RequestBody AlunoRequestDto alunoRequestDto, BindingResult result);
    public ResponseEntity<?>buscarTodosAlunos();
    public ResponseEntity<?> buscarTodosAlunosPorPaginacao(@RequestParam(value = "pag", defaultValue = "0") int pagina, @RequestParam(value = "size", defaultValue = "1") int tamanho);;
    public ResponseEntity<AlunoResponseDto> buscarPorId(@PathVariable("id") Long id);
    public ResponseEntity<AlunoResponseDto> buscarPorEmail(@PathVariable("email") String email);
    public ResponseEntity buscarPorCpf(@PathVariable("cpf") String documento);
    public ResponseEntity<?> atualizar( @Valid @RequestBody AlunoRequestDto alunoRequestDto, BindingResult result);
    public ResponseEntity<AlunoResponseDto> removerPorId(@PathVariable("id") Long id);
    public ResponseEntity<AlunoResponseDto> removerPorEmail(@PathVariable("email") String email);
    public ResponseEntity<AlunoResponseDto> removerPorCpf(@PathVariable("cpf") String cpf);
}

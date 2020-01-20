package br.eti.webstuff.CadastroAlunos.web.controllers.implementations;


import br.eti.webstuff.CadastroAlunos.entities.Aluno;
import br.eti.webstuff.CadastroAlunos.services.AlunoService;
import br.eti.webstuff.CadastroAlunos.services.ErrrorValidationService;
import br.eti.webstuff.CadastroAlunos.web.controllers.AlunoController;
import br.eti.webstuff.CadastroAlunos.web.controllers.converters.ConverteAluno;
import br.eti.webstuff.CadastroAlunos.web.controllers.error.ResourceNotFoundException;
import br.eti.webstuff.CadastroAlunos.web.dto.request.AlunoRequestDto;
import br.eti.webstuff.CadastroAlunos.web.dto.response.AlunoResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/aluno")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlunoControllerImplementation implements AlunoController {

    private static final Logger log = LoggerFactory.getLogger( AlunoController.class );

    private final AlunoService alunoService;
    private final ErrrorValidationService errrorValidationService;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;


    /**
     * Cadastra um novo aluno .
     *
     * @param alunoRequestDto
     * @return ResponseEntity<Response < AlunoResponseDto>>
     */
    @ApiOperation(value = "Cadastra um novo aluno")
    @PostMapping
    @Override
    public ResponseEntity<?> cadastrar(@Valid AlunoRequestDto alunoRequestDto, BindingResult result) {

        Optional<Map<String, String>> errorMap = errrorValidationService.validateInputData(result);
        if (errorMap.isPresent()) return new ResponseEntity<>(errorMap.get(), HttpStatus.BAD_REQUEST);

        log.info( "Cadastra uma pessoa: {}", alunoRequestDto.toString() );
        ConverteAluno converte = new ConverteAluno();
        Aluno al = new Aluno();
        Optional<Aluno> alunoBuscado = alunoService.buscarAlunoPorCpf( alunoRequestDto.getCpf() );
        if(!alunoBuscado.isPresent()){
            al = converte.converteAlunoRequestDtoParaAluno( alunoRequestDto );
            log.info("Metodo cadastrar - cadastrar aluno: sucesso!");
            Aluno alunoCadastrado = this.alunoService.cadastrarAluno( al );
            return new ResponseEntity<AlunoResponseDto>(converte.converteAlunoParaAlunoResponseDto( alunoCadastrado ), HttpStatus.CREATED);
        }else {
            log.info( "Metodo cadastrar - cadastrar aluno: Já existe!" );
            return new ResponseEntity<AlunoResponseDto>( converte.converteAlunoParaAlunoResponseDto( al ), HttpStatus.NO_CONTENT );
        }
    }

    @GetMapping(value = "/all")
    @Override
    public ResponseEntity<?> buscarTodosAlunos() {
        log.info( "Lista todos os alunos: {}" );
        ConverteAluno converte = new ConverteAluno();
        List<Aluno> alunos = this.alunoService.buscarTodosAluno();
        if (!alunos.isEmpty()) {
            log.info( "Metodo buscarTodosAlunos - Busca todos os alunos: sucesso!" );
            return new ResponseEntity<>( alunos, HttpStatus.OK );
        } else {
            log.info( "Metodo buscarTodosAlunos - Busca todos os alunos: Não existe!" );
            return new ResponseEntity<>( alunos, HttpStatus.NO_CONTENT );
        }
    }

    @GetMapping(value = "/page/all")
    @Override
    public ResponseEntity<?> buscarTodosAlunosPorPaginacao(@RequestParam(value = "pagina", defaultValue = "0") int numPage,
                                                           @RequestParam(value = "tamanho", defaultValue = "1") int sizePage) {
        Pageable pagina = (Pageable) PageRequest.of(numPage, sizePage);
        return new ResponseEntity<>( this.alunoService.buscarTodosPorPaginacao( (Pageable) pagina ), HttpStatus.OK );
    }

    @GetMapping(value = "/busca/id/{id}")
    @Override
    public ResponseEntity<AlunoResponseDto> buscarPorId(@PathVariable("id") Long id) {
        log.info("Busca aluno pelo id: {}", id);
        ConverteAluno converte = new ConverteAluno();
        AlunoResponseDto  alunoResponseDto = new AlunoResponseDto();
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorId( id );
        if (alunoValido.isPresent()) {
            log.info("Metodo buscarPorId - Busca aluno: sucesso!");
            alunoResponseDto = converte.converteAlunoParaAlunoResponseDto( alunoValido.get() );
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.OK);
        } else {
            log.info("Metodo buscarPorId - Busca aluno: Não existe!");
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/busca/email/{email}")
    @Override
    public ResponseEntity<AlunoResponseDto> buscarPorEmail(@PathVariable("email") String email) {
        log.info("Busca aluno pelo email: {}", email);
        ConverteAluno converte = new ConverteAluno();
        AlunoResponseDto  alunoResponseDto = new AlunoResponseDto();
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorEmail( email );
        if (alunoValido.isPresent()) {
            log.info("Metodo buscarPorEmail - Busca aluno: sucesso!");
            alunoResponseDto = converte.converteAlunoParaAlunoResponseDto( alunoValido.get() );
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.OK);
        } else {
            log.info("Metodo buscarPorEmail - Busca aluno: Não existe!");
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/busca/cpf/{documento}")
    @Override
    public ResponseEntity<AlunoResponseDto> buscarPorCpf(@PathVariable("documento") String documento) {
        log.info("Busca aluno pelo cpf: {}", documento);
        ConverteAluno converte = new ConverteAluno();
        AlunoResponseDto  alunoResponseDto = new AlunoResponseDto();
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorCpf(documento );
        if (alunoValido.isPresent()) {
            log.info("Metodo buscarPorCpf - Busca aluno: sucesso!");
            alunoResponseDto = converte.converteAlunoParaAlunoResponseDto( alunoValido.get() );
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.OK);
        } else {
            log.info("Metodo buscarPorCpf - Busca aluno: Não existe!");
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = "/atualiza")
    @Override
    public ResponseEntity<?> atualizar(@Valid AlunoRequestDto alunoRequestDto, BindingResult result)  {
        Optional<Map<String, String>> errorMap = errrorValidationService.validateInputData(result);
        if (errorMap.isPresent()) return new ResponseEntity<>(errorMap.get(), HttpStatus.BAD_REQUEST);
        log.info("Atualizando aluno: {}", alunoRequestDto.toString());
        ConverteAluno converte = new ConverteAluno();
        Aluno alunoAtualiza = new Aluno();
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorCpf(alunoRequestDto.getCpf() );
        if(alunoValido.isPresent()) {
            alunoAtualiza = converte.converteAlunoRequestDtoParaAluno( alunoRequestDto );
            alunoAtualiza.setId( alunoValido.get().getId() );
            alunoAtualiza.setDataCriacao( alunoValido.get().getDataCriacao() );
        }
        alunoAtualiza =  this.alunoService.atualizarAluno(alunoAtualiza);
        AlunoResponseDto  alunoResponseDto = converte.converteAlunoParaAlunoResponseDto( alunoAtualiza );
        if (alunoAtualiza != null) {
            log.info("Metodo atualizar - Atualiza aluno: sucesso!");
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.CREATED);
        } else {
            log.info("Metodo atualizar - Atualiza aluno: Não existe!");
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Remove um aluno por ID.
     *
     * @param id
     * @return ResponseEntity<?>
     */
    @DeleteMapping(value = "/id/{id}")
    @Override
    public ResponseEntity<?> removerPorId(@PathVariable("id") Long id) {
        log.info("Removendo aluno pelo id: {}", id);
        verificaSeAlunoExistePorId( id );
        this.alunoService.removeAlunoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove um aluno por Email.
     *
     * @param email
     * @return ResponseEntity<?>
     */
    @DeleteMapping(value = "/email/{email}")
    @Override
    public ResponseEntity<?> removerPorEmail(@PathVariable("email") String email) {
        log.info("Removendo aluno pelo email {}", email);
        verificasEAlunoExistePorEmail( email );
        this.alunoService.removeAlunoPorEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove um aluno por CPF.
     *
     * @param cpf
     * @return ResponseEntity<Response<?>>
     */
    @DeleteMapping(value = "/cpf/{cpf}")
    @Override
    public ResponseEntity<?> removerPorCpf(@PathVariable("cpf") String cpf) {
        log.info("Remove aluno pelo cpf {}", cpf);
        verificaSeAlunoExistePorCpf( cpf );
        this.alunoService.removeAlunoPorCpf(cpf);
        return new ResponseEntity<>(HttpStatus.OK);
    }





    

     private void verificaSeAlunoExistePorId(Long id){
         Optional<Aluno> aluno = this.alunoService.buscarAlunoPorId(id);
         if(!aluno.isPresent()){
             log.info("Aluno Não existe!");
             throw new ResourceNotFoundException( "Aluno não encontrado para o id: " + id );
         }
     }


    private void verificaSeAlunoExistePorCpf(String cpf){
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorCpf(cpf);
        if(!aluno.isPresent()){
            log.info("Aluno Não existe!");
            throw new ResourceNotFoundException( "Aluno não encontrado para o cpf: " + cpf );
        }
    }

    private void verificasEAlunoExistePorEmail(String email){
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorCpf( email );
        if(!aluno.isPresent()){
            log.info("Aluno Não existe!");
            throw new ResourceNotFoundException( "Aluno não encontrado para o email: " + email );
        }
    }



}

package br.eti.webstuff.CadastroAlunos.web.controllers.implementations;


import br.eti.webstuff.CadastroAlunos.entities.Aluno;
import br.eti.webstuff.CadastroAlunos.services.AlunoService;
import br.eti.webstuff.CadastroAlunos.services.ErrrorValidationService;
import br.eti.webstuff.CadastroAlunos.web.controllers.AlunoController;
import br.eti.webstuff.CadastroAlunos.web.controllers.converters.ConverteAluno;
import br.eti.webstuff.CadastroAlunos.web.controllers.error.ResourceNotFoundException;
import br.eti.webstuff.CadastroAlunos.web.controllers.handler.RestExceptionHandler;
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
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlunoControllerImplementation implements AlunoController {

    private static final Logger log = LoggerFactory.getLogger( AlunoController.class );

    private final AlunoService alunoService;
    private final ErrrorValidationService errrorValidationService;


    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    @Value("${log.cadastrar.aluno}")
    private String logCadastro;

    @Value("${log.buscar.todos.aluno}")
    private String logBuscaTodosAlunos;

    @Value("${log.buscar.todos.aluno.pagina}")
    private String logBuscaTodosAlunosPagina;

    @Value("${log.buscar.aluno.id}")
    private String logBuscaAlunoId;

    @Value("${log.buscar.aluno.email")
    private String logBuscaAlunoEmail;

    @Value("${log.buscar.aluno.cpf ")
    private String logBuscaAlunoCpf;

    @Value("${log.atualizar.aluno")
    private String logAtualizarAluno;

    @Value("${log.deletar.aluno.id")
    private String logDeletarAlunoId;

    @Value("${log.deletar.aluno.email ")
    private String logDeletarAlunoEmail;

    @Value("${log.deletar.aluno.cpf")
    private String logDeletarAlunoCpf;

    @Value("${log.verifica.aluno.id ")
    private String logVerificaAlunoId;

    @Value("${log.verifica.aluno.cpf ")
    private String logVerificaAlunoCpf;

    @Value("${log.verifica.aluno.email ")
    private String logVerificaAlunoEmail;

    @Value("${log.verifica.aluno.atulizado")
    private String logVerificaAtualizado;

    @Value("${log.verifica.aluno.cadastro")
    private String logVerificaCadastro;

    @Value("${log.nao.existe.aluno")
    private String logAlunoNaoExiste;

    @Value("${log.existe.aluno")
    private String logAlunoExiste;

    @Value("${log.aluno.exception.id")
    private String logAlunoIdException;

    @Value("${log.aluno.exception.email")
    private String logAlunoEmailException;

    @Value("${log.aluno.exception.cpf")
    private String logAlunoCpfException;

    @Value("${log.aluno.exception.atualizado")
    private String logAlunoAtualizadoException;

    @Value("${log.aluno.exception.cadastro")
    private String logAlunoCadastroException;


    /**
     * Cadastra um novo aluno .
     *
     * @param alunoRequestDto
     * @return ResponseEntity<?>
     */
    @ApiOperation(value = "Cadastra um novo aluno")
    @PostMapping
    @Override
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AlunoRequestDto alunoRequestDto, BindingResult result) {
        log.info( logCadastro );
        if(!result.getAllErrors().isEmpty()){
            return errrorValidationService.validateInputData( result );
        }
        verificaAlunoParaCadastro( alunoRequestDto.getCpf() );
        ConverteAluno converte = new ConverteAluno();
        Aluno al = converte.converteAlunoRequestDtoParaAluno( alunoRequestDto );
        Aluno alunoCadastrado = this.alunoService.cadastrarAluno( al );
        return new ResponseEntity<AlunoResponseDto>(converte.converteAlunoParaAlunoResponseDto( alunoCadastrado ), HttpStatus.CREATED);
    }

    /**
     * Busca todos alunos cadastrados.
     * @return ResponseEntity<List<Aluno> alunos>
     */
    @ApiOperation(value = "Busca todos alunos")
    @GetMapping(value = "/all")
    @Override
    public ResponseEntity<?> buscarTodosAlunos() {
        log.info( logBuscaTodosAlunos );
        ConverteAluno converte = new ConverteAluno();
        List<Aluno> alunos = this.alunoService.buscarTodosAluno();
        if (!alunos.isEmpty()) {
            return new ResponseEntity<>( alunos, HttpStatus.OK );
        } else {
            log.info( logAlunoNaoExiste );
            return new ResponseEntity<>( alunos, HttpStatus.NO_CONTENT );
        }
    }

    /**
     * Busca todos alunos por  página.
     * @param numPage
     * @param sizePage
     * @return ResponseEntity<Pageable>
     */
    @ApiOperation(value = "Busca todos alunos por página")
    @GetMapping(value = "/page/all")
    @Override
    public ResponseEntity<?> buscarTodosAlunosPorPaginacao(@RequestParam(value = "numPage", defaultValue = "0") int numPage,
                                                           @RequestParam(value = "sizePage", defaultValue = "1") int sizePage) {
        log.info( logBuscaTodosAlunosPagina );
        Pageable pagina = (Pageable) PageRequest.of(numPage, sizePage);
        return new ResponseEntity<>( this.alunoService.buscarTodosPorPaginacao( (Pageable) pagina ), HttpStatus.OK );
    }

    /**
     * Busca alunos por id.
     * @param id
     * @return ResponseEntity<AlunoResponseDto>
     */
    @ApiOperation(value = "Busca aluno por id")
    @GetMapping(value = "/busca/id/{id}")
    @Override
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        log.info( logBuscaAlunoId );
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorId( id );
        if (!alunoValido.isPresent()) {
            log.info( logAlunoNaoExiste );
            return new RestExceptionHandler().handleResourceNotFoundException( new ResourceNotFoundException( logAlunoIdException + id ) );
        }
        AlunoResponseDto  alunoResponseDto = new ConverteAluno().converteAlunoParaAlunoResponseDto( alunoValido.get() );
        return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.OK);
    }

    /**
     * Busca alunos por cpf.
     * @param email
     * @return ResponseEntity<AlunoResponseDto>
     */
    @ApiOperation(value = "Busca alunos por email")
    @GetMapping(value = "/busca/email/{email}")
    @Override
    public ResponseEntity<?> buscarPorEmail(@PathVariable("email") String email) {
        log.info( logBuscaAlunoEmail );
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorEmail( email );
        if (!alunoValido.isPresent()) {
            log.info( logAlunoNaoExiste );
            return new RestExceptionHandler().handleResourceNotFoundException( new ResourceNotFoundException( logAlunoEmailException + email ) );
        }
        AlunoResponseDto  alunoResponseDto = new ConverteAluno().converteAlunoParaAlunoResponseDto( alunoValido.get() );
        return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.OK);
    }

    /**
     * Busca alunos por email.
     * @param documento
     * @return ResponseEntity<AlunoResponseDto>
     */
    @ApiOperation(value = "Busca alunos por cpf")
    @GetMapping(value = "/busca/cpf/{documento}")
    @Override
    public ResponseEntity<?> buscarPorCpf(@PathVariable("documento") String documento) {
        log.info( logBuscaAlunoCpf );
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorCpf(documento );
        if (!alunoValido.isPresent()) {
            log.info( logBuscaAlunoCpf );
            return new RestExceptionHandler().handleResourceNotFoundException( new ResourceNotFoundException( logAlunoCpfException + documento) );
        }
        AlunoResponseDto  alunoResponseDto =  new ConverteAluno().converteAlunoParaAlunoResponseDto( alunoValido.get() );
        return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.OK);
    }

    /**
     * Altera um aluno cadastrado.
     * @param alunoRequestDto
     * @return ResponseEntity<AlunoResponseDto>
     */
    @ApiOperation(value = "Atualiza aluno")
    @PutMapping(value = "/atualiza")
    @Override
    public ResponseEntity<?> atualizar(@RequestBody @Valid AlunoRequestDto alunoRequestDto, BindingResult result)  {
        log.info( logAtualizarAluno );
        if(!result.getAllErrors().isEmpty()){
            return errrorValidationService.validateInputData( result );
        }
        Aluno alunoAtualiza = new Aluno();

        Optional<Aluno> alunoValido = verificaAlunoParaAtualizao( alunoRequestDto.getCpf() );

        alunoAtualiza = new ConverteAluno().converteAlunoRequestDtoParaAluno( alunoRequestDto );
        alunoAtualiza.setId( alunoValido.get().getId() );
        alunoAtualiza.setDataCriacao( alunoValido.get().getDataCriacao() );

        alunoAtualiza =  this.alunoService.atualizarAluno(alunoAtualiza);

        AlunoResponseDto  alunoResponseDto = new ConverteAluno().converteAlunoParaAlunoResponseDto( alunoAtualiza );
        if (alunoAtualiza == null) {
            log.info( logAlunoNaoExiste );
            return new RestExceptionHandler().handleResourceNotFoundException( new ResourceNotFoundException( logAlunoAtualizadoException ) );
        }
        return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.CREATED);
    }

    /**
     * Remove um aluno por ID.
     * @param id
     * @return void
     */
    @ApiOperation(value = "Remove aluno por id")
    @DeleteMapping(value = "/id/{id}")
    @Override
    public ResponseEntity<?> removerPorId(@PathVariable("id") Long id) {
        log.info( logDeletarAlunoId );
        verificaSeAlunoExistePorId( id );
        this.alunoService.removeAlunoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove um aluno por EMAIL.
     * @param email
     * @return void
     */
    @ApiOperation(value = "Remove aluno por email")
    @DeleteMapping(value = "/email/{email}")
    @Override
    public ResponseEntity<?> removerPorEmail(@PathVariable("email") String email) {
        log.info( logDeletarAlunoEmail );
        verificaSeAlunoExistePorEmail( email.trim() );
        this.alunoService.removeAlunoPorEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove um aluno por CPD.
     * @param cpf
     * @return void
     */
    @ApiOperation(value = "Remove aluno por cpf")
    @DeleteMapping(value = "/cpf/{cpf}")
    @Override
    public ResponseEntity<?> removerPorCpf(@PathVariable("cpf") String cpf) {
        log.info( logDeletarAlunoCpf );
        verificaSeAlunoExistePorCpf( cpf.trim() );
        this.alunoService.removeAlunoPorCpf(cpf);
        return new ResponseEntity<>(HttpStatus.OK);
    }

     private void verificaSeAlunoExistePorId(Long id){
        log.info( logVerificaAlunoId );
         Optional<Aluno> aluno = this.alunoService.buscarAlunoPorId(id);
         if(!aluno.isPresent()){
             log.info( logAlunoNaoExiste );
             throw new ResourceNotFoundException( logAlunoIdException + id );
         }
     }

    private void verificaSeAlunoExistePorCpf(String cpf){
        log.info( logVerificaAlunoCpf );
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorCpf(cpf);
        if(!aluno.isPresent()){
            log.info( logAlunoNaoExiste );
            throw new ResourceNotFoundException( logAlunoCpfException + cpf );
        }
    }

    private Optional<Aluno> verificaAlunoParaAtualizao(String cpf){
        log.info( logVerificaAtualizado );
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorCpf(cpf);
        if(!aluno.isPresent()){
            log.info( logAlunoNaoExiste );
            throw new ResourceNotFoundException( logAlunoCpfException + cpf );
        }
        return aluno;
    }

    private void verificaSeAlunoExistePorEmail(String email){
        log.info( logVerificaAlunoEmail );
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorEmail( email );
        if(!aluno.isPresent()){
            log.info( logAlunoNaoExiste );
            throw new ResourceNotFoundException( logAlunoEmailException + email );
        }
    }

    private void verificaAlunoParaCadastro(String cpf){
        log.info( logVerificaCadastro );
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorCpf(cpf);
        if(aluno.isPresent()){
            log.info( logAlunoExiste );
            throw new ResourceNotFoundException( logAlunoCpfException + cpf );
        }
    }



}

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
import org.springframework.transaction.annotation.Transactional;
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
     * @return ResponseEntity<?>
     */
    @ApiOperation(value = "Cadastra um novo aluno")
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity<?> cadastrar(@Valid AlunoRequestDto alunoRequestDto, BindingResult result) {
        Optional<Map<String, String>> errorMap = errrorValidationService.validateInputData(result);
        if (errorMap.isPresent()) return new ResponseEntity<>(errorMap.get(), HttpStatus.BAD_REQUEST);
        verificaAlunoParaCadastro( alunoRequestDto.getCpf() );
        ConverteAluno converte = new ConverteAluno();
        Aluno al = converte.converteAlunoRequestDtoParaAluno( alunoRequestDto );
        Aluno alunoCadastrado = this.alunoService.cadastrarAluno( al );
        log.info("Metodo cadastrar - cadastrar aluno: sucesso!");
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
        log.info("Busca aluno pelo id: {}", id);
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorId( id );
        if (!alunoValido.isPresent()) {
            log.info("Metodo buscarPorId - Busca aluno: Não existe!");
            return new RestExceptionHandler().handleResourceNotFoundException( new ResourceNotFoundException( "Aluno não encontrado para o id: " + id ) );
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
        log.info("Busca aluno pelo email: {}", email);
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorEmail( email );
        if (!alunoValido.isPresent()) {
            log.info("Metodo buscarPorEmail - Busca aluno: Não existe!");
            return new ResponseEntity<AlunoResponseDto>( HttpStatus.NOT_FOUND);
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
        log.info("Busca aluno pelo cpf: {}", documento);
        Optional<Aluno> alunoValido = this.alunoService.buscarAlunoPorCpf(documento );
        if (!alunoValido.isPresent()) {
            log.info( "Metodo buscarPorEmail - Busca aluno: Não existe!" );
            return new ResponseEntity<AlunoResponseDto>( HttpStatus.NOT_FOUND );
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity<?> atualizar(@Valid AlunoRequestDto alunoRequestDto, BindingResult result)  {
        log.info("Atualizando aluno: {}", alunoRequestDto.toString());
        Optional<Map<String, String>> errorMap = errrorValidationService.validateInputData(result);
        if (errorMap.isPresent()) return new ResponseEntity<>(errorMap.get(), HttpStatus.BAD_REQUEST);

        Aluno alunoAtualiza = new Aluno();

        Optional<Aluno> alunoValido = verificaAlunoParaAtualizao( alunoRequestDto.getCpf() );

        alunoAtualiza = new ConverteAluno().converteAlunoRequestDtoParaAluno( alunoRequestDto );
        alunoAtualiza.setId( alunoValido.get().getId() );
        alunoAtualiza.setDataCriacao( alunoValido.get().getDataCriacao() );

        alunoAtualiza =  this.alunoService.atualizarAluno(alunoAtualiza);

        AlunoResponseDto  alunoResponseDto = new ConverteAluno().converteAlunoParaAlunoResponseDto( alunoAtualiza );

        if (alunoAtualiza == null) {
            log.info("Metodo atualizar - Atualiza aluno: Não existe!");
            return new ResponseEntity<AlunoResponseDto>(alunoResponseDto, HttpStatus.NO_CONTENT);
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity<?> removerPorId(@PathVariable("id") Long id) {
        log.info("Removendo aluno pelo id: {}", id);
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity<?> removerPorEmail(@PathVariable("email") String email) {
        log.info("Removendo aluno pelo email {}", email);
        verificasEAlunoExistePorEmail( email );
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
    @Transactional(rollbackFor = Exception.class)
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

    private Optional<Aluno> verificaAlunoParaAtualizao(String cpf){
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorCpf(cpf);
        if(!aluno.isPresent()){
            log.info("Aluno Não existe!");
            throw new ResourceNotFoundException( "Aluno não encontrado para o cpf: " + cpf );
        }
        return aluno;
    }

    private void verificasEAlunoExistePorEmail(String email){
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorEmail( email );
        if(!aluno.isPresent()){
            log.info("Aluno Não existe!");
            throw new ResourceNotFoundException( "Aluno não encontrado para o email: " + email );
        }
    }

    private void verificaAlunoParaCadastro(String cpf){
        Optional<Aluno> aluno = this.alunoService.buscarAlunoPorCpf(cpf);
        if(aluno.isPresent()){
            log.info("Aluno já existe!");
            throw new ResourceNotFoundException( "Aluno encontrado para o : " + cpf );
        }
    }



}

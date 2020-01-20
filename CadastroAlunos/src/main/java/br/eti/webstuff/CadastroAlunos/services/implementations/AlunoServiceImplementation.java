package br.eti.webstuff.CadastroAlunos.services.implementations;

import br.eti.webstuff.CadastroAlunos.entities.Aluno;
import br.eti.webstuff.CadastroAlunos.repositories.AlunoRepository;
import br.eti.webstuff.CadastroAlunos.services.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AlunoServiceImplementation implements AlunoService {

    private final AlunoRepository alunoRepository;

    @Override
    public Aluno cadastrarAluno(Aluno aluno) {
        log.info("Persistindo novo aluno: {}", aluno);
        return this.alunoRepository.save(aluno);
    }

    @Override
    public Aluno atualizarAluno(Aluno aluno) {
        log.info("Atualizando aluno: {}", aluno);
        aluno.setDataAtualizacao( new Date() );
        return this.alunoRepository.save(aluno);
    }

    @Override
    public Optional<Aluno> buscarAlunoPorId(Long id) {
        log.info("Buscando um ALUNO por ID {}", id);
        return this.alunoRepository.findById(id);
    }

    @Override
    public Optional<Aluno> buscarAlunoPorEmail(String email) {
        log.info("Buscando um ALUNO para o EMAIL {}", email);
        return Optional.ofNullable(this.alunoRepository.findByEmail(email));
    }

    @Override
    public  Optional<Aluno> buscarAlunoPorCpf(String cpf) {
        log.info("Buscando um ALUNO para o CPF {}", cpf);
        return Optional.ofNullable(alunoRepository.findByCpf(cpf));
    }

    @Override
    public List<Aluno> buscarTodosAluno() {
        log.info("Buscados todos os alunos");
        return (List<Aluno>)this.alunoRepository.findAll();
    }

    @Override
    public Page<Aluno> buscarTodosPorPaginacao(Pageable pageable) {
        log.info("Buscados todos os alunos por paginação");
        return (Page<Aluno>) this.alunoRepository.findAll(pageable);
    }

    @Override
    public void removeAlunoPorId(Long id) {
        log.info("Excluindo um aluno pelo ID {}", id);
        this.alunoRepository.deleteById(id);
    }

    @Override
    public void removeAlunoPorCpf(String cpf) {
        log.info("Excluindo um aluno pelo CPF {}", cpf);
        Aluno aluno = this.alunoRepository.findByCpf(cpf);
        this.alunoRepository.deleteById(aluno.getId());
    }

    @Override
    public void removeAlunoPorEmail(String email) {
        log.info("Excluindo um aluno pelo Email {}", email);
        Aluno aluno = this.alunoRepository.findByEmail(email);
        this.alunoRepository.deleteById(aluno.getId());
    }
}

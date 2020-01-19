package br.eti.webstuff.CadastroAlunos.services;

import br.eti.webstuff.CadastroAlunos.entities.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AlunoService {
    Aluno cadastrarAluno(Aluno aluno);
    Aluno atualizarAluno(Aluno aluno);
    Optional<Aluno> buscarAlunoPorId(Long id);
    Optional<Aluno> buscarAlunoPorEmail(String email);
    Optional<Aluno> buscarAlunoPorCpf(String cpf);
    List<Aluno> buscarTodosAluno();
    Page<Aluno> buscarTodosPorPaginacao(Pageable pageable);
    void removeAlunoPorId(Long id);
    void removeAlunoPorCpf(String cpf);
    void removeAlunoPorEmail(String email);
}

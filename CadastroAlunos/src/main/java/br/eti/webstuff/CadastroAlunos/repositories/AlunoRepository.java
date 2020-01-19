package br.eti.webstuff.CadastroAlunos.repositories;

import br.eti.webstuff.CadastroAlunos.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Transactional(readOnly = true)
    Aluno findByCpf(String cpf);

    @Transactional(readOnly = true)
    Aluno findByNome(String nome);

    @Transactional(readOnly = true)
    Aluno findByEmail(String email);

}

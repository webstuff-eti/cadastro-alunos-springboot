package br.eti.webstuff.CadastroAlunos.web.controllers.converters;

import br.eti.webstuff.CadastroAlunos.entities.Aluno;
import br.eti.webstuff.CadastroAlunos.web.dto.request.AlunoRequestDto;
import br.eti.webstuff.CadastroAlunos.web.dto.response.AlunoResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ConverteAluno {

    /**
     * Converte Aluno para AlunoResponseDto.
     * @param aluno
     * @return alunoResponseDto
     */
    public AlunoResponseDto converteAlunoParaAlunoResponseDto(Aluno aluno) {
        AlunoResponseDto alunoResponseDto = new AlunoResponseDto();
        if(aluno != null) {
            if(aluno.getId() != null) {
                alunoResponseDto.setId(aluno.getId());
            }
            alunoResponseDto.setNome(aluno.getNome());
            alunoResponseDto.setCpf(aluno.getCpf());
            alunoResponseDto.setEmail(aluno.getEmail());
        }
        return alunoResponseDto;
    }

    /**
     * Converte AlunoRequestDto para Aluno.
     * @param alunoRequestDto
     * @return aluno
     */
    public Aluno converteAlunoRequestDtoParaAluno(AlunoRequestDto alunoRequestDto) {
        Aluno aluno = new Aluno();
        if(alunoRequestDto.getCpf() != null){
            aluno.setNome(alunoRequestDto.getNome());
            aluno.setCpf(alunoRequestDto.getCpf());
            aluno.setEmail(alunoRequestDto.getEmail());
        }
        return aluno;
    }

    /**
     * Converte AlunoRequestDto para Aluno.
     * @param alunos
     * @return alunosResponseDto
     */
    public List<AlunoResponseDto> converteListaAlunoParaAlunoListaResponseDto(List<Aluno> alunos) {
        List<AlunoResponseDto> alunosResponseDto = new ArrayList<>();
        if(alunos != null) {
            alunos.stream().forEach( aluno -> {
                alunosResponseDto.add(new AlunoResponseDto(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getEmail()));
            } );
        }
        return alunosResponseDto;
    }

}

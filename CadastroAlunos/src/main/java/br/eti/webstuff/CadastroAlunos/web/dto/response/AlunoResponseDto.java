package br.eti.webstuff.CadastroAlunos.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AlunoResponseDto {

    private Long id;
    private String nome;
    private String cpf;
    private String email;

}

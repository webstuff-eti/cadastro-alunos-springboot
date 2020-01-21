package br.eti.webstuff.CadastroAlunos.web.dto.response;

import lombok.*;

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

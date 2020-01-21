package br.eti.webstuff.CadastroAlunos.web.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
@ToString
public class AlunoRequestDto {

    @ApiModelProperty("Nome do aluno")
    @NotEmpty(message = "O  campo nome não pode ser vazio")
    @NotBlank(message="{name.not.blank}")
    private String nome;

    @ApiModelProperty("CPF do aluno")
    @NotEmpty(message = "O campo cpf não pode ser vazio")
    @NotBlank(message="O campo cpf deve ser inserido")
    private String cpf;

    @ApiModelProperty("Email do aluno")
    @NotEmpty(message = "O campo email não pode ser vazio")
    @NotBlank(message="O campo email deve ser inserido")
    @Email(message = "Digite um email válido")
    private String email;
}

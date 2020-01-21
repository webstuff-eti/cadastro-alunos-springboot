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
    @NotEmpty(message = "{name.not.empty}")
    @NotBlank(message="{name.not.blank}")
    private String nome;

    @ApiModelProperty("CPF do aluno")
    @NotEmpty(message = "{cpf.not.empty}")
    @NotBlank(message="{cpf.not.blank}")
    private String cpf;

    @ApiModelProperty("Email do aluno")
    @NotEmpty(message = "{email.not.empty}")
    @NotBlank(message="{email.not.blank}")
    @Email(message = "{email.validator}")
    private String email;

}

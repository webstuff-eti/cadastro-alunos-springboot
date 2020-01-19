package br.eti.webstuff.CadastroAlunos.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@ToString
public class AlunoRequestDto {

    @NotBlank(message = "Nome inválido")
    public String nome;

    @NotBlank(message="CPF invalido")
    private String cpf;

    @Email(message="Email inválidp")
    @NotBlank(message="Email vazio")
    private String email;
}

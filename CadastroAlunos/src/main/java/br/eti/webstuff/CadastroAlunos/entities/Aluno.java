package br.eti.webstuff.CadastroAlunos.entities;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty("Nome do aluno")
    @Column(name="nome", nullable=false)
    private String nome;

    @ApiModelProperty("Email do aluno")
    @Column(name="email", unique=true)
    private String email;

    @ApiModelProperty("CPF do aluno")
    @Column(name="cpf", unique=true)
    private String cpf;

    @ApiModelProperty("Data de cadastro do aluno")
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @ApiModelProperty("Data de atualização do aluno")
    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao;


    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

}

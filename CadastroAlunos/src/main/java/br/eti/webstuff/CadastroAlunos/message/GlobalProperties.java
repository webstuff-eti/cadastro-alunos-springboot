package br.eti.webstuff.CadastroAlunos.message;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:message.properties")
@ConfigurationProperties
public class GlobalProperties {

    private String cpf;
}

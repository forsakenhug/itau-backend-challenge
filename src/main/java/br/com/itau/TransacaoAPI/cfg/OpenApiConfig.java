package br.com.itau.TransacaoAPI.cfg;


import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// configuração do mr. bean!!!
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI meuOpenApidocumentacaoLegal() {
        return new OpenAPI()
                .info(new Info()
                        .title("Itaú Transação API By Forsaken")
                        .description("""
                                Projeto Teste e API para desafio Dev Spring Boot do Itaú
                                
                                O cara q ler isso tá amando minha organização
                                
                                **Rotinhas ai:**
                                - POSTZÃO -> Cria
                                - GETZÃO -> Stats
                                - DELCAO -> Limpa o Memory
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("O Incrível Forsaken"))
                        .license(new License()
                                .name("MIT Sem frescura")));
    }
    // agora me deu névoa cerebral
}

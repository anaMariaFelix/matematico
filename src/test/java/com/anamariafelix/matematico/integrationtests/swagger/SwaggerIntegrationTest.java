package com.anamariafelix.matematico.integrationtests.swagger;

import com.anamariafelix.matematico.config.TestConfigs;
import com.anamariafelix.matematico.integrationtests.testcontainers.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {//extends da classe que faz a logica de configuração do TestContainers

    @Test //deve exibir a página da interface do usuário do Swagger
    void shouldDisplaySwaggerUIPage(){
        //metodo do rest-assured
        var content = given()               //dada um url, na porta 8888, quando for execultado uma ação do tipo get, ele espera uma resposta 200, extraindo o conteudo do bory como uma string
                .basePath("/swagger-ui/index.html")
                    .port(TestConfigs.SERVER_PORT)//Pega a porta que foi definida na TestConfigs interface que criei
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        assertTrue(content.contains("Swagger UI"));
    }
}

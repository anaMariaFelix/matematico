package com.anamariafelix.matematico.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

//responsavel por criar o container do mysql dinamicamente atraves do TestContainer
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializar.class)
public class AbstractIntegrationTest {

    static class Initializar implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0"); //imagem do mysql, pode ser pega do docker hub

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();//inicializa o container a parti dessa imagens/instancia do mysql, so vai existir uma instancia desse containers durante a execução dos testes
            //ao final dos teste essa instancia do container morre
        }

        private Map<String, String> createConectionConfiguration() {
            //propriedades de conexão com o banco
            return Map.of(
                    "spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword()
            );
        }

        //primeiro metodo a ser execultado
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();//pegou o contexto do spring
            MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map)createConectionConfiguration());// adcionou as novas propriedades do testcontainer

            environment.getPropertySources().addFirst(testContainers);
        }
    }
}

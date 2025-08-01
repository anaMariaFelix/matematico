package com.anamariafelix.matematico.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.mail")//caminho do yml
public class EmailConfig {

    private String host; //4 variaveis do yml, devem esta iguais esta la
    private int port;
    private String username;
    private String password;

    private String from;
    private boolean ssl;
}

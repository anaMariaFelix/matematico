package com.anamariafelix.matematico.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "file") //file igual esta no yml
public class FileStorageConfig { //classe para o upload e download de arquivos

    // o mesmo upload-dir do yml
    private String uploaddir;
}

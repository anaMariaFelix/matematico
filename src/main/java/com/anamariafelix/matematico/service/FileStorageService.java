package com.anamariafelix.matematico.service;

import com.anamariafelix.matematico.config.FileStorageConfig;
import com.anamariafelix.matematico.exception.FileNotFoundException;
import com.anamariafelix.matematico.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileStorageService {

    private final Path fileStorageLocation; //define onde sera armazenado o arquivo no nosso sistema de arquivo

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploaddir()).toAbsolutePath().normalize(); //seta a propriedade upload-dir que foi definida no aplication.yml, e defini o caminho do diretorio onde sera salvo os arquivos e normaliza ele tratanto para que n tenha caracteres invalidos
        this.fileStorageLocation = path;

        try{
            log.info("Criando diretorio!");
            Files.createDirectories(fileStorageLocation); //cria o diretorio de armazenamento caso ele n exista

        } catch (Exception e) {
            log.error("Não pode criar o diretorio onde os arquivos seriam salvos");
            throw new FileStorageException("Não pode criar o diretorio onde os arquivos seriam salvos",e);
        }
    }

    //metodo para armazenar o arquivo em disco ou seja tentar gravar o arquivo
    public String storeFile(MultipartFile file){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //limpa o nome do arquivo, caso ele tenha caracteres que n sejam aceitos na hora de salvar o arquivo

        try{
            if(fileName.contains("..")){//significa que o nome do arquivo é invalido
                log.error("Desculpa! contém uma sequência de caminho inválida"+ fileName);
                throw new FileStorageException("Desculpa! contém uma sequência de caminho inválida" + fileName);
            }

            log.info("Salvando o arquivo em disco!");
            Path tragetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), tragetLocation, StandardCopyOption.REPLACE_EXISTING); //copia o arquivo no disco, caso ja exista um arquivo com esse nome ele sera sobrescrito por casa disso StandardCopyOption.REPLACE_EXISTING

            return fileName;

        }catch (Exception e){
            log.error("não foi possível armazenar o arquivo" + fileName + "tente novamente",e);
            throw new FileStorageException("não foi possível armazenar o arquivo" + fileName + "tente novamente",e);
        }
    }

    public Resource loadFilesAsResouces(String fileName){
        try{

            log.info("Salvando o arquivo em disco!");
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();// pega o caminho ate o arquivo, para chegar ate o arquivo que quero fazer download
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                log.error("Arquivo não encontrado "+ fileName);
                throw  new FileNotFoundException("Arquivo não encontrado "+ fileName);
            }
        }catch (Exception e){
            log.error("Arquivo não encontrado "+ fileName, e);
            throw  new FileNotFoundException("Arquivo não encontrado "+ fileName, e);
        }
    }

}

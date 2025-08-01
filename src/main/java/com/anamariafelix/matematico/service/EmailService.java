package com.anamariafelix.matematico.service;

import com.anamariafelix.matematico.config.EmailConfig;
import com.anamariafelix.matematico.dto.request.EmailRequestDTO;
import com.anamariafelix.matematico.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailSender emailSender;

    private final EmailConfig emailConfigs;

    //metodo responsavel por enviar um email simple
    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        emailSender
                .to(emailRequest.getTo())  //para onde vai
                .withSubject(emailRequest.getSubject())  //descrição
                .withMessage(emailRequest.getBody())//mensagem do email
                .send(emailConfigs);//envia o email
    }

    //metodo responsavel por enviar um email com anexos
    public void setEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
        File tempFile = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);//converte as coisas referentes ao email que vem como um jason sem ser pelo bory em um DTO

            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());//grava um arquivo temporariamnete prar poder enviar depois
            attachment.transferTo(tempFile);//deixa o arquivo em disco

            emailSender //coloca as informações do email
                    .to(emailRequest.getTo()) //para onde vai
                    .withSubject(emailRequest.getSubject()) //descrição
                    .withMessage(emailRequest.getBody()) //corpo do email
                    .attach(tempFile.getAbsolutePath())  //anexo ex: PDF
                    .send(emailConfigs); //envia o email

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request JSON!", e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachment!", e);
        } finally {
            if (tempFile != null && tempFile.exists()) tempFile.delete(); //deleta o arquivo q estava em disco
        }

    }
}

package com.anamariafelix.matematico.controller;

import com.anamariafelix.matematico.controller.docs.EmailControllerDocs;
import com.anamariafelix.matematico.dto.request.EmailRequestDTO;
import com.anamariafelix.matematico.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email/v1")
public class EmailController implements EmailControllerDocs {

    private final EmailService service;

    @PostMapping  //metodo para enviar email simples
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {

        service.sendSimpleEmail(emailRequestDTO);
        return new ResponseEntity<>("e-Mail sent with success!", HttpStatus.OK);
    }

    //metodo para enviar email com anexo
    @PostMapping(value = "/withAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//precisa ser feito dessa forma se n, não funciona
    public ResponseEntity<String> sendEmailWithAttachment(@RequestParam("emailRequest") String emailRequest,
            @RequestParam("attachment") MultipartFile attachment) //@RequestParam são passados no postman pelo body -> form-data a key deve ser exatamente igual esta no @RequestParam("emailRequest") caso contrario da erro
    {
        service.setEmailWithAttachment(emailRequest, attachment);

        return new ResponseEntity<>("e-Mail with attachment sent successfully!", HttpStatus.OK);
    }
}

package com.anamariafelix.matematico.mail;

import com.anamariafelix.matematico.config.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailSender {

    private final JavaMailSender mailSender;
    private String to;
    private String subject;
    private String body;
    private ArrayList<InternetAddress> recipients = new ArrayList<>();
    private File attachment; //referente a anexo, quando vc envia um email com enexos

    public EmailSender to(String to) {
        this.to = to;
        this.recipients = getRecipients(to);
        return this;
    }

    public EmailSender withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailSender withMessage(String body) {
        this.body = body;
        return this;
    }

    public EmailSender attach(String fileDir) {
        this.attachment = new File(fileDir);
        return this;
    }

    public void send(EmailConfig config){//metodo responsavel por enviar o email
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(config.getUsername());//para quem o email sera enviado
            helper.setTo(recipients.toArray(new InternetAddress[0]));
            helper.setSubject(subject);//assunto do email
            helper.setText(body, true);//mensagem do email

            if (attachment != null) {//adciona um anexo
                helper.addAttachment(attachment.getName(), attachment);//passa o nome e o arquivo em si
            }

            mailSender.send(message);

            log.info("Email sent to %s with the subject '%s'%n", to, subject);

            reset();//reseta as informações ao final de tudo

        } catch (MessagingException e) {
            throw new RuntimeException("Error sending the email", e);
        }

    }

    private void reset() {//resetando as informações ao finalizar o envio de email
        this.to = null;
        this.subject = null;
        this.body = null;
        this.recipients = null;
        this.attachment = null;
    }

    //cria um array list de endereços de email
    // email1@gmail.com;email2@gmail.com,email3@gmail.com
    private ArrayList<InternetAddress> getRecipients(String to) {

        String toWithoutSpaces = to.replaceAll("\\s", "");//remove espaços entres os email
        StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";");//tonqueriza

        ArrayList<InternetAddress> recipientsList = new ArrayList<>();

        while (tok.hasMoreElements()) {
            try {
                recipientsList.add(new InternetAddress(tok.nextElement().toString()));//adiciona na lista de internetaddress
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }
        return recipientsList;
    }
}

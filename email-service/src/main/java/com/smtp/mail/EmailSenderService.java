package com.smtp.mail;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    public void sendSimpleEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dangolprasanna2001@gmail.com");
        message.setTo(email.getToEmail());
        message.setText(email.getBody());
        message.setSubject(email.getSubject());
        mailSender.send(message);
    }

    // public void sendMailWithInlineResources(Email email, MultipartFile[] file)
    // throws MessagingException, IOException {
    // MimeMessage mimeMessage = mailSender.createMimeMessage();
    // MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    // helper.setFrom("dangolprasanna2001@gmail.com");
    // helper.setTo(email.getToEmail());
    // helper.setSubject(email.getSubject());
    // // helper.setText("<html><body><h2>this is html template</h2></body></html>",
    // // true);

    // ClassPathResource resource = new
    // ClassPathResource("com/smtp/mail/htmlTemp.html");
    // String htmlContent = StreamUtils.copyToString(resource.getInputStream(),
    // java.nio.charset.StandardCharsets.UTF_8);
    // helper.setText(htmlContent, true);

    // for (int i = 0; i < file.length; i++) {
    // helper.addAttachment(file[i].getOriginalFilename(), file[i]);
    // }
    // mailSender.send(mimeMessage);
    // }

    // public void sendMailWithTemplate(String emailJson, MultipartFile[] file,
    // MultipartFile tempFile)
    // throws MessagingException, IOException {

    // // Convert the JSON string to an Email object
    // ObjectMapper objectMapper = new ObjectMapper();
    // Email email = objectMapper.readValue(emailJson, Email.class);

    // MimeMessage mimeMessage = mailSender.createMimeMessage();
    // MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    // helper.setFrom("dangolprasanna2001@gmail.com");
    // helper.setTo(email.getToEmail());
    // helper.setSubject(email.getSubject());
    // String htmlContent = StreamUtils.copyToString(tempFile.getInputStream(),
    // java.nio.charset.StandardCharsets.UTF_8);
    // helper.setText(htmlContent, true);
    // for (int i = 0; i < file.length; i++) {
    // helper.addAttachment(file[i].getOriginalFilename(), file[i]);
    // }
    // mailSender.send(mimeMessage);
    // }

    @Autowired
    private TemplateEngine templateEngine;

    

    public void sendMailWithTemplate(String emailJson, MultipartFile[] file, MultipartFile tempFile)
            throws MessagingException, IOException {

        // Convert the JSON string to an Email object
        ObjectMapper objectMapper = new ObjectMapper();
        Email email = objectMapper.readValue(emailJson, Email.class);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("dangolprasanna2001@gmail.com");
        helper.setTo(email.getToEmail());
        helper.setSubject(email.getSubject());
        

        String name = "Prasanna Dangol";
        String balance = "10500";
        Integer[] transaction = { 72, 83 };
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("balance", balance);
        context.setVariable("transaction", transaction);

        // String htmlContentFile = StreamUtils.copyToString(tempFile.getInputStream(),
        // java.nio.charset.StandardCharsets.UTF_8);
        // ClassPathResource resource = new
        // ClassPathResource("com/smtp/mail/htmlTemplate.html");
        String htmlContent = templateEngine.process("htmlTemplate", context);

        helper.setText(htmlContent, true);
        if (file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                helper.addAttachment(file[i].getOriginalFilename(), file[i]);
            }
        }
        mailSender.send(mimeMessage);
    }

}

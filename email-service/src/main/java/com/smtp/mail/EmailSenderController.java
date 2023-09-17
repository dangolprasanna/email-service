package com.smtp.mail;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.mail.MessagingException;

@RestController
public class EmailSenderController {

    @Autowired
    public EmailSenderService sendEmail;

    @Autowired
    public EmailScheduler emailScheduler;

    @PostMapping("/send")
    public ResponseEntity<String> sendSimpleEmail(@RequestBody Email email) {
        sendEmail.sendSimpleEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body("Email is successfully Sent");
    }

    // @PostMapping("/sendTemp")
    // public String sendMailWithInlineResources(@RequestParam("emailData") String
    // emailJson,
    // @RequestParam("file") MultipartFile[] file) throws MessagingException,
    // IOException {
    // try {
    // // Convertc the JSON string to an Email object
    // ObjectMapper objectMapper = new ObjectMapper();
    // Email email = objectMapper.readValue(emailJson, Email.class);

    // // Send the email
    // sendEmail.sendMailWithInlineResources(email, file);

    // return "Email sent successfully!";
    // } catch (JsonProcessingException e) {
    // // Handle JSON parsing error
    // return "Error parsing JSON";
    // } catch (MailException | MessagingException e) {
    // // Handle email sending error
    // return "Error sending email";
    // }
    // }

    @PostMapping("/sendWithTemp")
    public String sendMailWithTemplate(@RequestParam("emailData") String emailJson,
            @RequestParam("file") MultipartFile[] file, @RequestParam("tempFile") MultipartFile tempFile)
            throws MessagingException, IOException {
        try {
            // Send the email
            sendEmail.sendMailWithTemplate(emailJson, file, tempFile);

            return "Email sent successfully!";
        } catch (JsonProcessingException e) {
            // Handle JSON parsing error
            return "Error parsing JSON";
        } catch (MailException e) {
            // Handle email sending error
            return "Error sending email";
        }
    }

    @PostMapping("/sendScheduledTemp")
    public ResponseEntity<?> sendScheduledMessage(@RequestParam("emailData") String emailJson,
            @RequestParam("file") MultipartFile[] file, @RequestParam("tempFile") MultipartFile tempFile,
            @RequestParam("DateTime") String DateTime) throws MessagingException, IOException, ParseException {

        emailScheduler.sendScheduledEmail(emailJson, file, tempFile, DateTime);
        return ResponseEntity.ok().body("Sent");
    }
}

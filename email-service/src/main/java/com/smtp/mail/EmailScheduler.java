package com.smtp.mail;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EmailScheduler {

    @Autowired
    public EmailSenderService senderService;

    Logger logger = LoggerFactory.getLogger(EmailScheduler.class);

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public ResponseEntity<String> sendScheduledEmail(String emailJson, MultipartFile[] file, MultipartFile tempFile,
                                                     String DateTime) throws MessagingException, IOException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date scheduledTime = dateFormat.parse(DateTime);
            long initialDelay = scheduledTime.getTime() - System.currentTimeMillis();
            logger.info("Total delay time: {}", DateTime);
            if (initialDelay > 0) {
                Thread.sleep(initialDelay);
                senderService.sendMailWithTemplate(emailJson, file, tempFile);
            }
        } catch (ParseException | InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("Email Scheduled");
    }

}

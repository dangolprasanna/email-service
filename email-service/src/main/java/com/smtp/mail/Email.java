package com.smtp.mail;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Email {
    private String[] toEmail;
    private String subject;
    private String body;
    List<Integer> l1 = new ArrayList<>();

    @JsonCreator
    public Email(@JsonProperty("toEmail") String[] toEmail,
                 @JsonProperty("subject") String subject,
                 @JsonProperty("body") String body) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }
}


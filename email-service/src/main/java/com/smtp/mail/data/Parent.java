package com.smtp.mail.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Parent {
    private Long id;
    private String name;
    private String emailAddress;
//    private Student student;
}

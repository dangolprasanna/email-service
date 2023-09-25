package com.smtp.mail.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectMark {
    private Long id;
    private String subjectName;
    private double mark;
//    private Student student;

}

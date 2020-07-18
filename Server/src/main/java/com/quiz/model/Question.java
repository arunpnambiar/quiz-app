package com.quiz.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public @Data  class Question {

    private String qid;
    private String question;
    private String options;
    private String qtype;
    private String answer;
    private String userid;
}

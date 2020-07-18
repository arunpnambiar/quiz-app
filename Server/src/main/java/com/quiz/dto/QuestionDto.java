package com.quiz.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public @Data  class QuestionDto {

    private String qid;
    private String question;
    private String options;
    private String qtype;
    private String userid;
}

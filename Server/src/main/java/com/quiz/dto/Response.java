package com.quiz.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public @Data class Response {
    private String userid;
    private  List<QuestionDto> questionsList;
    private double percentage;
}

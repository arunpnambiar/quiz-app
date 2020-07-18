package com.quiz.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public @Data class Answer {
    private String id;
    private String answer;
    private String userid;
}

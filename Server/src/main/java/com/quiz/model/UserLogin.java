package com.quiz.model;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public @Data class UserLogin {
    private UUID id;
    private String username;
    private String password;
    private String type;
}

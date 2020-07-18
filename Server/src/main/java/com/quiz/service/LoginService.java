package com.quiz.service;

import com.quiz.model.Question;
import com.quiz.model.UserLogin;

import java.util.List;

public interface LoginService {
    List<Question> login(UserLogin loginDetails);
}

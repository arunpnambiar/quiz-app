package com.quiz.controller;

import com.quiz.dto.QuestionDto;
import com.quiz.dto.Response;
import com.quiz.model.Question;
import com.quiz.model.UserLogin;
import com.quiz.service.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    LoginService loginService;

    @PostMapping
    @RequestMapping("/public/v1/login")
    public Response login(@RequestBody() UserLogin loginDetails){
        Response response = new Response();
        List<Question> questionList = loginService.login(loginDetails);
        response.setUserid(questionList.get(0).getUserid());
        response.setQuestionsList(questionList.stream().map(this::convertToDto).collect(Collectors.toList()));
        return response;
    }

    private QuestionDto convertToDto(Question question) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        return questionDto;
    }
}


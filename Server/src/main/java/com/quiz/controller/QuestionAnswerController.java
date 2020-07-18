package com.quiz.controller;

import com.quiz.dto.Response;
import com.quiz.model.Answer;
import com.quiz.model.Question;
import com.quiz.dto.QuestionDto;
import com.quiz.service.LoginService;
import com.quiz.service.QuestionAnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class QuestionAnswerController {
    @Autowired
    QuestionAnswerService questionAnswerService;
    @Autowired
    ModelMapper modelMapper;
    @GetMapping
    @RequestMapping("/private/v1/findallquestions/{uid}/{quetsiontype}")
    public Response fetchRandomQuestions(@PathVariable("uid") String uid,@PathVariable("quetsiontype") String type) throws Exception {
        Response response = new Response();
        List<Question> question = questionAnswerService.findByAllRandomQuestions(uid,type,"U");
        response.setUserid(question.get(0).getUserid());
        response.setQuestionsList(question.stream().map(this::convertToDto).collect(Collectors.toList()));
        return response;
    }

    @PostMapping
    @RequestMapping("/private/v1/getresult/{uid}")
    public Response getResult(@PathVariable("uid") String uid,@RequestBody() List<Answer> answer) throws Exception {
        Response response = new Response();
        response.setUserid(uid);
        response.setPercentage(questionAnswerService.checkQuestionAnswerMark(answer, uid));
        return response;
    }

    private QuestionDto convertToDto(Question question) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        return questionDto;
    }
}

package com.quiz.service;

import com.quiz.model.Answer;
import com.quiz.model.Question;
import java.util.List;
import java.util.Random;

public interface QuestionAnswerService {

    List<Question> findByAllRandomQuestions(String uid,String type,String pageFrom) ;
    double checkQuestionAnswerMark(List<Answer> answerList, String uid);
    public default int generateRandom(int upperLimit){
        Random random = new Random();
        return random.nextInt(upperLimit);
    }
}

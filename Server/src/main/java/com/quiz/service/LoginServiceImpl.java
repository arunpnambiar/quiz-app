package com.quiz.service;

import com.opencsv.CSVWriter;
import com.quiz.config.Common;
import com.quiz.errormanagement.FileNotFoundError;
import com.quiz.errormanagement.UserValidation;
import com.quiz.model.Question;
import com.quiz.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements  LoginService {
    @Autowired
    QuestionAnswerService questionAnswerService;
    private static final String  loginFilePath= "src" + File.separator + "main" + File.separator +"input"+File.separator+"login"+File.separator+"login.csv";
    @Override
    public List<Question> login(UserLogin loginDetails) {
        final List<String[]> loginList;
        try {
            loginList = Common.readCSVFiles(loginFilePath);
        } catch (IOException e) {
            throw new FileNotFoundError(e.getMessage());
        }
        String[] userDetails = checkUsernamePresent(loginList, loginDetails.getUsername());
        if(null != userDetails){
            if(userDetails[2].equals(Common.generateHash(loginDetails.getPassword()))){
                return questionAnswerService.findByAllRandomQuestions(userDetails[0],loginDetails.getType(),"L");

            }else{
                throw new UserValidation("Invalid password or username already used by another person");
            }
        }else{
            String password = Common.generateHash(loginDetails.getPassword());
            UUID uuid = UUID.randomUUID();
            FileWriter outfile = null;
            try {
                outfile = new FileWriter(loginFilePath);
                CSVWriter writer = new CSVWriter(outfile);
                loginList.add(new String[]{uuid.toString(),loginDetails.getUsername(),password});
                writer.writeAll(loginList);
                writer.close();
            } catch (IOException e) {
                throw new FileNotFoundError(e.getMessage());
            }
           return  questionAnswerService.findByAllRandomQuestions(uuid.toString(),loginDetails.getType(),"L");
        }
    }

    private String[] checkUsernamePresent(List<String[]> userList,String username){
        for(String[] users : userList){
            if(users[1].equals(username)){
                return users;
            }
        }
        return null;
    }

}

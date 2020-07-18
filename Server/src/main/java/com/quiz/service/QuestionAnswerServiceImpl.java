package com.quiz.service;

import com.opencsv.CSVWriter;
import com.quiz.config.Common;
import com.quiz.errormanagement.FileNotFoundError;
import com.quiz.errormanagement.UserValidation;
import com.quiz.model.Answer;
import com.quiz.model.Question;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    public static final int upperLimit = 4;
    private static final String  loginFilePath= "src" + File.separator + "main" + File.separator +"input"+File.separator+"login"+File.separator+"login.csv";
    @Override
    public List<Question> findByAllRandomQuestions(String uid,String type,String pageFrom)  {
        Map<Integer,String> checkDuplicateKey = new HashMap<>();
        List<Question> tempList = new ArrayList<>();
        if(!"L".equalsIgnoreCase(pageFrom)){
            final List<String[]> loginList;
            try {
                loginList = Common.readCSVFiles(loginFilePath);
            } catch (IOException e) {
                throw new FileNotFoundError(e.getMessage());
            }
            if(!checkUsernamePresent(loginList,uid)){
                throw new UserValidation("Invalid user-id or user-id not present in our system. Please Login");
            }
        }
        int index=0;
        List<Question> questionsList = new ArrayList<>();
        String Question_path = "src" + File.separator + "main" + File.separator +"input"+File.separator+"questions"+File.separator+type+".csv";
        String answer_path = "src" + File.separator + "main" + File.separator +"input"+File.separator+"questionsByUser"+File.separator+uid+".csv";
        List<String[]> records = null;
        try {

            Path path = Paths.get(answer_path);
            Files.deleteIfExists(path);       // delete file before already exist same name...
            records = Common.readCSVFiles(Question_path);
        } catch (IOException e) {
            throw new FileNotFoundError(e.getMessage());
        }
        for (String[] record : records) {
            tempList.add(new Question(record[0],
                    record[1],
                    record[2],
                    record[3],
                    record[4],uid));
        }
        FileWriter outfile = null;
        try {
            outfile = new FileWriter(answer_path);
        } catch (IOException e) {
            throw new FileNotFoundError(e.getMessage());
        }
        CSVWriter writer = new CSVWriter(outfile);
        while(upperLimit != index) {
            int randomIndex = generateRandom(tempList.size());
            if(!checkDuplicateKey.containsKey(randomIndex)){
                questionsList.add(tempList.get(randomIndex)
                                  );
                writer.writeNext(new String[]{tempList.get(randomIndex).getQid(),tempList.get(randomIndex).getAnswer()});
                checkDuplicateKey.put(randomIndex,String.valueOf(index));
                index++;
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new FileNotFoundError(e.getMessage());
        }
        return questionsList;
    }

    @Override
    public double checkQuestionAnswerMark(List<Answer> answerList, String uid)  {
        List<String[]> recordsList =null;
        String answer_path = "src" + File.separator + "main" + File.separator +"input"+File.separator+"questionsByUser"+File.separator+uid+".csv";
        try {
               recordsList = Common.readCSVFiles(answer_path);
               Path path = Paths.get(answer_path);
               Files.deleteIfExists(path);       // delete file before already exist same name...
        }
        catch (IOException  e){
            throw new FileNotFoundError("Time out exception or you are requested for old exam result or invalid user-id. Please check!!!");
        }
        return getResult(recordsList,answerList);
    }

    private double getResult(List<String[]> recordsList, List<Answer> answerList) {
        int correctAnswer = 0;
        for (String[] records: recordsList){
            correctAnswer += answerList.stream().filter(element -> element.getId().equalsIgnoreCase(records[0]) && element.getAnswer().equalsIgnoreCase(records[1])).count();
        }
        return ((double)correctAnswer /(double) upperLimit) * 100;
    }

    private boolean checkUsernamePresent(List<String[]> userList,String uid){
        for(String[] users : userList){
            if(users[0].equals(uid)){
                return true;
            }
        }
        return false;
    }
}

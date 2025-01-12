package com.project.QuizApp.Service;

import com.project.QuizApp.DAO.QuestionDAO;
import com.project.QuizApp.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionDAO questionDAO;

    public QuestionService(QuestionDAO questionDAO){
        this.questionDAO=questionDAO;
    }

    public List<Question> getAllQuestions(){
        return questionDAO.findAll();
    }
}

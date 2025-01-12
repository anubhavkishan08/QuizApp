package com.project.QuizApp.Service;

import com.project.QuizApp.DAO.QuestionDAO;
import com.project.QuizApp.Model.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private QuestionDAO questionDAO;

    public QuestionService(QuestionDAO questionDAO){
        this.questionDAO=questionDAO;
    }

    public ResponseEntity<List<Question>> getAllQuestions()
    {
        try {
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String questionCategory) {
        try {
            return new ResponseEntity<>(questionDAO.findQuestionsByCategory(questionCategory), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addQuestion(Question question) {
            questionDAO.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        Optional<Question> question=questionDAO.findById(id);
        if(question.isPresent()){
            questionDAO.delete(question.get());
            return new ResponseEntity<>("Question is deleted",HttpStatus.OK);
        }else
            return new ResponseEntity<>("Question Not found",HttpStatus.NOT_FOUND);
    }
}

package com.project.QuizApp.Controller;

import com.project.QuizApp.Model.Question;
import com.project.QuizApp.Service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService=questionService;
    }

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{questionCategory}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String questionCategory){
        return  questionService.getQuestionsByCategory(questionCategory);
    }

    @PostMapping("/addQuestion")
    public String addQuestion(@RequestBody Question question){
        questionService.addQuestion(question);
        return "Question added!....";

    }

    @DeleteMapping("/deletQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }
}

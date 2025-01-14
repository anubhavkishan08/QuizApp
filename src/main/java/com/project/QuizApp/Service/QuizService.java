package com.project.QuizApp.Service;

import com.project.QuizApp.DAO.QuestionDAO;
import com.project.QuizApp.DAO.QuizDAO;
import com.project.QuizApp.DTO.QuestionDTO;
import com.project.QuizApp.Model.Question;
import com.project.QuizApp.Model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;

    public QuizService(QuizDAO quizDAO,QuestionDAO questionDAO){
        this.quizDAO=quizDAO;
        this.questionDAO=questionDAO;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions=questionDAO.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questions);

        quizDAO.save(quiz);

        return new ResponseEntity<>("Quiz is created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(int id) {
        Optional<Quiz> quiz =quizDAO.findById(id);
        List<Question> questionsfromDB=quiz.get().getQuestionList();
        //System.out.println(quiz);
        //System.out.println(questionsfromDB);
        List<QuestionDTO> questionForUser=new ArrayList<>();
        for(Question q:questionsfromDB){
            QuestionDTO questionDTO=new QuestionDTO(q.getId(),q.getQuestionTitle(),
                    q.getOption1(),q.getOption2(), q.getOption3(), q.getOption4());
            questionForUser.add(questionDTO);
        }
        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }
}

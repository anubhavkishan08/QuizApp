package com.project.QuizApp.Service;

import com.project.QuizApp.DAO.QuestionDAO;
import com.project.QuizApp.DAO.QuizDAO;
import com.project.QuizApp.DTO.QuestionDTO;
import com.project.QuizApp.DTO.Response;
import com.project.QuizApp.Model.Question;
import com.project.QuizApp.Model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

    public ResponseEntity<Integer> checkQuizAnswers(int id, List<Response> responses) {
        // Find the quiz by id
        Optional<Quiz> quizOptional = quizDAO.findById(id);

        // If quiz is not found, return a not found response
        if (!quizOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Get the list of questions from the quiz
        List<Question> questions = quizOptional.get().getQuestionList();

        // Ensure the responses list matches the number of questions
        if (responses.size() != questions.size()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // In case of mismatch
        }

        // Use streams to count correct answers
        long correctAnswers = IntStream.range(0, responses.size())
                .filter(i -> responses.get(i).getResponse().equals(questions.get(i).getRightOption()))
                .count();

        // Return the number of correct answers
        return new ResponseEntity<>(Math.toIntExact(correctAnswers), HttpStatus.OK);
    }


//    public ResponseEntity<Integer> checkQuizAnswers(int id, List<Response> response) {
//        Optional<Quiz> quiz =quizDAO.findById(id);
//        List<Question> questions=quiz.get().getQuestionList();
//        int right=0,i=0;
//        for(Response r:response){
//            if(r.getResponse().equals(questions.get(i).getRightOption()))
//                right++;
//            i++;
//        }
//        return new ResponseEntity<>(right,HttpStatus.OK);
//    }
}

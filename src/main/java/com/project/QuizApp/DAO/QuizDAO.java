package com.project.QuizApp.DAO;

import com.project.QuizApp.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public interface QuizDAO extends JpaRepository<Quiz,Integer> {

}

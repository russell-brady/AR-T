package com.arproject.russell.ar_t.models;

import java.util.ArrayList;

public class QuizResponse {

    private int code;
    private String success;
    private ArrayList<CompletedQuiz> completedQuizzes;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<CompletedQuiz> getCompletedQuizzes() {
        return completedQuizzes;
    }

    public void setCompletedQuizzes(ArrayList<CompletedQuiz> completedLessons) {
        this.completedQuizzes = completedLessons;
    }
}

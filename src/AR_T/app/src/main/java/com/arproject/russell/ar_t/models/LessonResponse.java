package com.arproject.russell.ar_t.models;

import java.util.ArrayList;

public class LessonResponse {

    private int code;
    private String success;
    private ArrayList<CompletedLesson> completedLessons;

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

    public ArrayList<CompletedLesson> getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(ArrayList<CompletedLesson> completedLessons) {
        this.completedLessons = completedLessons;
    }
}

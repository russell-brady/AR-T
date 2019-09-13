package com.arproject.russell.ar_t.models;

import com.google.ar.sceneform.Node;

public class QuizQuestion {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answerNumber;
    private Node modelNode;

    public QuizQuestion() {
    }

    public QuizQuestion(String question, String option1, String option2, String option3, int answerNumber, Node modelNode) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNumber = answerNumber;
        this.modelNode = modelNode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    public Node getModelNode() {
        return modelNode;
    }

    public void setModelNode(Node modelNode) {
        this.modelNode = modelNode;
    }
}

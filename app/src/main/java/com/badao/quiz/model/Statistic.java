package com.badao.quiz.model;

public class Statistic {
    private String projectName;
    private int numberPlayed ;
    private int numberCorrect ;
    private int numberAnswer ;

    public Statistic(String projectName, int numberPlayed, int numberCorrect, int numberAnswer) {
        this.projectName = projectName;
        this.numberPlayed = numberPlayed;
        this.numberCorrect = numberCorrect;
        this.numberAnswer = numberAnswer;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getNumberPlayed() {
        return numberPlayed;
    }

    public void setNumberPlayed(int numberPlayed) {
        this.numberPlayed = numberPlayed;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public int getNumberAnswer() {
        return numberAnswer;
    }

    public void setNumberAnswer(int numberAnswer) {
        this.numberAnswer = numberAnswer;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "projectName='" + projectName + '\'' +
                ", numberPlayed=" + numberPlayed +
                ", numberCorrect=" + numberCorrect +
                ", numberAnswer=" + numberAnswer +
                '}';
    }
}

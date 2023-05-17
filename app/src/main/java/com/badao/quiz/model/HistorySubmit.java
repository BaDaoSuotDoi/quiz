package com.badao.quiz.model;

import java.util.ArrayList;
import java.util.List;

public class HistorySubmit {
    private int id;
    private int projectId;
    private boolean isSync = false;
    private int timeElapsed;
    private String submittedAt;
    private int correctAnswerNumber;
    private int noAnswerNumber;
    private int questionNumber;

    private Project project;

    public HistorySubmit(int id, int projectId, boolean isSync, int timeElapsed, String submittedAt, int correctAnswerNumber, int noAnswerNumber, int questionNumber) {
        this.id = id;
        this.projectId = projectId;
        this.isSync = isSync;
        this.timeElapsed = timeElapsed;
        this.submittedAt = submittedAt;
        this.correctAnswerNumber = correctAnswerNumber;
        this.noAnswerNumber = noAnswerNumber;
        this.questionNumber = questionNumber;
    }

    public HistorySubmit(int projectId, int timeElapsed, String submittedAt, int correctAnswerNumber, int noAnswerNumber, int questionNumber) {
        this.projectId = projectId;
        this.timeElapsed = timeElapsed;
        this.submittedAt = submittedAt;
        this.correctAnswerNumber = correctAnswerNumber;
        this.noAnswerNumber = noAnswerNumber;
        this.questionNumber = questionNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(boolean sync) {
        isSync = sync;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public void setCorrectAnswerNumber(int correctAnswerNumber) {
        this.correctAnswerNumber = correctAnswerNumber;
    }

    public int getNoAnswerNumber() {
        return noAnswerNumber;
    }

    public void setNoAnswerNumber(int noAnswerNumber) {
        this.noAnswerNumber = noAnswerNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    @Override
    public String toString() {
        return "HistorySubmit{" +
                "ID=" + id +
                ", projectId=" + projectId +
                ", isSync=" + isSync +
                ", timeElapsed=" + timeElapsed +
                ", submittedAt='" + submittedAt + '\'' +
                ", correctAnswerNumber=" + correctAnswerNumber +
                ", noAnswerNumber=" + noAnswerNumber +
                ", questionNumber=" + questionNumber +
                '}';
    }
}

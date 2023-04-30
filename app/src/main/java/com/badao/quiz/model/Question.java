package com.badao.quiz.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int ID;
    private String content = "";
    private String comment = "";
    private String createdAt = "";
    private String lastUpdated = "";
    private int type;
    private boolean isSync;

    private List<QuestionAnswer> answers = new ArrayList<>();
    private List<RecordUserAnswer> userAnswers = new ArrayList<>();

    public Question() {
        this.answers.add(new QuestionAnswer());
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public List<QuestionAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionAnswer> answers) {
        this.answers = answers;
    }

    public List<RecordUserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<RecordUserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "ID=" + ID +
                ", content='" + content + '\'' +
                ", comment='" + comment + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", type=" + type +
                ", isSync=" + isSync +
                ", answers=" + answers +
                ", userAnswers=" + userAnswers +
                '}';
    }
}

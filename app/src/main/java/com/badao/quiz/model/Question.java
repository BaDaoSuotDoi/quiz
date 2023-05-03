package com.badao.quiz.model;

import com.badao.quiz.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int ID = 0;
    private int index = 0;
    private String content = "";
    private String createdAt = "";
    private String lastUpdated = "";
    private int type = AppConstants.QUESTION_NORMAL_TYPE;
    private boolean isSync;
    private String comment = "";

    private boolean isTemp = false;

    public Question(int ID, String content, String createdAt, String lastUpdated, int type, boolean isSync, String comment) {
        this.ID = ID;
        this.content = content;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.type = type;
        this.isSync = isSync;
        this.comment = comment;
        this.userAnswers = new RecordUserAnswer(this.ID);
    }

    private List<QuestionAnswer> answers = new ArrayList<>();
    private RecordUserAnswer userAnswers ;

    public Question(int id) {
        this.ID = id;
        this.answers.add(new QuestionAnswer(this.ID));
    }

    public Question(int index, boolean isTemp) {
        this.index = index;
        this.answers.add(new QuestionAnswer(this.ID));
        this.isTemp = isTemp;
    }
    public Question() {
        this.answers.add(new QuestionAnswer(this.ID));
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

    public RecordUserAnswer getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(RecordUserAnswer userAnswers) {
        this.userAnswers = userAnswers;
    }

    public boolean isTemp() {
        return isTemp;
    }

    public void setTemp(boolean temp) {
        isTemp = temp;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

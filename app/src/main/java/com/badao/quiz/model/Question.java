package com.badao.quiz.model;

import com.badao.quiz.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int ID = 0;
    private String content = "";
    private String createdAt = "";
    private String lastUpdated = "";
    private int type = AppConstants.QUESTION_NORMAL_TYPE;
    private boolean isSync;
    private String comment = "";
    private int position = 0;

    private boolean isTemp = false;
    private boolean isViewed = false;

    public Question(int ID, String content, String createdAt, String lastUpdated, int type, boolean isSync, String comment, int position) {
        this.ID = ID;
        this.content = content;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.type = type;
        this.isSync = isSync;
        this.comment = comment;
        this.position = position;
        this.userAnswers = new RecordUserAnswer(this.ID, this.type);
    }

    public boolean isValid(){
        return (!this.isTemp && !(this.ID == 0 && !this.isViewed));
    }
    private List<QuestionAnswer> answers = new ArrayList<>();
    private RecordUserAnswer userAnswers ;

    public Question(int id) {
        this.ID = id;
        this.answers.add(new QuestionAnswer(this.ID));
    }

    public Question(int position, boolean isTemp) {
        this.position = position;
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

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Question{" +
                "ID=" + ID +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", type=" + type +
                ", isSync=" + isSync +
                ", comment='" + comment + '\'' +
                ", position=" + position +
                ", isTemp=" + isTemp +
                ", isViewed=" + isViewed +
                ", answers=" + answers +
                ", userAnswers=" + userAnswers +
                '}';
    }
}

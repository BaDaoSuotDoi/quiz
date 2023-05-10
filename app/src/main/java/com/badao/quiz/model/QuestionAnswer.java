package com.badao.quiz.model;

import com.badao.quiz.constants.AppConstants;

public class QuestionAnswer {
    private int ID;
    private int questionId;
    private String content = "";
    private int type = AppConstants.QUESTION_NORMAL_TYPE;
    private boolean isSync = false;
    private String createdAt ;
    private String lastUpdated;

    public QuestionAnswer(int questionId) {
        this.questionId = questionId;
    }

    public QuestionAnswer(int questionId, int type) {
        if(type == AppConstants.QUESTION_SELECTION_TYPE){
            this.content = AppConstants.TOKEN_FALSE_SELECT_ANSWER;
        }
        this.type = type;
        this.questionId = questionId;
    }

    public QuestionAnswer(int ID,int questionId, String content, int type, boolean isSync, String createdAt, String lastUpdated) {
        this.ID = ID;
        this.questionId = questionId;
        this.content = content;
        this.type = type;
        this.isSync = isSync;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
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

    public void addContent(String content) {
        this.content += content;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "ID=" + ID +
                ", questionId=" + questionId +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", isSync=" + isSync +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}

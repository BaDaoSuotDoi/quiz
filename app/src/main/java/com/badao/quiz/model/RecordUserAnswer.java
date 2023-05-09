package com.badao.quiz.model;

public class RecordUserAnswer {
    private int ID = 0;
    private int historyId;
    private int questionId;
    private String answer = "";
    private int status = 0;
    private boolean isSync = false;
    private String createdAt;
    private String lastUpdated;

    public RecordUserAnswer(int questionId){
        this.questionId = questionId;
    }

    public RecordUserAnswer(int ID,int historyId,int questionId, String answer, int status, boolean isSync, String createdAt, String lastUpdated) {
        this.ID = ID;
        this.historyId = historyId;
        this.questionId = questionId;
        this.answer = answer;
        this.status = status;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "RecordUserAnswer{" +
                "ID=" + ID +
                ", historyId=" + historyId +
                ", questionId=" + questionId +
                ", answer='" + answer + '\'' +
                ", status=" + status +
                ", isSync=" + isSync +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}

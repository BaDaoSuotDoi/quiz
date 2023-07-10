package com.badao.quiz.model;

import com.badao.quiz.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int id = 0;
    private String content = "";
    private String createdAt = "";
    private String lastUpdated = "";
    private int type = AppConstants.QUESTION_NORMAL_TYPE;
    private boolean isSync;
    private String comment = "";
    private int position = 0;

    private boolean isTemp = false;
    private boolean isViewed = false;
    private int status = 0;
    private int projectId;
    private int version;

    public Question(int id, String content, String createdAt, String lastUpdated, int type, boolean isSync, String comment, int position,
                    int status, int projectId, int version) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.type = type;
        this.isSync = isSync;
        this.comment = comment;
        this.position = position;
        this.userAnswers = new RecordUserAnswer(this.id, this.type);
        this.status = status;
        this.projectId = projectId;
        this.version = version;
    }

    public boolean isValid(){
        return (!this.isTemp && !(this.id == 0 && !this.isViewed));
    }
    private List<QuestionAnswer> answers = new ArrayList<>();
    private RecordUserAnswer userAnswers ;

    public Question(int id) {
        this.id = id;
        this.answers.add(new QuestionAnswer(this.id));
    }

    public Question(int projectId,int position, boolean isTemp, int type) {
        this.projectId = projectId;
        this.position = position;
        this.answers.add(new QuestionAnswer(this.id));
        this.isTemp = isTemp;
        this.version = 1;
        this.status = 1;
        this.type = type;
    }
    public Question() {
        this.answers.add(new QuestionAnswer(this.id));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(boolean sync) {
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Question{" +
                "ID=" + id +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", type=" + type +
                ", isSync=" + isSync +
                ", comment='" + comment + '\'' +
                ", position=" + position +
                ", isTemp=" + isTemp +
                ", isViewed=" + isViewed +
                ", status=" + status +
                ", projectId=" + projectId +
                ", version=" + version +
                ", answers=" + answers +
                ", userAnswers=" + userAnswers +
                '}';
    }

    public void upgrade() {
        this.version += 1;
    }
}

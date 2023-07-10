package com.badao.quiz.model;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private String createdAt;
    private String lastUpdated;
    private boolean isRandom = true;
    private int questionPerSession = 10;
    private int duration = -1 ;
    private int mode = AppConstants.PROJECT_EXAM_MODE;
    private boolean isSync = false;
    private String schedule = "";
    private int type = 0;
    private List<Question> questions = new ArrayList<>();

    public Project(int id, String name, String createdAt, String lastUpdated, boolean isRandom, int questionPerSession, int duration, int mode, boolean isSync, String schedule, int type) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.isRandom = isRandom;
        this.questionPerSession = questionPerSession;
        this.duration = duration;
        this.mode = mode;
        this.isSync = isSync;
        this.schedule = schedule;
        this.type = type;
    }

    public Project(String name){
        this.name = name;
        this.createdAt = Utils.getTimeCurrent();
        this.lastUpdated = Utils.getTimeCurrent();

    }
    public String getNameType(){
        if(this.type == AppConstants.PROJECT_NORMAL_TYPE){
            return "Normal";
        }
        if(this.type == AppConstants.PROJECT_VOCABULARY_TYPE){
            return "Vocabulary";
        }
        return "";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getIsRandom() {
        return isRandom;
    }

    public void setIsRandom(boolean random) {
        isRandom = random;
    }

    public int getQuestionPerSession() {
        return questionPerSession;
    }

    public void setQuestionPerSession(int questionPerSession) {
        this.questionPerSession = questionPerSession;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(boolean sync) {
        isSync = sync;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", isRandom=" + isRandom +
                ", questionPerSession=" + questionPerSession +
                ", duration=" + duration +
                ", mode=" + mode +
                ", isSync=" + isSync +
                ", schedule='" + schedule + '\'' +
                ", type=" + type +
                ", questions=" + questions +
                '}';
    }
}

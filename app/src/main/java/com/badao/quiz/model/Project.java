package com.badao.quiz.model;

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
    private int mode = 1;
    private boolean isSync = false;
    private String schedule = "";
    private List<Question> questions = new ArrayList<>();

    public Project(int id, String name, String createdAt, String lastUpdated, boolean isRandom, int questionPerSession, int duration, int mode, boolean isSync) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.isRandom = isRandom;
        this.questionPerSession = questionPerSession;
        this.duration = duration;
        this.mode = mode;
        this.isSync = isSync;
    }

    public Project(String name){
        this.name = name;
        this.createdAt = Utils.getTimeCurrent();
        this.lastUpdated = Utils.getTimeCurrent();

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

    public boolean isRandom() {
        return isRandom;
    }

    public void setRandom(boolean random) {
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

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
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

    @Override
    public String toString() {
        return "Project{" +
                "ID=" + id +
                ", name='" + name + '\'' +
                ", createAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", isRandom=" + isRandom +
                ", questionPerSession=" + questionPerSession +
                ", duration=" + duration +
                ", mode=" + mode +
                ", isSync=" + isSync +
                ", questions=" + questions +
                '}';
    }
}

package com.badao.quiz.model;

import java.util.List;

public class Question {
    private int ID;
    private String content;
    private String createdAt;
    private String lastUpdated;
    private int type;
    private boolean isSync;

    private List<QuestionAnswer> answers;
    private List<RecordUserAnswer> userAnswers;

}

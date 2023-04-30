package com.badao.quiz.model;

import com.badao.quiz.constants.AppConstants;

public class QuestionAnswer {
    private int ID;
    private String content = "";
    private int type = AppConstants.ANSWER_FILL_INPUT;
    private boolean isSync = false;
    private String createdAt ;
    private String lastUpdated;

    public QuestionAnswer() {

    }
}

package com.badao.quiz.function.question.play.vocabulary.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.function.question.play.presenter.QuestionPlayContract;

public class QuestionVocabularyPlayPresenter extends BasePresenter<QuestionVocabularyPlayContract.View> implements QuestionVocabularyPlayContract.Presenter{
    public QuestionVocabularyPlayPresenter(Context context) {
        super(context);
    }
}

package com.badao.quiz.function.project.play.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.question.edit.view.QuestionEditFragment;
import com.badao.quiz.function.question.play.QuestionPlayBaseFragment;
import com.badao.quiz.function.question.play.view.QuestionPlayFragment;
import com.badao.quiz.function.question.play.vocabulary.view.QuestionVocabularyPlayFragment;
import com.badao.quiz.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectPlayAdapter extends FragmentStateAdapter {
    private Project project;
    private int viewMode;
    private List<QuestionPlayBaseFragment> questionPlayBaseFragments;
    public ProjectPlayAdapter(@NonNull FragmentActivity fragmentActivity, Project project, int viewMode) {
        super(fragmentActivity);
        this.project = project;
        this.viewMode = viewMode;
        this.questionPlayBaseFragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("createFragment", position+"//"+viewMode);
        QuestionPlayBaseFragment questionPlayBaseFragment = null;
        if(project.getType() == AppConstants.PROJECT_NORMAL_TYPE){
            questionPlayBaseFragment = new QuestionPlayFragment( project.getQuestions().get(position),position, viewMode);
        }else if(project.getType() == AppConstants.PROJECT_VOCABULARY_TYPE){
            questionPlayBaseFragment = new QuestionVocabularyPlayFragment( project.getQuestions().get(position),position, viewMode);
        }
        questionPlayBaseFragments.add(questionPlayBaseFragment);
        return questionPlayBaseFragment;
    }

    @Override
    public int getItemCount() {
        return project.getQuestions().size();
    }

    @Override
    public long getItemId(int position) {
        return project.getQuestions().get(position).getId();
    }

    public void setViewMode(int viewMode){
        this.viewMode = viewMode;
        for(QuestionPlayBaseFragment questionPlayFragment: questionPlayBaseFragments){
            questionPlayFragment.setViewMode(viewMode);
        }
    }

    public void setViewMode(int viewMode, int idx){
        for(QuestionPlayBaseFragment questionPlayFragment: questionPlayBaseFragments){
            if(questionPlayFragment.getPosition() == idx){
                questionPlayFragment.setViewMode(viewMode);
            }
        }
    }
}

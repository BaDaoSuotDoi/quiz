package com.badao.quiz.function.project.play.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.badao.quiz.function.question.edit.view.QuestionEditFragment;
import com.badao.quiz.function.question.play.view.QuestionPlayFragment;
import com.badao.quiz.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectPlayAdapter extends FragmentStateAdapter {
    private Project project;
    private int viewMode;
    private List<QuestionPlayFragment> questionPlayFragments;
    public ProjectPlayAdapter(@NonNull FragmentActivity fragmentActivity, Project project, int viewMode) {
        super(fragmentActivity);
        this.project = project;
        this.viewMode = viewMode;
        this.questionPlayFragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("createFragment", position+"//"+viewMode);
        QuestionPlayFragment questionPlayFragment = new QuestionPlayFragment(position, project.getQuestions().get(position), viewMode);
        questionPlayFragments.add(questionPlayFragment);
        return questionPlayFragment;
    }

    @Override
    public int getItemCount() {
        return project.getQuestions().size();
    }

    @Override
    public long getItemId(int position) {
        return project.getQuestions().get(position).getID();
    }

    public void setViewMode(int viewMode){
        this.viewMode = viewMode;
        for(QuestionPlayFragment questionPlayFragment: questionPlayFragments){
            questionPlayFragment.setViewMode(viewMode);
        }
    }
}

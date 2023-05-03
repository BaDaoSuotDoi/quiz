package com.badao.quiz.function.project.play.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.badao.quiz.function.question.edit.view.QuestionEditFragment;
import com.badao.quiz.function.question.play.view.QuestionPlayFragment;
import com.badao.quiz.model.Project;

public class ProjectPlayAdapter extends FragmentStateAdapter {
    private Project project;
    private int viewMode;
    public ProjectPlayAdapter(@NonNull FragmentActivity fragmentActivity, Project project, int viewMode) {
        super(fragmentActivity);
        this.project = project;
        this.viewMode = viewMode;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new QuestionPlayFragment(position, project.getQuestions().get(position), viewMode);
    }

    @Override
    public int getItemCount() {
        return project.getQuestions().size();
    }

    @Override
    public long getItemId(int position) {
        return project.getQuestions().get(position).getID();
    }
}

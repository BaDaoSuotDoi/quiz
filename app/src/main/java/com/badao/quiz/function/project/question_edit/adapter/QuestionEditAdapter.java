package com.badao.quiz.function.project.question_edit.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.badao.quiz.function.question.edit.view.QuestionEditFragment;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;

public class QuestionEditAdapter extends FragmentStateAdapter {
    private Project project;

    public QuestionEditAdapter(@NonNull FragmentActivity fragmentActivity, Project project) {
        super(fragmentActivity);
        this.project = project;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("Question Position", position+"");
        if(position >= project.getQuestions().size()){
            project.getQuestions().add(new Question());
        }
        return new QuestionEditFragment(position, project.getQuestions().get(position));
    }

    @Override
    public int getItemCount() {
        return project.getQuestions().size()+1;
    }
}

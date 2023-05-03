package com.badao.quiz.function.project.question_edit.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.question.edit.view.QuestionEditFragment;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.utils.Utils;

public class QuestionEditAdapter extends FragmentStateAdapter {
    private Project project;
    public QuestionEditAdapter(@NonNull FragmentActivity fragmentActivity, Project project) {
        super(fragmentActivity);
        this.project = project;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("Question Position", position+"//"+project.getQuestions().size());
        Question question = project.getQuestions().get(position);
//        if(position == project.getQuestions().size()- 1){
//            project.getQuestions().add(new Question(AppConstants.QUESTION_TEMP_ID));
//        }
        Log.e("Question here", project.getQuestions().get(position).toString());
        return new QuestionEditFragment(position, question);
    }

    @Override
    public int getItemCount() {
        return project.getQuestions().size();
    }

    @Override
    public long getItemId(int position) {
        return project.getQuestions().get(position).getIndex();
    }

    public Question removeFragment(int position) {
        Question question = project.getQuestions().remove(position);
        notifyItemRemoved(position);
        return  question;
    }


}

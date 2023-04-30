package com.badao.quiz.function.project.question_edit.view;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.project.question_edit.adapter.QuestionEditAdapter;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditContract;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditPresenter;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;

import butterknife.BindView;

@ViewInflate(presenter = ProjectQuestionEditPresenter.class, layout = R.layout.fragment_project_question_edit)
public class ProjectQuestionEditFragment extends BaseAnnotatedFragment<ProjectQuestionEditContract.View, ProjectQuestionEditContract.Presenter> implements ProjectQuestionEditContract.View{
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.imSave)
    ImageView imSave;
    @BindView(R.id.imAcross)
    ImageView imAcross;
    @BindView(R.id.vpQuestionEdit)
    ViewPager2 vpQuestionEdit;
    @BindView(R.id.tvPrevious)
    TextView tvPrevious;
    @BindView(R.id.tvQuestionCurrent)
    TextView tvQuestionCurrent;
    @BindView(R.id.tvNext)
    TextView tvNext;
    private Project project;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        project = getPresenter().getProject();
        if(project.getQuestions().size() == 0){
            project.getQuestions().add(new Question());
        }
        updateProjectName();
        QuestionEditAdapter adapter = new QuestionEditAdapter(getActivity(), project);
        vpQuestionEdit.setAdapter(adapter);
    }


    @Override
    public void updateProjectName() {
        tvProjectName.setText(project.getName());
    }
}

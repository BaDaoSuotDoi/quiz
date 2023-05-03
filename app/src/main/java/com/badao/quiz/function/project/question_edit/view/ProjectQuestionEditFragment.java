package com.badao.quiz.function.project.question_edit.view;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.db.RecordUserAnswerDB;
import com.badao.quiz.function.project.question_edit.adapter.QuestionEditAdapter;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditContract;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditPresenter;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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
    private QuestionEditAdapter adapter;
    private List<Question> questionRemoved = new ArrayList<>();
    public  void initData(){
        project = getPresenter().getProject();
        project.setQuestions(getPresenter().getQuestions(project.getID()));
        for(Question question: project.getQuestions()){
            Log.e("My question", question.toString());
            question.setIndex(getPresenter().getQuestionIndex());
        }

        //buffer question
        if(project.getQuestions().size() == 0){
            project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), false));
            project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), true));
        }else{
            project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), true));
        }

    }
    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        initData();

        Log.e("Sizq question init", project.getQuestions().size()+"");
        updateProjectName();
        updateListAnswer();

        getViewModel().getMlQuestionStatus().observe(this, payload -> {
            Log.e("Run here", "Create question 1");
            if(payload.getAction()  == AppConstants.QUESTION_TEMP_ADD){
                Log.e("Run here", "Create question ccc");
                int numberQuestionInvalid = 0;
                for(Question question: project.getQuestions()){
                    if(!Utils.checkValid(question).isEmpty()){
                       numberQuestionInvalid++;
                    }
                }
                if(numberQuestionInvalid > 2){
                    int n = project.getQuestions().size();
                    adapter.removeFragment(n-1);
                }
            }
        });
        imSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Question> questions = project.getQuestions();
                int n = questions.size();
                for(int i=0; i<n; i++){
                    Question question =questions.get(i);
                    if(!question.isTemp()){
                        String valid = Utils.checkValid(question);
                        if(!valid.isEmpty()){
                            Toast.makeText(getContext(), valid, Toast.LENGTH_SHORT).show();
                            vpQuestionEdit.setCurrentItem(i);
                            return;
                        }
                    }
                }

                List<String> questionIds = new ArrayList<>();
                if(questionRemoved.size() != 0){
                    for(Question question: questionRemoved){
                        questionIds.add(String.valueOf(question.getID()));
                    }
                }
                // update here remove question
                for(Question question:questions){
                    if(!question.isTemp()){
                        questionIds.add(String.valueOf(question.getID()));
                    }
                }
                boolean wasDone = RecordUserAnswerDB.getInstance(getContext()).findBy(questionIds).size() != 0;
                for(Question question: questionRemoved){
                    if(!question.isTemp()){
                        QuestionDB.getInstance(getContext()).update(question, project.getID(),wasDone,true);
                    }
                }

                for(Question question:questions){
                    if(!question.isTemp()){
                        QuestionDB.getInstance(getContext()).update(question, project.getID(),wasDone, false);
                    }
                }
                Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
            }
        });

        imAcross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemCurrent = vpQuestionEdit.getCurrentItem();
                Log.e("Delete question "+itemCurrent , project.getQuestions().get(itemCurrent).toString());
//                QuestionEditAdapter adapter = (QuestionEditAdapter)vpQuestionEdit.getAdapter();
//                adapter.notifyDataSetChanged();
                int n = project.getQuestions().size();
                if(itemCurrent == n - 2){
                    project.getQuestions().get(n-1).setTemp(false);
                    project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), true));
                }
                questionRemoved.add(adapter.removeFragment(itemCurrent));
            }
        });

        vpQuestionEdit.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Question questionCurrent = project.getQuestions().get(position);
                if(questionCurrent.isTemp()){
                    int n = project.getQuestions().size();
                    Log.e("PAGE selected", "POS:" +position+"//"+n);
                    boolean isValid = true;
                    for(int i = 0; i < n; i++){
                        Question question = project.getQuestions().get(i);
                        if(!Utils.checkValid(question).isEmpty() && !question.isTemp()){
                            isValid = false;
                            vpQuestionEdit.setCurrentItem(i);
                            break;
                        }
                    }
                    if(isValid){
                        Log.e("Add question---", "OK");
                        questionCurrent.setTemp(false);
                        project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), true));
                    }
                }
            }
        });
    }

    @Override
    public void updateProjectName() {
        tvProjectName.setText(project.getName());
    }

    @Override
    public void updateListAnswer() {
        adapter = new QuestionEditAdapter(getActivity(), project);
        vpQuestionEdit.setAdapter(adapter);
        vpQuestionEdit.setOffscreenPageLimit(1);
    }
}

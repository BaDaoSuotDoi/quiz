package com.badao.quiz.function.question.edit.view;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditContract;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditPresenter;
import com.badao.quiz.function.question.edit.adapter.AnswerAdapter;
import com.badao.quiz.function.question.edit.presenter.QuestionEditContract;
import com.badao.quiz.function.question.edit.presenter.QuestionEditPresenter;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;

import butterknife.BindView;

@ViewInflate(presenter = QuestionEditPresenter.class, layout = R.layout.fragment_question_edit)
public class QuestionEditFragment extends BaseAnnotatedFragment<QuestionEditContract.View, QuestionEditContract.Presenter>
        implements QuestionEditContract.View{
    @BindView(R.id.edContent)
    EditText edContent;
    @BindView(R.id.imMode)
    ImageView imMode;
    @BindView(R.id.rvListAnswer)
    RecyclerView rvListAnswer;
    @BindView(R.id.tvAdd)
    TextView tvAdd;
    @BindView(R.id.edComment)
    EditText edComment;
    @BindView(R.id.imComment)
    ImageView imComment;
    private  int position;
    private  Question question;

    public QuestionEditFragment(int position, Question question){
        this.position = position;
        this.question = question;
    }

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        edContent.setText(question.getContent());
        edComment.setText(question.getComment());
        if(question.getAnswers().size() == 0){
            question.getAnswers().add(new QuestionAnswer());
        }
        AnswerAdapter adapter = new AnswerAdapter(getActivity(), question.getAnswers());
        rvListAnswer.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                Log.e("Run delete here", "Ok");
                question.getAnswers().remove(position);
                Log.e("QA", question.getAnswers().size()+"");
                adapter.setData(question.getAnswers());
            }
        });
        rvListAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addData(new QuestionAnswer());
            }
        });
    }

}

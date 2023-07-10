package com.badao.quiz.function.question.edit.view;

import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.dialog.ProjectDialog;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditContract;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditPresenter;
import com.badao.quiz.function.question.edit.adapter.AnswerAdapter;
import com.badao.quiz.function.question.edit.dialog.CommentDialog;
import com.badao.quiz.function.question.edit.presenter.QuestionEditContract;
import com.badao.quiz.function.question.edit.presenter.QuestionEditPresenter;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.llComment)
    LinearLayout llComment;
    @BindView(R.id.imComment)
    ImageView imComment;
    private  int position;
    private  Question question;
    private AnswerAdapter adapter;

    private PopupMenu menuModeAnswer;
    private QuestionEditListener questionEditListener;
    public QuestionEditFragment(int position, Question question, QuestionEditListener questionEditListener){
        this.position = position;
        this.question = question;
        this.questionEditListener = questionEditListener;
    }

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        edContent.setText(question.getContent());
        tvComment.setText(question.getComment());
        if(question.getAnswers().size() == 0){
            question.getAnswers().add(new QuestionAnswer(question.getId()));
        }
        updateListAnswer();
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String content = charSequence.toString();
                questionEditListener.onContentQuestionChange(question.getPosition(), content);
                question.setContent(content);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog commentDialog = new CommentDialog(question.getComment(),new CommentDialog.ICommentDialog() {
                    @Override
                    public void onCommentChange(String content) {
                        tvComment.setText(content);
                        question.setComment(content);
                    }
                });
                commentDialog.show(getParentFragmentManager(), CommentDialog.class.getName());
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question.getType() == AppConstants.QUESTION_SELECTION_TYPE){
                    adapter.addData(new QuestionAnswer(question.getId(), AppConstants.QUESTION_SELECTION_TYPE));
                }
            }
        });

        menuModeAnswer = new PopupMenu(getContext(), imMode);
        menuModeAnswer.inflate(R.menu.menu_mode_answer);
        menuModeAnswer.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ViewGroup.LayoutParams layoutParams = tvAdd.getLayoutParams();
                switch (menuItem.getItemId()){
                    case R.id.selection_mode:
                        Log.e("Mode answer", "Selection");
                        for(QuestionAnswer questionAnswer: question.getAnswers()){
                            questionAnswer.setContent(AppConstants.TOKEN_FALSE_SELECT_ANSWER);
                            questionAnswer.setType(AppConstants.QUESTION_SELECTION_TYPE);
                        }
                        question.setType(AppConstants.QUESTION_SELECTION_TYPE);
                        adapter = new AnswerAdapter(getActivity(), question.getAnswers(), question.getType(),false);
                        listenerAdapter();
                        rvListAnswer.setAdapter(adapter);

                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        tvAdd.setLayoutParams(layoutParams);
                        return true;
                    case R.id.type_mode:
                        Log.e("Mode answer", "Selection");
                        if( question.getAnswers().size() >= 1){
                            QuestionAnswer questionAnswer = question.getAnswers().get(0);
                            questionAnswer.setContent("");
                            questionAnswer.setType(AppConstants.QUESTION_NORMAL_TYPE);
                            question.setAnswers(new ArrayList<>(Arrays.asList(questionAnswer)));
                        }else{
                            question.setAnswers(new ArrayList<>(Arrays.asList(new QuestionAnswer(question.getId()))));
                        }
                        question.setType(AppConstants.QUESTION_NORMAL_TYPE);
                        adapter = new AnswerAdapter(getActivity(), question.getAnswers(), question.getType(),false);
                        listenerAdapter();
                        rvListAnswer.setAdapter(adapter);

                        layoutParams.width = 0;
                        layoutParams.height = 0;
                        tvAdd.setLayoutParams(layoutParams);
                        return true;
                }
                return false;
            }
        });

        if(question.getType() == AppConstants.QUESTION_SELECTION_TYPE){
            ViewGroup.LayoutParams layoutParams = tvAdd.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvAdd.setLayoutParams(layoutParams);
        }

        imMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuModeAnswer.show();
            }
        });
    }

    @Override
    public void updateListAnswer() {
        if(question.getType() == AppConstants.QUESTION_NORMAL_TYPE){
            if( question.getAnswers().size() > 1){
                question.setAnswers(new ArrayList<>(Arrays.asList(question.getAnswers().get(0))));
            }
        }
        adapter = new AnswerAdapter(getActivity(), question.getAnswers(), question.getType(), false);
        listenerAdapter();
        rvListAnswer.setAdapter(adapter);
        rvListAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void checkValidInput() {
        String content = edContent.getText().toString();
        if(content.isEmpty()){
            edContent.setHint("Empty!");
            edContent.setHintTextColor(Color.parseColor("#D30C12"));
        }
        adapter = new AnswerAdapter(getActivity(), question.getAnswers(), question.getType(), true);
        listenerAdapter();
        rvListAnswer.setAdapter(adapter);
    }

    public interface QuestionEditListener{
        void onContentQuestionChange(int index, String content);
    }

    void listenerAdapter(){
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                Log.e("Run delete here", "Ok");
                if(question.getType() == AppConstants.QUESTION_SELECTION_TYPE){
                    if(question.getAnswers().size() > 1){
                        question.getAnswers().remove(position);
                        adapter.setData(question.getAnswers());
                    }else{
                        Toast.makeText(getContext(), "Must at least one anwer!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

package com.badao.quiz.function.question.edit.vocabulary.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.question.edit.dialog.CommentDialog;
import com.badao.quiz.function.question.edit.presenter.QuestionEditPresenter;
import com.badao.quiz.function.question.edit.view.QuestionEditFragment;
import com.badao.quiz.function.question.edit.vocabulary.presenter.QuestionVocabularyEditContract;
import com.badao.quiz.function.question.edit.vocabulary.presenter.QuestionVocabularyEditPresenter;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;

import butterknife.BindView;

@ViewInflate(presenter = QuestionVocabularyEditPresenter.class, layout = R.layout.fragment_question_vocabulary_edit)
public class QuestionVocabularyEditFragment   extends BaseAnnotatedFragment<QuestionVocabularyEditContract.View, QuestionVocabularyEditContract.Presenter>
        implements QuestionVocabularyEditContract.View{
    @BindView(R.id.edContent)
    EditText edContent;
    @BindView(R.id.llComment)
    LinearLayout llComment;
    @BindView(R.id.tvComment)
    TextView tvComment;
    private int position;
    private Question question;
    private QuestionEditFragment.QuestionEditListener questionEditListener;

    public QuestionVocabularyEditFragment(int position, Question question, QuestionEditFragment.QuestionEditListener questionEditListener) {
        this.position = position;
        this.question = question;
        this.questionEditListener = questionEditListener;
        if(question.getAnswers().size() == 0){
            question.getAnswers().add(new QuestionAnswer(question.getId()));
        }
    }

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        edContent.setText(question.getContent());
        tvComment.setText(question.getComment());
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String content = charSequence.toString();
                questionEditListener.onContentQuestionChange(question.getPosition(), content);
                question.getAnswers().get(0).setContent(content);
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
    }
}

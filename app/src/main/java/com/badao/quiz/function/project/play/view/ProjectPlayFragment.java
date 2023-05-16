package com.badao.quiz.function.project.play.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.QuestionAnswerDB;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.function.project.play.adapter.ProjectPlayAdapter;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;
import com.badao.quiz.function.project.play.presenter.ProjectPlayPresenter;
import com.badao.quiz.function.question.play.view.QuestionPlayFragment;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.RecordUserAnswer;
import com.badao.quiz.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = ProjectPlayPresenter.class, layout = R.layout.fragment_project_play)
public class ProjectPlayFragment  extends BaseAnnotatedFragment<ProjectPlayContract.View, ProjectPlayContract.Presenter> implements ProjectPlayContract.View{
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.imPrevious)
    ImageView imPrevious;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.vpQuestionPlay)
    ViewPager2 vpQuestionPlay;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvQuestionIndex)
    TextView tvQuestionIndex;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.imMenu)
    ImageView imMenu;
    TextView tvPage;
    TextView tvQuestionAnswered;
    TextView tvRemainingQuestion;
    TextView tvQuestionSaw;
    TextView tvTimeElapsed;
    Button btSubmit;
    private Project project;
    private List<Question> questions;
    private ProjectPlayAdapter adapter;

    private HistorySubmit historySubmit;

    private int viewMode;
    private MenuItem menuItemSelected;
    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        project = getPresenter().getProject();
        viewMode = getPresenter().getViewMode();

        initViewMode();
        updateQuestionPlay();
        updateMenuQuestion();
        initMenuHeader();

        vpQuestionPlay.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Question questionCurrent = project.getQuestions().get(position);
                int n = project.getQuestions().size();
                selectMenuItem(position);
                questionCurrent.setViewed(true);
                int questionSawNumber = 0;
                int questionAnsweredNumber = 0;
                for(Question question: project.getQuestions()){
                    if(question.isViewed()){
                        questionSawNumber++;
                    }
                    if(!question.getUserAnswers().getAnswer().isEmpty()){
                        questionAnsweredNumber++;
                    }
                }
                tvQuestionIndex.setText(String.format(AppConstants.FORMAT_RATIO, position+1, n));
                tvPage.setText(String.format(AppConstants.FORMAT_RATIO, position+1, n));
                tvQuestionAnswered.setText(String.format(AppConstants.FORMAT_RATIO, questionAnsweredNumber, n));
                tvRemainingQuestion.setText(String.format(AppConstants.FORMAT_RATIO, n - questionSawNumber, n));
                tvQuestionSaw.setText(String.format(AppConstants.FORMAT_RATIO, questionSawNumber, n));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(menuItemSelected != null){
                    setBackgroundColorForMenuItem(menuItemSelected, R.drawable.radius_border_hidden);
                }
                menuItemSelected = item;
                int itemId = item.getItemId();
                int position = -1;
                int n = project.getQuestions().size();
                for(int i = 0; i< n ; i++){
                    Question question = project.getQuestions().get(i);
                    if(question.getPosition() == itemId){
                        position = i;
                    }
                }
                vpQuestionPlay.setCurrentItem(position);
                drawerLayout.closeDrawer(GravityCompat.END);
                setBackgroundColorForMenuItem(item, R.drawable.radius_border_solid);
                return false;
            }
        });
        imMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index =  vpQuestionPlay.getCurrentItem();
                if(index > 0){
                    vpQuestionPlay.setCurrentItem(index - 1);
                }
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index =  vpQuestionPlay.getCurrentItem();
                int n = project.getQuestions().size();
                if(index < n - 1){
                    vpQuestionPlay.setCurrentItem(index + 1);
                }
            }
        });

        imPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        selectMenuItem(0);
        observe();

        if(viewMode == AppConstants.PROJECT_SHOW_ANSWER){
            btSubmit.setVisibility(View.INVISIBLE);
        }
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewMode == AppConstants.PROJECT_PLAY){
//                    getPresenter().submit(project);
//                    Log.e("Submit", "OK");
//                    adapter.setViewMode(AppConstants.PROJECT_SHOW_ANSWER);
                    historySubmit = getPresenter().submit(project);
                    setViewMode(AppConstants.PROJECT_SHOW_ANSWER);
                    changeViewSubmit(historySubmit);

                }
            }
        });
    }

    @Override
    public void updateTime(String time) {
        tvTime.setText(time);
        if(tvTimeElapsed != null){
            tvTimeElapsed.setText(time);
        }
    }

    @Override
    public void updateQuestionPlay() {
        adapter = new ProjectPlayAdapter(getActivity(),project, viewMode);
        vpQuestionPlay.setAdapter(adapter);
        vpQuestionPlay.setOffscreenPageLimit(1);
    }

    @Override
    public void updateMenuQuestion() {
        if(navigationView != null){
            int n = project.getQuestions().size();
            Menu menu = navigationView.getMenu();
            for(int i= 0; i< n; i++){
                Question question = project.getQuestions().get(i);
                if(question.isValid()) {
                    MenuItem menuItem= menu.add(Menu.NONE, question.getPosition(), Menu.NONE, "");
                    View actionView = LayoutInflater.from(getContext()).inflate(R.layout.menu_question_play, null, false);
                    TextView tvContent = actionView.findViewById(R.id.tvMenuContent);
                    tvContent.setText(question.getContent());
                    menuItem.setActionView(actionView);
                    if(viewMode == AppConstants.PROJECT_SHOW_ANSWER){
                        setModeMenuShowAnswer();
                    }
                }
            }
        }
    }

    @Override
    public void initMenuHeader() {
        View view = navigationView.getHeaderView(0);
        tvPage = view.findViewById(R.id.tvPage);
        tvQuestionAnswered = view.findViewById(R.id.tvQuestionAnswered);
        tvRemainingQuestion = view.findViewById(R.id.tvRemainingQuestion);
        tvQuestionSaw = view.findViewById(R.id.tvQuestionSaw);
        tvTimeElapsed = view.findViewById(R.id.tvTimeElapsed);
        btSubmit = view.findViewById(R.id.btSubmit);

        if(viewMode == AppConstants.PROJECT_SHOW_ANSWER){
            tvTimeElapsed.setText(Utils.displayTime(historySubmit.getTimeElapsed()));
        }
    }

    @Override
    public void observe() {
        getViewModel().getMlUserChangeAnswer().observe(this, payload -> {
            if(payload.getAction() == AppConstants.USER_CHANGE_ANSWER){
                QuestionPlayFragment.QuestionUserAnswer questionUserAnswer = (QuestionPlayFragment.QuestionUserAnswer) payload.getValue();
                int position = questionUserAnswer.getQuestionPosition();
                int n = questions.size();
                int questionAnsweredNumber = 0;

                for(int i = 0; i<n;i++){
                    Question question = questions.get(i);
                    if(question.getPosition() == position){
                        MenuItem menuItem = navigationView.getMenu().getItem(i);
                        View view = menuItem.getActionView();
                        ImageView imageView = view.findViewById(R.id.imQuestionAnswer);
                        if(questionUserAnswer.isAnswer()){
                            imageView.setImageResource(R.drawable.ic_checked);
                        }else{
                            imageView.setImageResource(R.drawable.ic_uncheck);
                        }
                    }
                    if(!question.getUserAnswers().getAnswer().isEmpty()){
                        questionAnsweredNumber++;
                    }
                }

                if(questionUserAnswer.isAnswer()){
                    tvQuestionAnswered.setText(String.format(AppConstants.FORMAT_RATIO, questionAnsweredNumber, n));

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().stopTime();
    }

    public void initViewMode(){
        if(viewMode == AppConstants.PROJECT_PLAY){
            List<Question> allQuestion  = getPresenter().getQuestions(project.getId());

            if(!project.isRandom()){
                questions = allQuestion;
                project.setQuestions(questions);
            }else{
                List<Question> questionRandom = new ArrayList<>();
                List<Integer> randomIntArray = Utils.generateRandomIntList(allQuestion.size(), project.getQuestionPerSession());
                for(int index: randomIntArray){
                    questionRandom.add(allQuestion.get(index));
                }
                questions = questionRandom;
                project.setQuestions(questions);
            }
            for(Question question: questions){
                Log.e("Play", question.toString());
            }
            updateTime("00:00");

            int duration = project.getDuration();
            if(duration == -1){
                getPresenter().setTimeStart(0);
                getPresenter().setTimeType(- AppConstants.TIMER_COUNTDOWN);
            }else if(duration < -1){
                getPresenter().setTimeStart(-duration*project.getQuestions().size());
                getPresenter().setTimeType( AppConstants.TIMER_COUNTDOWN);
            }else{
                getPresenter().setTimeStart(duration);
                getPresenter().setTimeType( AppConstants.TIMER_COUNTDOWN);
            }

            for(Question question: project.getQuestions()){
                question.setAnswers(QuestionAnswerDB.getInstance(getContext()).findBy(question.getId()));
            }

            getPresenter().start();
        }else if(viewMode == AppConstants.PROJECT_SHOW_ANSWER){
            historySubmit = getPresenter().getHistorySubmit();
            List<RecordUserAnswer> recordUserAnswers =getPresenter().getUserAnswers();
            questions = new ArrayList<>();
            for(RecordUserAnswer recordUserAnswer: recordUserAnswers){
                Question question = QuestionDB.getInstance(getContext()).findByPk(recordUserAnswer.getQuestionId());
                question.setAnswers(QuestionAnswerDB.getInstance(getContext()).findBy(question.getId()));
                question.setUserAnswers(recordUserAnswer);
                questions.add(question);
                Log.e("Question result", question.toString());
            }

            project.setQuestions(questions);
            updateTime(Utils.displayTime(historySubmit.getTimeElapsed()));
        }
    }


    public void setBackgroundColorForMenuItem(MenuItem menuItem, @DrawableRes int draw){
        View view = menuItem.getActionView();
        view.setBackground(ContextCompat.getDrawable(getContext(), draw));
    }

    public void selectMenuItem(int position){
        if(menuItemSelected != null){
            setBackgroundColorForMenuItem(menuItemSelected, R.drawable.radius_border_hidden);
        }
        MenuItem menuItem = navigationView.getMenu().getItem(position);
        menuItemSelected = menuItem;
        setBackgroundColorForMenuItem(menuItem, R.drawable.radius_border_solid);
    }

    @Override
    public void setViewMode(int viewMode) {
        this.viewMode = viewMode;
        adapter.setViewMode(viewMode);
    }

    @Override
    public void setModeMenuShowAnswer() {
        Menu menu = navigationView.getMenu();
        int n = menu.size();

        for(int i = 0; i< n; i++){
            MenuItem menuItem = menu.getItem(i);
            ImageView imageView = menuItem.getActionView().findViewById(R.id.imQuestionAnswer);
            Question question = project.getQuestions().get(i);
            if(question.getUserAnswers().getStatus() == AppConstants.QUESTION_ANSWER_CORRECT){
                imageView.setImageResource(R.drawable.ic_correct);
            }else{
                imageView.setImageResource(R.drawable.ic_wrong);
            }

        }
    }

    @Override
    public void changeViewSubmit(HistorySubmit historySubmit) {
        tvTimeElapsed.setText(Utils.displayTime(historySubmit.getTimeElapsed()));
        Log.e("historySubmit.getTimeElapsed(", Utils.displayTime(historySubmit.getTimeElapsed()));
        setModeMenuShowAnswer();
        btSubmit.setVisibility(View.INVISIBLE);
    }
}

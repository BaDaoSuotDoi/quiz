package com.badao.quiz.function.project.question_edit.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.BaseFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.db.RecordUserAnswerDB;
import com.badao.quiz.function.project.question_edit.adapter.QuestionEditAdapter;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditContract;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditPresenter;
import com.badao.quiz.function.question.edit.view.QuestionEditFragment;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@ViewInflate(presenter = ProjectQuestionEditPresenter.class, layout = R.layout.fragment_project_question_edit)
public class ProjectQuestionEditFragment extends BaseAnnotatedFragment<ProjectQuestionEditContract.View, ProjectQuestionEditContract.Presenter> implements ProjectQuestionEditContract.View{
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.imPrevious)
    ImageView imPrevious;
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.imSave)
    ImageView imSave;
    @BindView(R.id.imAcross)
    ImageView imAcross;
    @BindView(R.id.imInsert)
    ImageView imInsert;
    @BindView(R.id.imMenu)
    ImageView imMenu;
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
    private MenuItem menuItemSelected;

    public  void initData(){

        project = getPresenter().getProject();
        project.setQuestions(getPresenter().getQuestions(project.getID()));
        int n = project.getQuestions().size();
        if(n>0){
            getPresenter().setQuestionIndex(project.getQuestions().get(n-1).getPosition()+1);
        }
        for(Question question: project.getQuestions()){
            question.setPosition(getPresenter().getQuestionIndex());
        }

        //buffer question
        project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), false));
        project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), true));

        Question question = n > 0? project.getQuestions().get(n-1):project.getQuestions().get(0) ;
        question.setViewed(true);

    }
    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        initData();

        updateProjectName();
        updateListAnswer();
        updateMenuQuestion();

        for(Question question: project.getQuestions()){
            Log.e("Question here",question.toString());
        }

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
                    Question question = questions.get(i);
                    if(question.isValid()){
                        String valid = Utils.checkValid(question);
                        if(!valid.isEmpty()){
                            Toast.makeText(getContext(), valid, Toast.LENGTH_SHORT).show();
                            vpQuestionEdit.setCurrentItem(i);
                            // warning
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            for(Fragment fragment: fragmentManager.getFragments()){
                                if(fragment instanceof  BaseFragment){
                                    BaseFragment baseFragment = (BaseFragment) fragment;
                                    if(baseFragment.getTagCustom().equals("qe"+question.getPosition())){
                                        QuestionEditFragment questionEditFragment = (QuestionEditFragment) baseFragment;
                                        questionEditFragment.checkValidInput();
                                    }
                                }
                            }
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
                    if(question.isValid()){
                        questionIds.add(String.valueOf(question.getID()));
                    }
                }
                boolean wasDone = RecordUserAnswerDB.getInstance(getContext()).findBy(questionIds).size() != 0;
                for(Question question: questionRemoved){
                    if(question.isValid()){
                        QuestionDB.getInstance(getContext()).update(question, project.getID(),wasDone,true);
                    }
                }

                for(Question question:questions){
                    if(question.isValid()){
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
                Question question = project.getQuestions().get(itemCurrent);
                int n = project.getQuestions().size();
                if(n == 2 && itemCurrent == 0){
                    Question questionTemp = new Question(getPresenter().getQuestionIndex(), false);
                    project.getQuestions().set(0, questionTemp );
                    removeMenuQuestion(question.getPosition());
                    addMenuQuestion(questionTemp.getPosition(), itemCurrent, "1. --Waiting edition--");
                }
                if(!question.isTemp() && n > 2){
                    Log.e("Delete question "+itemCurrent + "//"+n , project.getQuestions().get(itemCurrent).toString());
//                QuestionEditAdapter adapter = (QuestionEditAdapter)vpQuestionEdit.getAdapter();
//                adapter.notifyDataSetChanged();
                    // delete end question
                    if(itemCurrent == n - 3){
                        Log.e("Run here", "Question end");
//                        project.getQuestions().get(n-1).setTemp(false);
//                        Question questionAdd = new Question(getPresenter().getQuestionIndex(), true);
//                        project.getQuestions().add(questionAdd);
//                        addMenuQuestion(questionAdd.getIndex(),n, (n+1) + "--Waiting edition--");
                        vpQuestionEdit.setCurrentItem(itemCurrent - 1);
                        adapter.removeFragment(itemCurrent);
                        selectMenuItem(itemCurrent);
                    }
                    // delete question waiting edit
                    else if(itemCurrent == n -2){
                        question.setViewed(false);
                        vpQuestionEdit.setCurrentItem(itemCurrent - 1);
                        selectMenuItem(itemCurrent - 1);
                    }else{
                        adapter.removeFragment(itemCurrent);
                        selectMenuItem(itemCurrent);
                    }

                    questionRemoved.add(question);
                    removeMenuQuestion(question.getPosition());

                }

            }
        });

        imInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemCurrent = vpQuestionEdit.getCurrentItem();

            }
        });
        vpQuestionEdit.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Question questionCurrent = project.getQuestions().get(position);
                int n = project.getQuestions().size();
                if(questionCurrent.isTemp()){
                    Log.e("PAGE selected", "POS:" +position+"//"+n);
                    boolean isValid = true;
                    for(int i = 0; i < n; i++){
                        Question question = project.getQuestions().get(i);
                        if(!Utils.checkValid(question).isEmpty() && !question.isTemp()){
                            isValid = false;
                            vpQuestionEdit.setCurrentItem(i);
                            selectMenuItem(i);
                            break;
                        }
                    }
                    if(isValid){
                        Log.e("Add question---", "OK");
                        questionCurrent.setTemp(false);
                        addMenuQuestion(questionCurrent.getPosition(), n, n + ".--Waiting edition--");
                        selectMenuItem(position);
                        adapter.addFragment(n, new Question(getPresenter().getQuestionIndex(), true));
//                        project.getQuestions().add(new Question(getPresenter().getQuestionIndex(), true));
                    }
                }else{
                    // create menu for question temp
                    if(questionCurrent.getID() == 0 && !questionCurrent.isViewed()){
                        addMenuQuestion(questionCurrent.getPosition(), position + 1, (position + 1) +  ". --Waiting edition--");
                    }
                    selectMenuItem(position);
                }
                questionCurrent.setViewed(true);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.e("Item clicked", item.getItemId()+"");
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
                vpQuestionEdit.setCurrentItem(position);
                drawerLayout.closeDrawer(GravityCompat.END);
                setBackgroundColorForMenuItem(item, R.drawable.radius_border_solid);
                return true;
            }
        });
        imPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        imMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        int n = project.getQuestions().size();
        if( n > 2){
            Question question = project.getQuestions().get(n-3);
            // set init navigation
            selectMenuItem(n-3);
            vpQuestionEdit.setCurrentItem(n-3);
        }else{
            Question question = project.getQuestions().get(0);
            selectMenuItem(0);
        }

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
    public void updateProjectName() {
        tvProjectName.setText(project.getName());
    }

    @Override
    public void updateListAnswer() {
        adapter = new QuestionEditAdapter(getActivity(), project, new QuestionEditFragment.QuestionEditListener() {
            @Override
            public void onContentQuestionChange(int index, String content) {
                TextView textView = navigationView.getMenu().findItem(index).getActionView().findViewById(R.id.tvMenuContent);
                String position = textView.getText().toString().split("\\.")[0];
                textView.setText(position+". "+content);
            }
        });
        vpQuestionEdit.setAdapter(adapter);
        vpQuestionEdit.setOffscreenPageLimit(1);
    }

    @Override
    public void updateMenuQuestion() {
        if(navigationView != null){
            int n = project.getQuestions().size();
            for(int i= 0; i< n; i++){
                Question question = project.getQuestions().get(i);
                if(question.isValid()) {
                    addMenuQuestion(question.getPosition(), i, (i + 1) + ". " + question.getContent());
                }
            }
        }
    }

    @Override
    public void addMenuQuestion(int id, int position, String content ) {
        Menu menu = navigationView.getMenu();
        MenuItem menuItem= menu.add(Menu.NONE, id, Menu.NONE, "");
        View actionView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item_action_view, null, false);
        TextView tvContent = actionView.findViewById(R.id.tvMenuContent);
        tvContent.setText(content);
        menuItem.setActionView(actionView);
    }

    @Override
    public void checkValidInput() {

    }

    @Override
    public void removeMenuQuestion(int id) {
        Menu menu = navigationView.getMenu();
        menu.removeItem(id);
        for(int i=0; i< menu.size(); i++){
            MenuItem menuItem = menu.getItem(i);
            TextView  textView = menuItem.getActionView().findViewById(R.id.tvMenuContent);
            Log.e("Remove Item",textView.getText().toString().split("\\.").length+"");
            String[] items = textView.getText().toString().split("\\.");
            items[0] = String.valueOf(i+1);
            textView.setText(String.join(".",items ));
        }
    }

    private void setTextColorForMenuItem(MenuItem menuItem, @ColorRes int color) {
//        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
//        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), color)), 0, spanString.length(), 0);
//        menuItem.setTitle(spanString);
        View view = menuItem.getActionView();
        view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.radius_border_solid));
    }

    public void setBackgroundColorForMenuItem(MenuItem menuItem, @DrawableRes int draw){
        View view = menuItem.getActionView();
        view.setBackground(ContextCompat.getDrawable(getContext(), draw));
    }
}

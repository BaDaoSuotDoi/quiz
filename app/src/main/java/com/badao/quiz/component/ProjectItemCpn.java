package com.badao.quiz.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.model.Project;
import com.badao.quiz.utils.BundleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectItemCpn extends LinearLayout implements  View.OnClickListener {
    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvCreatedAt)
    TextView tvCreatedAt;

    @BindView(R.id.imAction)
    ImageView imAction;

    private  IAction iAction;
    private Project project;

    public ProjectItemCpn(Context context, Project project, IAction iAction) {
        super(context);
        this.project = project;
        this.iAction = iAction;
        initView();
        tvCreatedAt.setOnClickListener(this);
        tvName.setOnClickListener(this);
    }

    public ProjectItemCpn(Context context) {
        super(context);
        initView();
    }

    public ProjectItemCpn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProjectItemCpn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_project, this, true);
        ButterKnife.bind(this);
        Log.e("Project", "init view");
        String name = project.getName();
        if(name.length() > 8){
            tvName.setText(name.substring(0, 5)+"...");
        }else{
            tvName.setText(name);
        }

        tvCreatedAt.setText(project.getCreatedAt());
        imAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Project menu", "clicked");
                PopupMenu popupMenu = new PopupMenu(getContext(), imAction);
                popupMenu.inflate(R.menu.menu_project);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.action_edit:
                                Log.e("Project Action", "edit");
                                iAction.navigateQuestionEdit(project);
                                return true;
                            case R.id.action_delete:
                                Log.e("Project Action", "delete");
                                iAction.onDelete(project);
                                return true;
                            case R.id.action_play:
                                int questionNumber = ProjectDB.getInstance(getContext()).getNumberQuestion(project.getId());
                                if(questionNumber == 0){
                                    Toast.makeText(getContext(), "Cannot play.Empty project!", Toast.LENGTH_SHORT).show();
                                }else{
                                    iAction.navigateProjectPlay(project);
                                }
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        iAction.navigateEdit(project);
    }

     public interface IAction{
        void navigateEdit(Project project);
        void navigateQuestionEdit(Project project);
        void navigateProjectPlay(Project project);
        void onDelete(Project project);
    }
}

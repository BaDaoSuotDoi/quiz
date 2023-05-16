package com.badao.quiz.function.home.view;

import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.function.home.dialog.DeleteProjectDialog;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.component.ProjectItemCpn;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.dialog.ProjectDialog;
import com.badao.quiz.function.home.presenter.HomeContract;
import com.badao.quiz.function.home.presenter.HomePresenter;
import com.badao.quiz.model.Project;
import com.badao.quiz.utils.BundleBuilder;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@ViewInflate(presenter = HomePresenter.class, layout = R.layout.fragment_home, hasToolbar = false)
public class HomeFragment extends BaseAnnotatedFragment<HomeContract.View, HomeContract.Presenter> implements HomeContract.View{
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.flProject)
    FlexboxLayout flProject;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    ProjectDialog projectDialog;
    private  DatabaseReference mDatabase;
    private TextView tvSync;
    private ImageView imSync;

    private AlertDialog dialog;
    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        getPresenter().initProjects();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.e("Open", "OK");
                if(ProjectDB.getInstance(getContext()).checkIsSync()){
                    tvSync.setTextColor(Color.parseColor("#D30C12"));
                }else{
                    imSync.setImageResource(R.drawable.correct);
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mProject:
                        drawerLayout.closeDrawer(navigationView);
                        return  true;
                    case R.id.mHistory:
                        drawerLayout.closeDrawer(navigationView);
                        navigate(R.id.historyFragment, AnimationType.FROM_RIGHT_TO_LEFT);
                        return  true;
                    case R.id.mStatistic:
                        drawerLayout.closeDrawer(navigationView);
                        navigate(R.id.statisticsFragment, AnimationType.FROM_RIGHT_TO_LEFT);
                        return  true;
                }
                return false;
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        mDatabase = FirebaseDatabase.getInstance().getReference(email.substring(0, email.length() - 10));

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.e("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        initProfileHeaderMenu();
        getPresenter().firebaseListener();
        setupSearch();
        observe();
    }


    @OnClick({R.id.btCreateProject})
    public  void onClick(View view){
//        getPresenter().sendMessage();
        switch (view.getId()){
//            case R.id.btLogout:
//                FirebaseAuth.getInstance().signOut();
//                navigate(R.id.loginFragment, AnimationType.FROM_BOTTOM_LEFT_CORNER_TO_WHOLE_SCREEN);
//                break;
            case  R.id.btCreateProject:
                if(projectDialog == null){
                    projectDialog = new ProjectDialog();
                }
                projectDialog.show(getParentFragmentManager(), ProjectDialog.class.getName());
        }

    }

    @Override
    public void observe() {
        getViewModel().getMldProjectStatus().observe(this, payload -> {
            if(payload.getAction()  == AppConstants.PROJECT_ADD){
                Project project =(Project) payload.getValue();
                addProject(project);
            }
        });
    }

    @Override
    public void addProject(Project project) {
        flProject.addView(new ProjectItemCpn(getContext(), project, new ProjectItemCpn.IAction() {
            @Override
            public void navigateEdit(Project project) {
                navigate(R.id.projectDetailFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getId())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }

            @Override
            public void navigateQuestionEdit(Project project) {
                navigate(R.id.projectQuestionEditFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getId())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }

            @Override
            public void navigateProjectPlay(Project project) {
                navigate(R.id.projectPlayFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getId()),
                        Pair.create(AppConstants.VIEW_MODE, AppConstants.PROJECT_PLAY)
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }

            @Override
            public void onDelete(Project project) {
                DeleteProjectDialog dialog = new DeleteProjectDialog(project, new DeleteProjectDialog.IListener() {
                    @Override
                    public void onDelete() {
                        ProjectDB.getInstance(getContext()).destroy(project);
                        getPresenter().initProjects();
                    }
                });
                dialog.show(getParentFragmentManager(), DeleteProjectDialog.class.getName());
            }
        }));
    }

    @Override
    public void setupSearch() {
        SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submit
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Project> projects = ProjectDB.getInstance(getContext()).findByName(newText);
                refreshView();
                for(Project project: projects){
                    addProject(project);
                }
                return true;
            }
        });
    }

    @Override
    public void initProfileHeaderMenu() {
        View view = navigationView.getHeaderView(0);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView textView = view.findViewById(R.id.tvEmail);
        textView.setText(user.getEmail());
        tvSync = view.findViewById(R.id.tvSync);
        imSync = view.findViewById(R.id.imSync);
        Button btLogout = view.findViewById(R.id.btLogout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                navigate(R.id.loginFragment, AnimationType.FROM_BOTTOM_LEFT_CORNER_TO_WHOLE_SCREEN);
            }
        });

        tvSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProjectDB.getInstance(getContext()).checkIsSync()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alter_sync, null);

                    builder.setView(dialogView);
                    builder.setCancelable(false);
                    dialog = builder.create();
                    dialog.show();
                    getPresenter().sync();

                }

            }
        });
    }

    @Override
    public void refreshView() {
        flProject.removeAllViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.mProject);
    }

    @Override
    public void syncSuccess() {
        tvSync.setTextColor(Color.parseColor("#23B81E"));
        dialog.dismiss();
    }

    @Override
    public DatabaseReference getMDatabase() {
        return mDatabase;
    }

}

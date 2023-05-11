package com.badao.quiz.function.home.view;

import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.dialog.DeleteProjectDialog;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.component.ProjectItemCpn;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.dialog.ProjectDialog;
import com.badao.quiz.function.home.presenter.HomeContract;
import com.badao.quiz.function.home.presenter.HomePresenter;
import com.badao.quiz.function.login.presenter.LoginContract;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.model.Project;
import com.badao.quiz.utils.BundleBuilder;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        getPresenter().initProjects();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
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
        initProfileHeaderMenu();
        setupSearch();
        observe();
    }

    @OnClick({R.id.btLogout, R.id.btCreateProject})
    public  void onClick(View view){
        switch (view.getId()){
            case R.id.btLogout:
                FirebaseAuth.getInstance().signOut();
                navigate(R.id.loginFragment, AnimationType.FROM_BOTTOM_LEFT_CORNER_TO_WHOLE_SCREEN);
                break;
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
                        Pair.create(AppConstants.PROJECT_ID, project.getID())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }

            @Override
            public void navigateQuestionEdit(Project project) {
                navigate(R.id.projectQuestionEditFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getID())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }

            @Override
            public void navigateProjectPlay(Project project) {
                navigate(R.id.projectPlayFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getID()),
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


}

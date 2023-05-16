package com.badao.quiz.function.home.presenter;
import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.Project;
import com.google.firebase.database.DatabaseReference;

public class HomeContract {

    public interface View extends BaseContract.View {
        void observe();
        void addProject(Project project);
        void refreshView();
        void initProfileHeaderMenu();
        void setupSearch();
        void syncSuccess();
        DatabaseReference getMDatabase();
    }

    public interface Presenter extends BaseContract.Presenter<HomeContract.View> {
        void initProjects();
        void firebaseListener();

        void sync();

    }
}

package com.badao.quiz.function.main.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseMvpActivity;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.main.presenter.MainActivityContract;
import com.badao.quiz.function.main.presenter.MainActivityPresenter;
import com.badao.quiz.helper.NavHelper;
import com.badao.quiz.model.Project;
import com.badao.quiz.service.NotificationService;
import com.badao.quiz.viewmode.BaseVMF;

public class MainActivity  extends BaseMvpActivity<MainActivityContract.Presenter>
        implements MainActivityContract.View{
    public NavHelper navHelper;
    private MainActivityVM mMainActivityModel;

    private NotificationService.NotificationBinder notificationBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            notificationBinder = (NotificationService.NotificationBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            notificationBinder = null;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initNavHelper() {
        navHelper = new NavHelper(this, R.id.navHost);
        Log.e("Init navHelper", "navHelper");
    }


    private void initViewModel() {
        mMainActivityModel = new ViewModelProvider(this, BaseVMF.getInstance()).get(MainActivityVM.class);
    }

    private void doObserve() {

        mMainActivityModel.getEventShowKeyBoard().observe(this, isShow -> {
           toggleKeyboard(isShow);
        });

        mMainActivityModel.getMlScheduleNotificationProject().observe(this, payload -> {
            if(AppConstants.SCHEDULE_NOTIFICATION == payload.getAction()){
                Project project = (Project) payload.getValue();
                if(notificationBinder != null){
                    notificationBinder.getService().scheduleAlarm(project.getSchedule(),
                                project.getId(), project.getName(), String.format("Funny Practice '%s' now!", project.getName()));
                }
            }
        });
    }

    @Override
    public void initViews(boolean isRefreshData) {
        initViewModel();
        initNavHelper();
        doObserve();
        startService( new Intent( this, NotificationService. class )) ;
        Intent intent = new Intent(this, NotificationService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected MainActivityContract.Presenter createPresenterInstance() {
        return new MainActivityPresenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

}
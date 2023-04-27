package com.badao.quiz.function.main.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.badao.quiz.viewmode.SingleLiveEvent;

public class MainActivityVM extends ViewModel {
    private boolean pendingCheckActivity = false;
    private boolean pendingBackToHome = false;
    private MutableLiveData<Integer> mldNumberOfTasks = new MutableLiveData<>();
    private MutableLiveData<Error> mldErrorDeviceDisable = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> mldIsRecreateFragment = new SingleLiveEvent<>();
    private SingleLiveEvent<Long> mMaxPosition = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mldPendingUpdateLesson = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> mVideoIdSeen = new SingleLiveEvent<>();
    private SingleLiveEvent<String> mldSearchKeyword = new SingleLiveEvent<>();
    private SingleLiveEvent<Void> mldRefreshSearch = new SingleLiveEvent<>();
    private SingleLiveEvent<Void> mRefreshNetwork = new SingleLiveEvent<>();
    private SingleLiveEvent<Void> eventPendingBackToHome = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> eventShowKeyBoard = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getEventShowKeyBoard() {
        return eventShowKeyBoard;
    }

    public void setStatusShowKey(Boolean isShow){
        eventShowKeyBoard.postValue(isShow);
    }

    public boolean isPendingCheckActivity() {
        return pendingCheckActivity;
    }
}

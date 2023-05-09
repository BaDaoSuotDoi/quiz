package com.badao.quiz.function.main.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.badao.quiz.viewmode.SingleLiveEvent;

public class MainActivityVM extends ViewModel {
    private boolean pendingCheckActivity = false;
    private MutableLiveData<Payload> mldProjectStatus = new SingleLiveEvent<>();
    private MutableLiveData<Payload> mlQuestionStatus = new SingleLiveEvent<>();
    private MutableLiveData<Payload> mlUserChangeAnswer = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> eventShowKeyBoard = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> getEventShowKeyBoard() {
        return eventShowKeyBoard;
    }

    public void setStatusShowKey(Boolean isShow){
        eventShowKeyBoard.postValue(isShow);
    }
    public void setProjectStatus(Payload payload){
        mldProjectStatus.postValue(payload);
    }

    public MutableLiveData<Payload> getMldProjectStatus() {
        return mldProjectStatus;
    }

    public MutableLiveData<Payload> getMlUserChangeAnswer() {
        return mlUserChangeAnswer;
    }

    public MutableLiveData<Payload> getMlQuestionStatus() {
        return mlQuestionStatus;
    }

    public boolean isPendingCheckActivity() {
        return pendingCheckActivity;
    }

    public static class Payload {
        private int action;
        private Object value;

        public Payload(int action, Object value) {
            this.action = action;
            this.value = value;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}

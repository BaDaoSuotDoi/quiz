package com.badao.quiz.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.badao.quiz.R;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetupTimeCpn extends LinearLayout {
    @BindView(R.id.sHour)
    Spinner sHour;
    @BindView(R.id.tvMinute)
    TextView tvMinute;
    @BindView(R.id.sSecond)
    Spinner sSecond;

    private int hour;
    private int minute;
    private int second;

    String[] hours = getResources().getStringArray(R.array.hour_setup);
    String[] minutes = getResources().getStringArray(R.array.minute_setup);
    String[] seconds = getResources().getStringArray(R.array.second_setup);
    private IListener iListener;
    public SetupTimeCpn(Context context) {
        super(context);
        initView();
    }

    public SetupTimeCpn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SetupTimeCpn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SetupTimeCpn(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void setListener(IListener iListener){
        this.iListener = iListener;
    }

    public void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_time_setup, this, true);
        ButterKnife.bind(this);
        ListPopupWindow listPopupWindow = new ListPopupWindow(tvMinute.getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(tvMinute.getContext(),
                android.R.layout.simple_spinner_dropdown_item, minutes);
        listPopupWindow.setAdapter(adapter);

        int heightInPixels = 400; // Set the desired height in pixels
        listPopupWindow.setHeight(heightInPixels);

        listPopupWindow.setAnchorView(tvMinute);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvMinute.setText(minutes[i]);
                listPopupWindow.dismiss();
            }
        });
        tvMinute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listPopupWindow.show();
            }
        });
        sHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                hour = Utils.getTimeSetup(adapterView.getItemAtPosition(position).toString());
                if(iListener!=null){
                    iListener.onChangeTime(hour,minute,second);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        sMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                minute = Utils.getTimeSetup(adapterView.getItemAtPosition(position).toString());
//                if(iListener!=null){
//                    iListener.onChangeTime(hour,minute,second);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        sSecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                second = Utils.getTimeSetup(adapterView.getItemAtPosition(position).toString());
                if(iListener!=null){
                    iListener.onChangeTime(hour,minute,second);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setTime(int time){
        if(time == -1){
            for(int i = 0; i < hours.length; i++){
                if(hours[i].equals("Endless")){
                    sHour.setSelection(i);
                }
            }
            tvMinute.setText(minutes[0]);
            tvMinute.setText(minutes[0]);
        }else{
            int hour = time /3600;
            int minute = (time - hour*3600)/60;
            int second = (time - hour*3600 - minute*60);
            Log.e("Set time", hour+"//"+minute+"//"+second);
            for(int i = 0; i < hours.length; i++){
                if(Utils.getTimeSetup(hours[i]) == hour){
                    sHour.setSelection(i);
                }
            }

            for(int i = 0; i < minutes.length; i++){
                if(Utils.getTimeSetup(minutes[i]) == minute){
                    tvMinute.setText(minutes[i]);
                }
            }

            for(int i = 0; i < seconds.length; i++){
                if(Utils.getTimeSetup(seconds[i]) == second){
                    sSecond.setSelection(i);
                }
            }
        }
    }

    public interface IListener{
        void onChangeTime(int hour, int minute, int second);
    }
}

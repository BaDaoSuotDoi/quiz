package com.badao.quiz.function.project.detail.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.badao.quiz.R;
import com.badao.quiz.base.dialog.BaseDialog;
import com.badao.quiz.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class ScheduleDialog extends BaseDialog {
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvSave)
    TextView tvSave;
    @BindView(R.id.sRepeat)
    Spinner sRepeat;

    private String date;
    private IListener iListener;
    public ScheduleDialog(String schedule, IListener iListener){
        if(schedule.isEmpty()){
            date = "00#00#00 "+ Utils.getTimeCurrent();
        }else{
            this.date = schedule;
        }

        Log.e("Date", date);
        this.iListener = iListener;

    }
    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_schedule_project;
    }

    @Override
    public void setupView() {
        super.setupView();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String content = String.format("%02d:%02d", hourOfDay, minute);
                date = date.replace(Utils.getTime(date), content);
                Log.e("date time", date);
                tvTime.setText(Utils.getTime(date));
            }
        };

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String formattedDate = String.format("%04d/%02d/%02d",year, monthOfYear + 1, dayOfMonth);
                date = date.replace(Utils.getDate(date), formattedDate);
                Log.e("date date", date);

                tvDate.setText(formattedDate);
            }
        };

        tvDate.setText(Utils.getDate(this.date));
        tvTime.setText(Utils.getTime(this.date));

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] times = Utils.getTime(date).split(":");
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeSetListener,
                        Integer.parseInt(times[0]), Integer.parseInt(times[1]), false);
                timePickerDialog.show();
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = Utils.getDate(date).split("/");
                // Show date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener,
                        Integer.parseInt(items[0]),  Integer.parseInt(items[1]) - 1,  Integer.parseInt(items[2]));
                datePickerDialog.show();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListener.onSave(date);
                dismiss();
            }
        });


        sRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String interval = adapterView.getItemAtPosition(position).toString();
                if(interval.equals("Every day")) {
                    date = "00#00#01 " + date.substring(9);
                }else if(interval.equals("Every week")){
                    date = "00#01#00 " + date.substring(9);
                }else if(interval.equals("Every month")){
                    date = "01#00#00 " + date.substring(9);
                }else if(interval.equals("No repeat")){
                    date = "00#00#00 " + date.substring(9);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public  interface IListener{
        void onSave(String date);
    }
}

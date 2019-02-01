package com.sup2is.accountbook.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.databinding.FragmentInputFormBinding;
import com.sup2is.accountbook.databinding.LayoutDatePickerBinding;
import com.sup2is.accountbook.databinding.LayoutTimePickerBinding;
import com.sup2is.accountbook.dialog.CustomDialog;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;

public class InputFormDialogFragment extends DialogFragment implements View.OnClickListener {


    private final GlobalDate globalDate = GlobalDate.getInstance();

    private FragmentInputFormBinding inputFormBinding;

    private DatePickerDialog datePickerDialog;

    private TimePickerDialog timePickerDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER| Gravity.BOTTOM);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_input_form,container,false);
        inputFormBinding = DataBindingUtil.bind(view);


        ArrayList<String> methodList = new ArrayList<>();
        methodList.add("지출");
        methodList.add("수입");

        ArrayAdapter<String> methodAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner_text_item,methodList);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFormBinding.acsMethod.setAdapter(methodAdapter);

        ArrayList<String> spendingList = new ArrayList<>();
        spendingList.add("카드");
        spendingList.add("현금");

        ArrayAdapter<String> spendingAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner_text_item,spendingList);
        spendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFormBinding.acsSpending.setAdapter(spendingAdapter);

        ArrayList<String> groupList = new ArrayList<>();
        groupList.add("식비");
        groupList.add("교통비");
        groupList.add("술값");
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner_text_item,groupList);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFormBinding.acsGroup.setAdapter(groupAdapter);


        globalDate.setCurrentTime();

        inputFormBinding.tvDate.setText(globalDate.getYearMonthDayToString());
        inputFormBinding.tvTime.setText(globalDate.getTimeToString());


        datePickerDialog = new DatePickerDialog(getContext(),R.style.CustomDatePicker,new DatePickerListener(),globalDate.getYear(),globalDate.getMonth()-1,globalDate.getDay());
        timePickerDialog = new TimePickerDialog(getContext(),R.style.CustomTimePicker,new TimePickerListener(),globalDate.getHour(),globalDate.getMinute(),false);


        inputFormBinding.ibAddGroup.setOnClickListener(this);
        inputFormBinding.ibAddSpending.setOnClickListener(this);
        inputFormBinding.rlDateContainer.setOnClickListener(this);
        inputFormBinding.rlTimeContainer.setOnClickListener(this);
        inputFormBinding.btnCancel.setOnClickListener(this);
        inputFormBinding.btnMore.setOnClickListener(this);
        inputFormBinding.btnOk.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        CustomDialog.Builder builder;
        CustomDialog dialog;
        switch (v.getId()) {
            case R.id.ib_add_group:
                builder = new CustomDialog.Builder();
                builder.setTitle("분류 저장하기")
                        .setHint("내용을 입력해주세요");
                dialog = new CustomDialog(getActivity(),builder);
                dialog.show();
                break;
            case R.id.ib_add_spending:
                builder = new CustomDialog.Builder();
                builder.setTitle("분류 저장하기")
                        .setHint("내용을 입력해주세요");
                dialog = new CustomDialog(getActivity(),builder);
                dialog.show();
                break;
            case R.id.rl_date_container:
                datePickerDialog.show();
                break;
            case R.id.rl_time_container:
                timePickerDialog.show();
                break;
            case R.id.btn_ok:
                getDialog().dismiss();
                break;
            case R.id.btn_cancel:
                getDialog().dismiss();
                break;
            case R.id.btn_more:
                break;
        }

    }

    private class TimePickerListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            inputFormBinding.tvTime.setText(hourOfDay+ ":" + minute);
        }
    }

    private class DatePickerListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            inputFormBinding.tvDate.setText(year+ "." + (month + 1) + "." + dayOfMonth);
        }
    }

}

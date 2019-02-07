package com.sup2is.accountbook.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.databinding.FragmentInputFormBinding;
import com.sup2is.accountbook.dialog.CustomDialog;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.DateBundle;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InputFormDialogFragment extends DialogFragment implements View.OnClickListener {


    private final GlobalDate globalDate = GlobalDate.getInstance();

    private FragmentInputFormBinding inputFormBinding;

    private DatePickerDialog datePickerDialog;

    private TimePickerDialog timePickerDialog;

    private DBManager dbManager;

    private AccountBookApplication application;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER| Gravity.BOTTOM);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_input_form,container,false);
        inputFormBinding = DataBindingUtil.bind(view);

        application = (AccountBookApplication) getActivity().getApplication();
        dbManager = application.getDbManager();

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



        //단순 input은 global date를 사용하면 안됨
        Calendar calendar = Calendar.getInstance();

        inputFormBinding.tvDate.setText(calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE));
        inputFormBinding.tvTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

        datePickerDialog = new DatePickerDialog(getContext(),R.style.CustomDatePicker,new DatePickerListener(),calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH)),calendar.get(Calendar.DATE));
        timePickerDialog = new TimePickerDialog(getContext(),R.style.CustomTimePicker,new TimePickerListener(),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);

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

                String date = inputFormBinding.tvDate.getText().toString();
                String[] dates = date.split("\\.");

                String time = inputFormBinding.tvTime.getText().toString();
                String[] times = time.split(":");

                String money = inputFormBinding.etMoney.getText().toString();
                String method = inputFormBinding.acsMethod.getSelectedItem().toString();
                String group = inputFormBinding.acsGroup.getSelectedItem().toString();
                String spending = inputFormBinding.acsSpending.getSelectedItem().toString();
                String content = inputFormBinding.etContent.getText().toString();

                String dayOfWeek = getDayOfWeek(dates[0],dates[1],dates[2]);
                DateBundle dateBundle = new DateBundle(dates[0],dates[1],dates[2],dayOfWeek,times[0],times[1],"0");
                Account account = new Account(dateBundle,money,method,group,spending,content);
                dbManager.insertItem(account);

                //DailyListViewFragment
                DailyListViewFragment dailyListViewFragment = (DailyListViewFragment) getFragmentManager().getFragments().get(0);
                dailyListViewFragment.setUserVisibleHint(true);
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

    private String getDayOfWeek(String year, String month, String day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day)));
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return "일";
            case 2:
                return "월";
            case 3:
                return "화";
            case 4:
                return "수";
            case 5:
                return "목";
            case 6:
                return "금";
            case 7:
                return "토";
        }
        return null;
    }


}

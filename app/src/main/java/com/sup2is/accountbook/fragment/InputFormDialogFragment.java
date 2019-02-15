package com.sup2is.accountbook.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.sup2is.accountbook.util.CommaFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InputFormDialogFragment extends DialogFragment implements View.OnClickListener {

    private FragmentInputFormBinding inputFormBinding;

    private DatePickerDialog datePickerDialog;

    private TimePickerDialog timePickerDialog;

    private DBManager dbManager;

    private AccountBookApplication application;

    private int idx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER| Gravity.BOTTOM);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_input_form,container,false);
        inputFormBinding = DataBindingUtil.bind(view);

        application = (AccountBookApplication) getActivity().getApplication();
        dbManager = application.getDbManager();

        //todo method, spending, group은 전부 category db에서 불러오도록
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
        inputFormBinding.tvTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

        datePickerDialog = new DatePickerDialog(getContext(),R.style.CustomDatePicker,new DatePickerListener(),calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH)),calendar.get(Calendar.DATE));
        timePickerDialog = new TimePickerDialog(getContext(),R.style.CustomTimePicker,new TimePickerListener(),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);

        //todo 만약 bundle 값이 있으면 idx로 db에 검색 후 저장된 값으로 스피너, 달력 , 시계 세팅
        if(bundle != null) {
            idx = bundle.getInt("idx");
            Account account = dbManager.selectByIdx(idx);
            inputFormBinding.acsMethod.setSelection(methodList.indexOf(account.getMethod()));

            inputFormBinding.tvTitle.setText(getResources().getString(R.string.modify));
            inputFormBinding.acsGroup.setSelection(groupList.indexOf(account.getGroup()));
            inputFormBinding.acsSpending.setSelection(spendingList.indexOf(account.getSpending()));
            inputFormBinding.etMoney.setText(CommaFormatter.comma(Long.parseLong(account.getMoney())));
            inputFormBinding.etContent.setText(account.getContent());
            datePickerDialog = new DatePickerDialog(getContext(),R.style.CustomDatePicker,new DatePickerListener(),Integer.parseInt(account.getDateBundle().getYear()),Integer.parseInt(account.getDateBundle().getMonth()) -1,Integer.parseInt(account.getDateBundle().getDay()));
            timePickerDialog = new TimePickerDialog(getContext(),R.style.CustomTimePicker,new TimePickerListener(),Integer.parseInt(account.getDateBundle().getHour()),Integer.parseInt(account.getDateBundle().getDay()),false);
            inputFormBinding.tvDate.setText(account.getDateBundle().getYear() + "." + account.getDateBundle().getMonth()+ "." + account.getDateBundle().getDay());
            inputFormBinding.tvTime.setText(account.getDateBundle().getHour() + ":" + account.getDateBundle().getMinute());
            inputFormBinding.llEditBtnWrapper.setVisibility(View.VISIBLE);
            inputFormBinding.llInputBtnWrapper.setVisibility(View.GONE);
            inputFormBinding.tvId.setText(idx + "");
        }

        inputFormBinding.ibAddGroup.setOnClickListener(this);
        inputFormBinding.ibAddSpending.setOnClickListener(this);
        inputFormBinding.rlDateContainer.setOnClickListener(this);
        inputFormBinding.rlTimeContainer.setOnClickListener(this);
        inputFormBinding.btnCancel.setOnClickListener(this);
        inputFormBinding.btnMore.setOnClickListener(this);
        inputFormBinding.btnOk.setOnClickListener(this);
        inputFormBinding.btnModify.setOnClickListener(this);
        inputFormBinding.btnDelete.setOnClickListener(this);
        inputFormBinding.etMoney.addTextChangedListener(textWatcher);
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
                if(inputFormBinding.etMoney.getText().toString().length() == 0 && inputFormBinding.etMoney.getText().toString().equals("")) {
                    inputFormBinding.tilEtMoney.setError("금액을 입력해주세요.");
                    return;
                }
                addItem();
                refreshDailyFragment();
                getDialog().dismiss();
                break;
            case R.id.btn_cancel:
                getDialog().dismiss();
                break;
            case R.id.btn_more:
                if(inputFormBinding.etMoney.getText().toString().length() == 0 && inputFormBinding.etMoney.getText().toString().equals("")) {
                    inputFormBinding.tilEtMoney.setError("금액을 입력해주세요.");
                    return;
                }
                addItem();
                refreshDailyFragment();
                getDialog().dismiss();
                FragmentManager fm = getFragmentManager();
                InputFormDialogFragment inputFormDialogFragment = new InputFormDialogFragment();
                inputFormDialogFragment.show(fm,"input");
                break;
            case  R.id.btn_modify:
                if(inputFormBinding.etMoney.getText().toString().length() == 0 && inputFormBinding.etMoney.getText().toString().equals("")) {
                    inputFormBinding.tilEtMoney.setError("금액을 입력해주세요.");
                    return;
                }
                modifyItem(Integer.parseInt(inputFormBinding.tvId.getText().toString()));
                refreshDailyFragment();
                getDialog().dismiss();
                break;
            case  R.id.btn_delete:
                deleteItem(Integer.parseInt(inputFormBinding.tvId.getText().toString()));
                refreshDailyFragment();
                getDialog().dismiss();
                break;

        }
    }

    private void refreshDailyFragment() {
        DailyListViewFragment dailyListViewFragment = (DailyListViewFragment) getFragmentManager().getFragments().get(0);
        dailyListViewFragment.setUserVisibleHint(true);
    }

    private void addItem () {
        String money = inputFormBinding.etMoney.getText().toString().replaceAll(",", "");
        String date = inputFormBinding.tvDate.getText().toString();
        String[] dates = date.split("\\.");
        String time = inputFormBinding.tvTime.getText().toString();
        String[] times = time.split(":");
        String group = inputFormBinding.acsGroup.getSelectedItem().toString();
        String method = inputFormBinding.acsMethod.getSelectedItem().toString();
        String spending = inputFormBinding.acsSpending.getSelectedItem().toString();
        String content = inputFormBinding.etContent.getText().toString();
        String dayOfWeek = getDayOfWeek(dates[0],dates[1],dates[2]); // year, month, day
        DateBundle dateBundle = new DateBundle(dates[0],dates[1],dates[2],dayOfWeek,times[0],times[1],"0");
        String type = dbManager.getItemType(dateBundle);  // day, hour, minute
        int idx = dbManager.getNextAutoIncrement();
        Account account = new Account(idx,dateBundle,money,method,group,spending,content,type);
        dbManager.insertItem(account);
    }

    private void modifyItem(int idx) {
        String money = inputFormBinding.etMoney.getText().toString().replaceAll(",", "");
        String date = inputFormBinding.tvDate.getText().toString();
        String[] dates = date.split("\\.");
        String time = inputFormBinding.tvTime.getText().toString();
        String[] times = time.split(":");
        String group = inputFormBinding.acsGroup.getSelectedItem().toString();
        String method = inputFormBinding.acsMethod.getSelectedItem().toString();
        String spending = inputFormBinding.acsSpending.getSelectedItem().toString();
        String content = inputFormBinding.etContent.getText().toString();
        String dayOfWeek = getDayOfWeek(dates[0],dates[1],dates[2]);
        DateBundle dateBundle = new DateBundle(dates[0],dates[1],dates[2],dayOfWeek,times[0],times[1],"0");
        String type = dbManager.getItemType(dateBundle);
        Account newAccount = new Account(idx,dateBundle,money,method,group,spending,content,type);
        Account oldAccount = dbManager.selectByIdx(idx);
        dbManager.modifyItem(newAccount);
        dbManager.reorderingData(oldAccount.getDateBundle());
    }

    private void deleteItem(int idx) {
        Account oldAccount = dbManager.selectByIdx(idx);
        dbManager.deleteItem(idx);
        dbManager.reorderingData(oldAccount.getDateBundle());
    }

    private class TimePickerListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            inputFormBinding.tvTime.setText(String.format("%02d:%02d", hourOfDay, minute));
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

    private String result;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                result = CommaFormatter.comma(Long.parseLong(charSequence.toString().replaceAll(",", "")));
                inputFormBinding.etMoney.setText(result);
                inputFormBinding.etMoney.setSelection(result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };



}

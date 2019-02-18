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
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBHelper;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.databinding.FragmentInputFormBinding;
import com.sup2is.accountbook.dialog.CustomDialog;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.DateBundle;
import com.sup2is.accountbook.util.CommaFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InputFormDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FragmentInputFormBinding inputFormBinding;

    private DatePickerDialog datePickerDialog;

    private TimePickerDialog timePickerDialog;

    private DBManager dbManager;

    private AccountBookApplication application;

    private int idx;

    private ArrayAdapter<String> methodAdapter;
    private ArrayAdapter<String> spendingAdapter;
    private ArrayAdapter<String> groupAdapter;
    private ArrayAdapter<String> incomingAdapter;

    private ArrayList<String> groupList;
    private ArrayList<String> spendingList;
    private ArrayList<String> incomingList;

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

        ArrayList<String> methodList = dbManager.getSpinnerList(DBHelper.METHOD_TYPE);
        methodAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner_text_item,methodList);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFormBinding.acsMethod.setAdapter(methodAdapter);

        groupList = dbManager.getSpinnerList(DBHelper.GROUP_TYPE);
        groupAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner_text_item,groupList);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFormBinding.acsGroup.setAdapter(groupAdapter);

        spendingList = dbManager.getSpinnerList(DBHelper.SPENDING_TYPE);
        spendingAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner_text_item,spendingList);
        spendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFormBinding.acsSpending.setAdapter(spendingAdapter);

        incomingList = dbManager.getSpinnerList(DBHelper.INCOMING_TYPE);
        incomingAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner_text_item,incomingList);
        incomingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFormBinding.acsIncoming.setAdapter(incomingAdapter);

        //단순 input은 global date를 사용하면 안됨
        Calendar calendar = Calendar.getInstance();
        inputFormBinding.tvDate.setText(calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE));
        inputFormBinding.tvTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

        datePickerDialog = new DatePickerDialog(getContext(),R.style.CustomDatePicker,new DatePickerListener(),calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH)),calendar.get(Calendar.DATE));
        timePickerDialog = new TimePickerDialog(getContext(),R.style.CustomTimePicker,new TimePickerListener(),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);

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
        inputFormBinding.acsMethod.setOnItemSelectedListener(this);
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
                        .setHint("내용을 입력해주세요")
                        .setType(DBHelper.GROUP_TYPE);
                dialog = new CustomDialog(getActivity(),builder);
                dialog.show();
                break;
            case R.id.ib_add_spending:
                builder = new CustomDialog.Builder();
                builder.setTitle("지출 저장하기")
                        .setHint("내용을 입력해주세요")
                        .setType(DBHelper.SPENDING_TYPE);
                dialog = new CustomDialog(getActivity(),builder);
                dialog.show();
                break;
            case R.id.ib_add_incoming:
                builder = new CustomDialog.Builder();
                builder.setTitle("수입 저장하기")
                        .setHint("내용을 입력해주세요")
                        .setType(DBHelper.INCOMING_TYPE);
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
        DateBundle dateBundle = getDateBundle();
        String money = inputFormBinding.etMoney.getText().toString().replaceAll(",", "");
        String group = inputFormBinding.acsGroup.getSelectedItem().toString();
        String method = inputFormBinding.acsMethod.getSelectedItem().toString();
        String spending;
        if(method.equals("수입")){
            spending = inputFormBinding.acsIncoming.getSelectedItem().toString();
        }else {
            spending = inputFormBinding.acsSpending.getSelectedItem().toString();
        }

        String content = inputFormBinding.etContent.getText().toString();
        String type = dbManager.getItemType(dateBundle);  // day, hour, minute
        int idx = dbManager.getNextAutoIncrement(DBHelper.TBL_ACCOUNT);
        Account account = new Account(idx,dateBundle,money,method,group,spending,content,type);
        dbManager.insertItem(account);
    }

    private DateBundle getDateBundle() {
        String date = inputFormBinding.tvDate.getText().toString();
        String[] dates = date.split("\\.");
        String time = inputFormBinding.tvTime.getText().toString();
        String[] times = time.split(":");
        String dayOfWeek = getDayOfWeek(dates[0],dates[1],dates[2]); // year, month, day
        return new DateBundle(dates[0],dates[1],dates[2],dayOfWeek,times[0],times[1],"0");
    }

    private void modifyItem(int idx) {
        DateBundle dateBundle = getDateBundle();
        String money = inputFormBinding.etMoney.getText().toString().replaceAll(",", "");
        String group = inputFormBinding.acsGroup.getSelectedItem().toString();
        String method = inputFormBinding.acsMethod.getSelectedItem().toString();
        String spending;
        if(method.equals("수입")){
            spending = inputFormBinding.acsIncoming.getSelectedItem().toString();
        }else {
            spending = inputFormBinding.acsSpending.getSelectedItem().toString();
        }
        String content = inputFormBinding.etContent.getText().toString();
        String type = dbManager.getItemType(dateBundle);
        Account newAccount = new Account(idx,dateBundle,money,method,group,spending,content,type);
        //원본객체
        Account oldAccount = dbManager.selectByIdx(idx);
        dbManager.modifyItem(newAccount);
        dbManager.reorderingData(oldAccount.getDateBundle());
    }

    private void deleteItem(int idx) {
        //원본객체
        Account oldAccount = dbManager.selectByIdx(idx);
        dbManager.deleteItem(idx);
        dbManager.reorderingData(oldAccount.getDateBundle());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();

        if(selected.equals("지출")) {
            inputFormBinding.acsSpendingWrapper.setVisibility(View.VISIBLE);
            inputFormBinding.acsIncomingWrapper.setVisibility(View.GONE);
        }
        if(selected.equals("수입")) {
            inputFormBinding.acsIncomingWrapper.setVisibility(View.VISIBLE);
            inputFormBinding.acsSpendingWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void refreshSpinnerAdapter(int type,String name) {
        switch (type){
            case DBHelper.GROUP_TYPE :
                groupAdapter.add(name);
                groupAdapter.notifyDataSetChanged();
                inputFormBinding.acsGroup.setSelection(groupList.indexOf(name));
                break;
            case DBHelper.SPENDING_TYPE :
                spendingAdapter.add(name);
                spendingAdapter.notifyDataSetChanged();
                inputFormBinding.acsSpending.setSelection(spendingList.indexOf(name));
                break;
            case DBHelper.INCOMING_TYPE :
                incomingAdapter.add(name);
                incomingAdapter.notifyDataSetChanged();
                inputFormBinding.acsIncoming.setSelection(incomingList.indexOf(name));
                break;
        }
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

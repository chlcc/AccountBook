package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.adapter.CalendarGridAdapter;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.databinding.FragmentCalendarBinding;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.DateBundle;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment {


    private static final String TAG = CalendarFragment.class.getSimpleName();
    private static final int GRID_COUNT = 42;

    private FragmentCalendarBinding calendarBinding;
    private GlobalDate globalDate = GlobalDate.getInstance();
    private ArrayList<String> dayList = new ArrayList<>();
    private CalendarGridAdapter calendarGridAdapter;
    private final String[] DAY_OF_WEEK = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
    private ArrayList<Account> accounts;
    private AccountBookApplication application;
    private DBManager dbManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);
        calendarBinding = DataBindingUtil.bind(view);

        application = (AccountBookApplication) getActivity().getApplication();
        dbManager = application.getDbManager();

        DateBundle dateBundle = new DateBundle(String.valueOf(globalDate.getYear()),String.valueOf(globalDate.getMonth()),String.valueOf(globalDate.getDay()),null,null,null,null);
        accounts = dbManager.selectByDate(dateBundle);

        dayList = getCurrentDayList();

        calendarGridAdapter = new CalendarGridAdapter(getContext(),dayList,accounts);
        calendarBinding.gvCalendar.setAdapter(calendarGridAdapter);
        calendarBinding.gvCalendar.setVerticalScrollBarEnabled(false);

        TextView temp;

        for(int i = 0; i < Calendar.DAY_OF_WEEK; i++) {
            temp = new TextView(getContext());
            temp.setText(DAY_OF_WEEK[i]);
            temp.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT,1));
            temp.setGravity(Gravity.CENTER);
            temp.setTextColor(getResources().getColor(R.color.darkGray));
            calendarBinding.llDayOfWeek.addView(temp);
        }

        return view;
    }

    private ArrayList<String> getCurrentDayList() {
        ArrayList<String> array = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        // 현재 년, 월의 1일 셋팅
        calendar.set(globalDate.getYear(),globalDate.getMonth()-1,1);

        int dayNum = calendar.get(Calendar.DAY_OF_WEEK) - 1 ;

        for(int i = 0; i < dayNum; i++) {
            array.add("");
        }

        for(int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i ++) {
            array.add("" + (i + 1));
        }

        for(int i = array.size(); i < GRID_COUNT; i ++) {
            array.add("");
        }
        return array;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            calendarBinding.gvCalendar.setAdapter(null);
            dayList.clear();
            dayList = getCurrentDayList();
            DateBundle dateBundle = new DateBundle(String.valueOf(globalDate.getYear()),String.valueOf(globalDate.getMonth()),String.valueOf(globalDate.getDay()),null,null,null,null);
            accounts.clear();
            accounts = dbManager.selectByDate(dateBundle);
            calendarGridAdapter = new CalendarGridAdapter(getContext(),dayList,accounts);
            calendarBinding.gvCalendar.setAdapter(calendarGridAdapter);
//            calendarGridAdapter.updateList(dayList,accounts);
//            calendarGridAdapter.notifyDataSetChanged();
        }
    }
}

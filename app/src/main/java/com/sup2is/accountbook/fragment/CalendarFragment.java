package com.sup2is.accountbook.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.adapter.CalendarGridAdapter;
import com.sup2is.accountbook.databinding.FragmentCalendarBinding;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends BaseFragment {


    private static final String TAG = CalendarFragment.class.getSimpleName();
    private static final int GRID_COUNT = 42;

    private FragmentCalendarBinding calendarBinding;

    private GlobalDate globalDate = GlobalDate.getInstance();

    private final ArrayList<String> dayList = new ArrayList<>();

    private CalendarGridAdapter calendarGridAdapter;

    private final String[] DAY_OF_WEEK = {"SUN","MON","TUE","WED","THU","FRI","SAT"};


    @Override
    public void onAttach(Context context) {
        Log.d(TAG,"onAttach call");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreate call");
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onActivityCreated call");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG,"onStart call");
        super.onStart();
    }


    @Override
    public void onResume() {
        Log.d(TAG,"onResume call");
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG,"onCreateView call");
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);
        calendarBinding = DataBindingUtil.bind(view);

        setCalendarDate();

        calendarGridAdapter = new CalendarGridAdapter(getContext(),dayList);
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

    private void setCalendarDate() {

        if(this.dayList != null) {
            this.dayList.clear();
        }

        int dayNum = globalDate.getDayNum();

        for(int i = 0; i < dayNum; i++) {
            dayList.add("");
        }

        for(int i = 0; i < globalDate.getActualMaximum(); i ++) {
            dayList.add("" + (i + 1));
        }

        for(int i = dayList.size(); i < GRID_COUNT; i ++) {
            dayList.add("");
       }

    }


    @Override
    public void refreshView() {

        globalDate = GlobalDate.getInstance();
        globalDate.getDate();

        //adapter reset

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            setCalendarDate();
            calendarGridAdapter.setDayList(dayList);
            calendarGridAdapter.notifyDataSetChanged();
        }
    }
}

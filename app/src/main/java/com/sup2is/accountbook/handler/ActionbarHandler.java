package com.sup2is.accountbook.handler;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.activity.MainActivity;
import com.sup2is.accountbook.fragment.CalendarPickerDialogFragment;
import com.sup2is.accountbook.util.GlobalDate;

public class ActionbarHandler {

    private GlobalDate globalDate;

    private RelativeLayout parent;

    public void onClickLeftButton (View view) {
        globalDate = GlobalDate.getInstance();
        globalDate.previousMonth();
        parent = (RelativeLayout) view.getParent();
        ((TextView) parent.findViewById(R.id.tv_calendar)).setText(globalDate.getDate());
        ((MainActivity)view.getContext()).refreshFragment();
    }

    public void onClickRightButton (View view) {
        globalDate = GlobalDate.getInstance();
        globalDate.nextMonth();
        parent = (RelativeLayout) view.getParent();
        ((TextView) parent.findViewById(R.id.tv_calendar)).setText(globalDate.getDate());
        ((MainActivity)view.getContext()).refreshFragment();
    }

    public void onClickCalendarPicker(View view) {

        FragmentManager fm = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
        CalendarPickerDialogFragment calendarPickerFragment = new CalendarPickerDialogFragment();
        calendarPickerFragment.show(fm,"calendar");

    }

}

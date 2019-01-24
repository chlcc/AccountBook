package com.sup2is.accountbook.handler;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.util.GlobalDate;

public class ActionbarHandler {

    private GlobalDate globalDate;

    private RelativeLayout parent;

    private TextView tv_calendar;

    public void onClickLeftButton (View view) {
        globalDate = GlobalDate.getInstance();
        globalDate.previousDate();
        parent = (RelativeLayout) view.getParent();
        ((TextView) parent.findViewById(R.id.tv_calendar)).setText(globalDate.getDate());
    }

    public void onClickRightButton (View view) {
        globalDate = GlobalDate.getInstance();
        globalDate.nextDate();
        parent = (RelativeLayout) view.getParent();
        ((TextView) parent.findViewById(R.id.tv_calendar)).setText(globalDate.getDate());
    }


}

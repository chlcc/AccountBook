package com.sup2is.accountbook.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.util.GlobalDate;

public class CalendarFragment extends BaseFragment {


    private static final String TAG = CalendarFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar,container,false);
    }


    @Override
    public void refreshView() {

        globalDate = GlobalDate.getInstance();
        globalDate.getDate();


    }
}

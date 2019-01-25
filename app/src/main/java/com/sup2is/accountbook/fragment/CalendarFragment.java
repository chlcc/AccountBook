package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.databinding.FragmentCalendarBinding;
import com.sup2is.accountbook.util.GlobalDate;

public class CalendarFragment extends BaseFragment {


    private static final String TAG = CalendarFragment.class.getSimpleName();

    private FragmentCalendarBinding calendarBinding;

    private GlobalDate globalDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        globalDate = GlobalDate.getInstance();

        View view = inflater.inflate(R.layout.fragment_calendar,container,false);
        calendarBinding = DataBindingUtil.bind(view);
        calendarBinding.mcvContainer.setTopbarVisible(false);

        return view;
    }


    @Override
    public void refreshView() {

        globalDate = GlobalDate.getInstance();
        globalDate.getDate();


    }
}

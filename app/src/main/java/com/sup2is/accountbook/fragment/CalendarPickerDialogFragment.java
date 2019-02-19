package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.activity.MainActivity;
import com.sup2is.accountbook.databinding.FragmentCalendarPickerBinding;
import com.sup2is.accountbook.util.GlobalDate;

public class CalendarPickerDialogFragment extends DialogFragment {


    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1980;

    FragmentCalendarPickerBinding calendarPickerBinding;

    private final GlobalDate globalDate = GlobalDate.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

        View view = inflater.inflate(R.layout.fragment_calendar_picker,container,false);

        calendarPickerBinding = DataBindingUtil.bind(view);

        calendarPickerBinding.npMonth.setMinValue(1);
        calendarPickerBinding.npMonth.setMaxValue(12);
        calendarPickerBinding.npMonth.setValue(globalDate.getMonth());

        calendarPickerBinding.npYear.setMinValue(MIN_YEAR);
        calendarPickerBinding.npYear.setMaxValue(MAX_YEAR);
        calendarPickerBinding.npYear.setValue(globalDate.getYear());
        CalendarPickerListener listener = new CalendarPickerListener();

        calendarPickerBinding.npYear.setOnClickListener(listener);
        calendarPickerBinding.npMonth.setOnClickListener(listener);

        return view;
    }

    private class CalendarPickerListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int selectedMonth = calendarPickerBinding.npMonth.getValue() - 1;
            int selectedYear = calendarPickerBinding.npYear.getValue();
            globalDate.setDate(selectedYear,selectedMonth,1);
            ((MainActivity)(getActivity())).refreshFragment();
            ((MainActivity)(getActivity())).refreshActionBar();
            getDialog().dismiss();
        }
    }
}
package com.sup2is.accountbook.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.util.GlobalDate;

public class DailyListViewFragment extends BaseFragment {

    private static final String TAG = DailyListViewFragment.class.getSimpleName();

    private GlobalDate globalDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        globalDate = GlobalDate.getInstance();
        return inflater.inflate(R.layout.fragment_daily_listview,container,false);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void refreshView() {

        globalDate = GlobalDate.getInstance();
        globalDate.getDate();

    }
}

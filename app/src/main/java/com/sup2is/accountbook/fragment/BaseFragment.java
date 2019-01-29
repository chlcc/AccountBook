package com.sup2is.accountbook.fragment;

import android.support.v4.app.Fragment;

import com.sup2is.accountbook.util.GlobalDate;

public abstract class BaseFragment extends Fragment {

    GlobalDate globalDate;
    public abstract void refreshView();

}

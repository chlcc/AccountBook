package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.adapter.DailyListViewAdapter;
import com.sup2is.accountbook.databinding.FragmentDailyListviewBinding;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.DateBundle;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;

public class DailyListViewFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = DailyListViewFragment.class.getSimpleName();

    private GlobalDate globalDate;

    private FragmentDailyListviewBinding dailyListviewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        globalDate = GlobalDate.getInstance();
        View view = inflater.inflate(R.layout.fragment_daily_listview,container,false);
        dailyListviewBinding = DataBindingUtil.bind(view);

        ArrayList<Account> accounts = new ArrayList<>();

        //todo db에서 global date의 해당하는 값들 전부 불러서 dailyListItem 객체로 변환



        accounts.add(new Account(new DateBundle("2018","1","26","목","6","30","31"),"559,000","지출","카드","식비","돈없다 .."));

        DailyListViewAdapter dailyListViewAdapter = new DailyListViewAdapter(getContext(), accounts);
        dailyListviewBinding.lvDailyList.setAdapter(dailyListViewAdapter);


        dailyListviewBinding.fabInput.setOnClickListener(this);

        return view;

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
        globalDate.getYearMonthToString();

    }

    @Override
    public void onClick(View v) {

        FragmentManager fm = getFragmentManager();
        InputFormDialogFragment inputFormDialogFragment = new InputFormDialogFragment();
        inputFormDialogFragment.show(fm,"input");

//        new InputDialog(getActivity());


    }
}

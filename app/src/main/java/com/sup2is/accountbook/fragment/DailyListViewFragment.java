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
import com.sup2is.accountbook.model.DailyListItem;
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

        ArrayList<DailyListItem> dailyListItems = new ArrayList<>();

        //todo db에서 global date의 해당하는 값들 전부 불러서 dailyListItem 객체로 변환

        dailyListItems.add(new DailyListItem("24","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("15","16:30","금","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("13","16:30","토","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","일","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));
        dailyListItems.add(new DailyListItem("10","16:30","목","99,000","에어팟 샀음","기타","카드"));

        DailyListViewAdapter dailyListViewAdapter = new DailyListViewAdapter(getContext(),dailyListItems);
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
        globalDate.getDate();

    }

    @Override
    public void onClick(View v) {


        FragmentManager fm = getFragmentManager();
        InputFormDialogFragment inputFormDialogFragment = new InputFormDialogFragment();
        inputFormDialogFragment.show(fm,"input");

//        new InputDialog(getActivity());


    }
}

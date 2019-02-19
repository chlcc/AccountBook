package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sup2is.accountbook.R;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.databinding.FragmentStatisticsBinding;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.DateBundle;
import com.sup2is.accountbook.util.CommaFormatter;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatisticsFragment extends BaseFragment {

    private static final String TAG = StatisticsFragment.class.getSimpleName();

    private FragmentStatisticsBinding statisticsBinding;

    private AccountBookApplication application;
    private DBManager dbManager;
    private GlobalDate globalDate = GlobalDate.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_statistics,container,false);
        statisticsBinding = DataBindingUtil.bind(view);
        application = (AccountBookApplication) getActivity().getApplication();
        dbManager = application.getDbManager();
        initChart();
        return view;
    }


    private void initChart() {

        ArrayList<Entry> dataSet = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        if(statisticsBinding.pcContainer.getData() != null) {
            statisticsBinding.pcContainer.clear();
            statisticsBinding.pcContainer.invalidate();
        }

        DateBundle dateBundle = new DateBundle(String.valueOf(globalDate.getYear()), String.valueOf(globalDate.getMonth()), null,null,null,null,null);
        ArrayList<Account> accounts = dbManager.selectByDate(dateBundle);
        ArrayList<String> spendingList = dbManager.selectByDateToSpendingList(dateBundle);

        statisticsBinding.pcContainer.setUsePercentValues(true);
        Map<String,Long> dataMap = new HashMap<>();
        for(String spending : spendingList) {
            long value;
            dataMap.put(spending, 0L);
            for(Account account : accounts){
                if(account.getSpending().equals(spending)){
                    value = dataMap.get(spending) + Long.parseLong(account.getMoney());
                    dataMap.remove(spending);
                    dataMap.put(spending,value);
                }
            }
        }

        Iterator<String> keys = dataMap.keySet().iterator();

        if(keys.hasNext()) {
            int index = 0;
            while( keys.hasNext() ){
                String key = keys.next();
                dataSet.add(new Entry(dataMap.get(key),index));
                xVals.add(key + "\n" + CommaFormatter.comma(dataMap.get(key)) + " 원");
                index ++;
            }

            PieDataSet pieDataSet = new PieDataSet(dataSet,"");

            PieData data = new PieData(xVals, pieDataSet);
            pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            data.setValueTextSize(11);
            data.setValueTextColor(Color.DKGRAY);
            statisticsBinding.pcContainer.setDrawHoleEnabled(true);
            statisticsBinding.pcContainer.setTransparentCircleRadius(32f);
            statisticsBinding.pcContainer.setHoleRadius(30f);
            statisticsBinding.pcContainer.setData(data);
            statisticsBinding.pcContainer.setDescription("");

            long totalSpending = dbManager.selectByDateToTotalSpending(dateBundle);
            statisticsBinding.pcContainer.setCenterText("총금액" + "\n" + CommaFormatter.comma(totalSpending) + "원" );
        }

    }


    @Override
    public void refreshView() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            initChart();
        }
    }
}

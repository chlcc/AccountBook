package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
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
import com.sup2is.accountbook.databinding.FragmentStatisticsBinding;

import java.util.ArrayList;

public class StatisticsFragment extends BaseFragment {

    private static final String TAG = StatisticsFragment.class.getSimpleName();

    FragmentStatisticsBinding statisticsBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_statistics,container,false);
        statisticsBinding = DataBindingUtil.bind(view);

        statisticsBinding.pcContainer.setUsePercentValues(true);


        ArrayList<Entry> dataSet = new ArrayList<>();

        dataSet.add(new Entry(8,0));
        dataSet.add(new Entry(15,1));
        dataSet.add(new Entry(12,2));
        dataSet.add(new Entry(30,3));
        dataSet.add(new Entry(9,4));
        dataSet.add(new Entry(9,5));
        dataSet.add(new Entry(9,6));
        dataSet.add(new Entry(9,7));
        dataSet.add(new Entry(9,8));
        dataSet.add(new Entry(9,9));

        PieDataSet pieDataSet = new PieDataSet(dataSet,"statistic");

        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");
        xVals.add("23");
        xVals.add("53");
        xVals.add("15");
        xVals.add("asdf");
        xVals.add("zx");

        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(xVals, pieDataSet);
        data.setValueTextSize(15);

        statisticsBinding.pcContainer.setDrawHoleEnabled(true);
        statisticsBinding.pcContainer.setTransparentCircleRadius(32f);
        statisticsBinding.pcContainer.setHoleRadius(30f);
        statisticsBinding.pcContainer.setData(data);
        statisticsBinding.pcContainer.setDescription("");

        return view;
    }


    @Override
    public void refreshView() {

    }
}

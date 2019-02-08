package com.sup2is.accountbook.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.sup2is.accountbook.fragment.CalendarFragment;
import com.sup2is.accountbook.fragment.DailyListViewFragment;
import com.sup2is.accountbook.fragment.SettingsFragment;
import com.sup2is.accountbook.fragment.StatisticsFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    private DailyListViewFragment dailyListViewFragment;
    private CalendarFragment calendarFragment;
    private StatisticsFragment statisticsFragment;
    private SettingsFragment settingsFragment;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                dailyListViewFragment = new DailyListViewFragment();
                return dailyListViewFragment;
            case 1:
                calendarFragment = new CalendarFragment();
                return calendarFragment;
            case 2:
                statisticsFragment = new StatisticsFragment();
                return statisticsFragment;
            case 3:
                settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        Log.d("TAG" , object.toString());
        return super.getItemPosition(object);
    }

    public void refreshFragment(int position) {
        switch (position) {
            case 0:
                dailyListViewFragment.setUserVisibleHint(dailyListViewFragment.getUserVisibleHint());
                break;
            case 1:
                calendarFragment.setUserVisibleHint(calendarFragment.getUserVisibleHint());
                break;
            case 2:
                statisticsFragment.onResume();
                break;
            case 3:
                settingsFragment.onResume();
                break;
        }
    }
}

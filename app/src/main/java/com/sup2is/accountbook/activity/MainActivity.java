package com.sup2is.accountbook.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.adapter.TabPagerAdapter;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.databinding.ActivityMainBinding;
import com.sup2is.accountbook.databinding.LayoutActionbarBinding;
import com.sup2is.accountbook.handler.ActionbarHandler;
import com.sup2is.accountbook.util.GlobalDate;
import com.sup2is.accountbook.util.SharedPreferenceManager;

import java.util.Date;


public class MainActivity extends AppCompatActivity {


    private AccountBookApplication application;

    private SharedPreferenceManager pref;

    private ActivityMainBinding binding;

    private LayoutActionbarBinding actionbarBinding;

    private TabPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (AccountBookApplication) getApplication();
        pref = application.getSpm();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewInit();

        if(pref.getString(SharedPreferenceManager.USE_FIRST) == null) {
            //todo 첫 사용자 사용법 알림
            Toast.makeText(application, "첫사용자입니다", Toast.LENGTH_SHORT).show();

            // todo 사용자 알림서 전부 확인 시 다음부터 출력 X
            pref.putString(SharedPreferenceManager.USE_FIRST,"true");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void viewInit() {

        //custom actionbar
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        View customView = LayoutInflater.from(this).inflate(R.layout.layout_actionbar, null);
        actionbarBinding = DataBindingUtil.bind(customView);
        actionbarBinding.setHandler(new ActionbarHandler());
        actionBar.setCustomView(customView, params);

        Toolbar parent = (Toolbar) customView.getParent();
        parent.setPadding(0,0,0,0);
        parent.setContentInsetsAbsolute(0,0);

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        // gnb
        TabLayout tabLayout =  actionbarBinding.tlGnb;
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_daily_list));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_calendar));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_statistics));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_settings));

        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        binding.vpContainer.setAdapter(tabPagerAdapter);
        binding.vpContainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpContainer.setCurrentItem(tab.getPosition());
                tabPagerAdapter.refreshFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //global date
        GlobalDate globalDate = GlobalDate.getInstance();
        actionbarBinding.tvCalendar.setText(globalDate.getDate());

    }


    public void refresh() {
        tabPagerAdapter.notifyDataSetChanged();
    }
}

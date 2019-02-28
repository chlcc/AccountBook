package com.sup2is.accountbook.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.adapter.TabPagerAdapter;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBHelper;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.databinding.ActivityMainBinding;
import com.sup2is.accountbook.databinding.LayoutActionbarBinding;
import com.sup2is.accountbook.dialog.CustomDialog;
import com.sup2is.accountbook.handler.ActionbarHandler;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.DateBundle;
import com.sup2is.accountbook.util.CommaFormatter;
import com.sup2is.accountbook.util.GlobalDate;
import com.sup2is.accountbook.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private AccountBookApplication application;
    private SharedPreferenceManager spm;
    private ActivityMainBinding binding;
    private LayoutActionbarBinding actionbarBinding;
    private TabPagerAdapter tabPagerAdapter;
    private int tabPosition;
    private DBManager dbManager;
    private GlobalDate globalDate = GlobalDate.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (AccountBookApplication) getApplication();
        dbManager = application.getDbManager();

        spm = application.getSpm();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewInit();

        if (spm.getString(SharedPreferenceManager.USE_FIRST,null) == null) {
            //todo 첫 사용자 사용법 알림
            Toast.makeText(application, "첫사용자입니다", Toast.LENGTH_SHORT).show();

            // todo 사용자 알림서 전부 확인 시 다음부터 출력 X
            spm.putString(SharedPreferenceManager.USE_FIRST, "true");
        }

        if(spm.getString(SharedPreferenceManager.MONTH_GOAL + globalDate.getYear() + globalDate.getMonth(),null) == null) {
            CustomDialog.Builder builder = new CustomDialog.Builder();
            builder.setHint("이달의 목표 금액을 입력하세요!")
                    .setMaxLength(50)
                    .setType(DBHelper.MONTH_GOAL)
                    .setCommaTextWatcher(true)
                    .setTitle( globalDate.getYear() +"." + globalDate.getMonth()+" 월달의 목표 금액 설정");

            CustomDialog customDialog = new CustomDialog(this,builder);
            customDialog.show();
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

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View customView = LayoutInflater.from(this).inflate(R.layout.layout_actionbar, null);
        actionbarBinding = DataBindingUtil.bind(customView);
        actionbarBinding.setHandler(new ActionbarHandler());
        actionBar.setCustomView(customView, params);

        Toolbar parent = (Toolbar) customView.getParent();
        parent.setPadding(0, 0, 0, 0);
        parent.setContentInsetsAbsolute(0, 0);

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar)));

        // gnb
        TabLayout tabLayout = actionbarBinding.tlGnb;
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_daily_list));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_calendar));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_statistics));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.button_settings));

        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        binding.vpContainer.setOffscreenPageLimit(3);
        binding.vpContainer.setAdapter(tabPagerAdapter);
        binding.vpContainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpContainer.setCurrentItem(tab.getPosition());
                tabPosition = tab.getPosition();
                tabPagerAdapter.refreshFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        refreshActionBar();
    }

    public void refreshFragment() {
        tabPagerAdapter.refreshFragment(tabPosition);
    }


    public void setCalendarText(String date) {
        actionbarBinding.tvCalendar.setText(date);
    }


    public void refreshActionBar() {
        DateBundle dateBundle = new DateBundle(String.valueOf(globalDate.getYear()), String.valueOf(globalDate.getMonth()), String.valueOf(globalDate.getDay()),null,null,null,null);
//        ArrayList<Account> accounts = dbManager.selectByDate(dateBundle);

        long incoming = dbManager.selectByDateToTotalIncoming(dateBundle);
        long spending = dbManager.selectByDateToTotalSpending(dateBundle);

        actionbarBinding.tvGoalMoney.setText("");
        actionbarBinding.tvIncomingMoney.setText(CommaFormatter.comma(incoming) + "원");
        actionbarBinding.tvSpendingMoney.setText(CommaFormatter.comma(spending) + "원");

        if(spm.getString(SharedPreferenceManager.MONTH_GOAL + globalDate.getYear() + globalDate.getMonth() , null) != null) {
            actionbarBinding.tvGoalMoney.setText(CommaFormatter.comma(Long.parseLong(spm.getString(SharedPreferenceManager.MONTH_GOAL + globalDate.getYear() + globalDate.getMonth(),null)))+"원");
        }else {
            actionbarBinding.tvGoalMoney.setText("0원");
        }

        actionbarBinding.tvCalendar.setText(globalDate.getYearMonthToString());
    }
}

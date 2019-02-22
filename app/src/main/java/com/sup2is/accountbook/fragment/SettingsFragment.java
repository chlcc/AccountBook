package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.adapter.SettingsAdapter;
import com.sup2is.accountbook.databinding.FragmentSettingsBinding;
import com.sup2is.accountbook.model.Setting;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private FragmentSettingsBinding settingsBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        settingsBinding = DataBindingUtil.bind(view);

        ArrayList<Setting> settings = new ArrayList<>();

        String[] settingsTitleArray = getResources().getStringArray(R.array.settings_title_array);
        String[] settingsIconArray = getResources().getStringArray(R.array.settings_icon_array);
        String title;
        int resourceId;

        for (int i = 0; i < settingsTitleArray.length ; i ++) {

            title = settingsTitleArray[i];
            resourceId = getResources().getIdentifier(settingsIconArray[i],"drawable", getContext().getPackageName());
            settings.add(new Setting(resourceId, title));
        }

        SettingsAdapter settingsAdapter = new SettingsAdapter(getContext(),settings);
        settingsBinding.rvSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        settingsBinding.rvSettingList.setAdapter(settingsAdapter);
        return view;

    }
}

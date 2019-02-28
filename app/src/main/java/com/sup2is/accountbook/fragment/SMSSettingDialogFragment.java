package com.sup2is.accountbook.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBHelper;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.databinding.FragmentSettingSmsBinding;
import com.sup2is.accountbook.dialog.CustomDialog;
import com.sup2is.accountbook.util.SharedPreferenceManager;

import java.util.ArrayList;

public class SMSSettingDialogFragment extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private FragmentSettingSmsBinding settingSmsBinding;
    private AccountBookApplication application;


    private SharedPreferenceManager spm;
    private DBManager dbManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER| Gravity.BOTTOM);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.fragment_setting_sms,container,false);
        settingSmsBinding = DataBindingUtil.bind(view);
        application = (AccountBookApplication) getActivity().getApplication();
        spm = application.getSpm();
        dbManager = application.getDbManager();

        //db에서 폰넘버 불러오기
        ArrayList<String> phoneNumberList = new ArrayList<>();
        phoneNumberList.add("02-6241-1145");
        phoneNumberList.add("070-6241-1145");
        boolean smsOnOff = spm.getBoolean(SharedPreferenceManager.USE_SMS_ON_OFF , false);

        int resourceId ;
        if(smsOnOff) {
            resourceId = R.layout.layout_custom_list_text_item;
        }else {
            resourceId = R.layout.layout_custom_list_text_item_disable;
        }

        ArrayAdapter<String> phoneNumberAdapter = new ArrayAdapter<>(getContext(),resourceId,phoneNumberList);
        settingSmsBinding.lvSmsPhoneNumberList.setAdapter(phoneNumberAdapter);

        setPhoneNumberListStyle(smsOnOff);
        settingSmsBinding.sSmsOnOff.setChecked(smsOnOff);

        settingSmsBinding.ibAddPhoneNumber.setOnClickListener(this);
        settingSmsBinding.sSmsOnOff.setOnCheckedChangeListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ib_add_phone_number :
                CustomDialog.Builder builder = new CustomDialog.Builder();
                builder.setMaxLength(13)
                        .setType(DBHelper.ADD_PHONE_NUMBER)
                        .setHint("ex:) 02-1234-5678 or 070-1234-5678")
                        .setTitle("번호를 입력해주세요 (특수문자 포함)");
                CustomDialog dialog = new CustomDialog(getContext(),builder);
                dialog.show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        spm.putBoolean(SharedPreferenceManager.USE_SMS_ON_OFF,isChecked);
        setPhoneNumberListStyle(isChecked);
    }

    private void setPhoneNumberListStyle(boolean flag) {
        if(flag) {
            settingSmsBinding.tvAddPhoneNumber.setTextColor(getContext().getResources().getColor(R.color.black));
            settingSmsBinding.ibAddPhoneNumber.setEnabled(true);
            int count = settingSmsBinding.lvSmsPhoneNumberList.getChildCount();
            for(int i = 0 ; i < count ; i ++) {
                View v = settingSmsBinding.lvSmsPhoneNumberList.getChildAt(i);
                ((TextView)v).setTextColor(getContext().getResources().getColor(R.color.black));
            }
        }else {
            settingSmsBinding.tvAddPhoneNumber.setTextColor(getContext().getResources().getColor(R.color.darkGray));
            settingSmsBinding.ibAddPhoneNumber.setEnabled(false);
            int count = settingSmsBinding.lvSmsPhoneNumberList.getChildCount();
            for(int i = 0 ; i < count ; i ++) {
                View v = settingSmsBinding.lvSmsPhoneNumberList.getChildAt(i);
                ((TextView)v).setTextColor(getContext().getResources().getColor(R.color.darkGray));
            }
        }
    }

}


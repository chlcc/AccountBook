package com.sup2is.accountbook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.Objects;

public class SMSReceiver extends BroadcastReceiver {


    private static final String TAG = SMSReceiver.class.getSimpleName();

    private DBManager dbManager;
    private AccountBookApplication application;
    private SharedPreferenceManager spm;
    private ArrayList<String> list;




    @Override
    public void onReceive(Context context, Intent intent) {

        if(application == null || dbManager == null || spm == null) {
            application = ((AccountBookApplication)context.getApplicationContext());
            dbManager = application.getDbManager();
            spm = application.getSpm();
            list = dbManager.selectSmsPhoneNumberList();
        }

        if(spm.getBoolean(SharedPreferenceManager.USE_SMS_ON_OFF , false)) {

            if(intent.getAction().equals( "android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();
                SmsMessage[] messages = parseSMSMessage(bundle);


                if(messages.length > 0) {
                    String sender = messages[0].getOriginatingAddress();
                    String contents = messages[0].getMessageBody();
                    Log.d(TAG, "Sender :" + sender);
                    Log.d(TAG, "contents : " + contents);

                    //todo sender가 등록한 번호일때 sms 파싱해서 db에 인서트
                    if(list.contains(sender)) {



                    }
                }

            }
        }
    }

    private SmsMessage[] parseSMSMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i<objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }
}

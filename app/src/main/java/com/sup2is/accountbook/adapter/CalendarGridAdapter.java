package com.sup2is.accountbook.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.activity.MainActivity;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.util.CommaFormatter;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarGridAdapter extends BaseAdapter {


    private static final String TAG = "### "+CalendarGridAdapter.class.getSimpleName() + " : ";
    private ArrayList<Account> accounts;
    private ArrayList<String> dayList;
    private LayoutInflater inflater;
    private Context context;

    public CalendarGridAdapter(Context context, ArrayList<String> dayList, ArrayList<Account> accounts) {
        this.context = context;
        this.dayList = dayList;
        this.accounts = accounts;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dayList.size();
    }


    @Override
    public String getItem(int position) {
        return dayList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        Calendar currentCal = Calendar.getInstance();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_calendar_day_item, parent, false);
            holder = new ViewHolder();
            holder.tv_day = convertView.findViewById(R.id.tv_day);
            holder.tv_incoming = convertView.findViewById(R.id.tv_incoming);
            holder.tv_spending = convertView.findViewById(R.id.tv_spending);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        long spending = 0;
        long incoming = 0;

        String day = getItem(position);
        holder.tv_day.setText("" + day);

        if(!day.equals("")) {
            for (Account account : accounts) {
                if (account.getDateBundle().getDay().equals(day)) {
                    if (account.getMethod().equals("수입")) {
                        incoming += Long.parseLong(account.getMoney());
                    } else {
                        spending += Long.parseLong(account.getMoney());
                    }
                }
            }
            if (spending != 0) {
                holder.tv_spending.setText("-" + CommaFormatter.comma(spending));
            }

            if (incoming != 0) {
                holder.tv_incoming.setText("+" + CommaFormatter.comma(incoming));
            }

        }
        return convertView;
    }

    class ViewHolder {
        private TextView tv_day;
        private TextView tv_spending;
        private TextView tv_incoming;
    }

    public void updateList(ArrayList<String> dayList ,ArrayList<Account> accountList) {
        this.accounts = accountList;
        this.dayList = dayList;
        this.notifyDataSetChanged();
    }


}





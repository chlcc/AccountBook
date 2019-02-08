package com.sup2is.accountbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.model.Account;

import java.util.ArrayList;

public class DailyListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Account> accounts;
    private LayoutInflater inflater;

    private int currentDay;


    private final int TYPE_HEADER = 0;
    private final int TYPE_BODY = 1;
    private final int VIEW_COUNT = 2;

    public DailyListViewAdapter(Context context, ArrayList<Account> accounts) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.accounts = accounts;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if(currentDay == Integer.parseInt(accounts.get(position).getDateBundle().getDay())){
            return TYPE_BODY;
        }else {
            return TYPE_HEADER;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Account item;
        int view_type = getItemViewType(position);
        currentDay = Integer.parseInt(accounts.get(position).getDateBundle().getDay());

        if(convertView == null) {
            if (view_type == TYPE_HEADER) {
                convertView = inflater.inflate(R.layout.layout_custom_listview_header,parent,false);
            }else{
                convertView = inflater.inflate(R.layout.layout_custom_listview_body,parent,false);
            }
            viewHolder = new ViewHolder();
            viewHolder.tv_day = convertView.findViewById(R.id.tv_day);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_day_of_week = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.tv_incoming = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.tv_incoming_money = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.tv_spending = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.tv_spending_money = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.tv_total = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.tv_total_money = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.tv_group = convertView.findViewById(R.id.tv_group);

            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        item = accounts.get(position);

        if (view_type == TYPE_HEADER) {
            viewHolder.tv_day.setText(item.getDateBundle().getDay());
        }else {
            viewHolder.tv_time.setText("20:30");
        }


//        viewHolder.tv_date.setText(item.getContent());
//        viewHolder.group.setText(item.getGroup());
//        viewHolder.method.setText(item.getMethod());
//        viewHolder.time.setText(item.getDateBundle().getHour() + ":" + item.getDateBundle().getMinute());
//        viewHolder.tv_day_of_week.setText(item.getDateBundle().getDayOfWeek());
//        viewHolder.money.setText(item.getMoney());

        return convertView;
    }

    private class ViewHolder{

        private TextView tv_day;
        private TextView tv_date;
        private TextView tv_day_of_week;
        private TextView tv_incoming;
        private TextView tv_incoming_money;
        private TextView tv_spending;
        private TextView tv_spending_money;
        private TextView tv_total;
        private TextView tv_total_money;
        private TextView tv_group;
        private TextView tv_method;

        private TextView tv_time;

    }

    public void updateList(ArrayList<Account> newList) {
        this.accounts.clear();
        this.accounts = newList;
        this.notifyDataSetChanged();
    }
}

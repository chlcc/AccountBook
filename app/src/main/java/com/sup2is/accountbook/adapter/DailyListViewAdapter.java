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
import java.util.List;

public class DailyListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Account> accounts;
    private LayoutInflater inflater;

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


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Account item;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_custom_listview,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.day = convertView.findViewById(R.id.tv_day);
            viewHolder.content = convertView.findViewById(R.id.tv_content);
            viewHolder.group = convertView.findViewById(R.id.tv_group);
            viewHolder.method = convertView.findViewById(R.id.tv_method);
            viewHolder.time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_day_of_week = convertView.findViewById(R.id.tv_day_of_week);
            viewHolder.money = convertView.findViewById(R.id.tv_money);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        item = accounts.get(position);

        viewHolder.day.setText(item.getDateBundle().getDay());
        viewHolder.content.setText(item.getContent());
        viewHolder.group.setText(item.getGroup());
        viewHolder.method.setText(item.getMethod());
        viewHolder.time.setText(item.getDateBundle().getHour() + ":" + item.getDateBundle().getMinute());
        viewHolder.tv_day_of_week.setText(item.getDateBundle().getDayOfWeek());
        viewHolder.money.setText(item.getMoney());

        return convertView;
    }

    private class ViewHolder{

        private TextView day;
        private TextView time;
        private TextView tv_day_of_week;
        private TextView money;
        private TextView content;
        private TextView group;
        private TextView method;

    }

    public void updateList(ArrayList<Account> newList) {
        this.accounts.clear();
        this.accounts = newList;
        this.notifyDataSetChanged();
    }
}

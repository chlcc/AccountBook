package com.sup2is.accountbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.model.DailyListItem;

import java.util.ArrayList;

public class DailyListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DailyListItem> dailyListItems;
    private LayoutInflater inflater;

    public DailyListViewAdapter(Context context, ArrayList<DailyListItem> dailyListItems) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dailyListItems = dailyListItems;
    }

    @Override
    public int getCount() {
        return dailyListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        DailyListItem item;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_custom_listview,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.day = convertView.findViewById(R.id.tv_day);
            viewHolder.content = convertView.findViewById(R.id.tv_content);
            viewHolder.group = convertView.findViewById(R.id.tv_group);
            viewHolder.method = convertView.findViewById(R.id.tv_method);
            viewHolder.time = convertView.findViewById(R.id.tv_time);
            viewHolder.week = convertView.findViewById(R.id.tv_week);
            viewHolder.money = convertView.findViewById(R.id.tv_money);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        item = dailyListItems.get(position);

        viewHolder.day.setText(item.getDay());
        viewHolder.content.setText(item.getContent());
        viewHolder.group.setText(item.getGroup());
        viewHolder.method.setText(item.getMethod());
        viewHolder.time.setText(item.getTime());
        viewHolder.week.setText(item.getWeek());
        viewHolder.money.setText(item.getMoney());

        return convertView;
    }

    private class ViewHolder{

        private TextView day;
        private TextView time;
        private TextView week;
        private TextView money;
        private TextView content;
        private TextView group;
        private TextView method;

    }
}

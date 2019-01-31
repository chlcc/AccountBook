package com.sup2is.accountbook.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.List;

public class CalendarGridAdapter extends BaseAdapter {


    private List<String> dayList;
    private LayoutInflater inflater;
    GlobalDate globalDate;

    public void setDayList(List<String> dayList) {
        this.dayList = dayList;
    }

    public CalendarGridAdapter(Context context, List<String> dayList) {
        this.dayList = dayList;
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

        holder.tv_day.setText("" + getItem(position));
//        holder.tv_incoming.setText("" + getItem(position));
//        holder.tv_spending.setText("" + getItem(position));


        //해당 날짜 텍스트 컬러,배경 변경

        globalDate = GlobalDate.getInstance();

        int today = globalDate.getToday();

        if (String.valueOf(today).equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
            holder.tv_day.setTypeface(null, Typeface.BOLD);
        }

        return convertView;
    }

    class ViewHolder {

        private TextView tv_day;
        private TextView tv_spending;
        private TextView tv_incoming;

    }


}





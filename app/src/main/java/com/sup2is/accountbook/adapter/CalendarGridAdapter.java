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
import com.sup2is.accountbook.model.CalendarItem;
import com.sup2is.accountbook.util.CommaFormatter;
import com.sup2is.accountbook.util.GlobalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarGridAdapter extends BaseAdapter {


    private static final String TAG = "### "+CalendarGridAdapter.class.getSimpleName() + " : ";
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<CalendarItem> calendarItems;

    public CalendarGridAdapter(Context context,ArrayList<CalendarItem> calendarItems ) {
        this.context = context;
        this.calendarItems = calendarItems;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return calendarItems.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarItems.get(position);
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

        CalendarItem calendarItem = (CalendarItem) getItem(position);
        holder.tv_day.setText("" + calendarItem.getDay());

        if(calendarItem.getSpendingMoney() != 0) {
            holder.tv_spending.setText("-" + CommaFormatter.comma(calendarItem.getSpendingMoney()));
        }else {
            holder.tv_spending.setText("");
        }

        if(calendarItem.getIncomingMoney() != 0) {
            holder.tv_incoming.setText("+" + CommaFormatter.comma(calendarItem.getIncomingMoney()));
        }else {
            holder.tv_incoming.setText("");
        }
        return convertView;
    }

    class ViewHolder {
        private TextView tv_day;
        private TextView tv_spending;
        private TextView tv_incoming;
    }

    public void updateList(ArrayList<CalendarItem> calendarItems) {
        this.calendarItems.clear();
        this.calendarItems = calendarItems;
        this.notifyDataSetChanged();
    }
}





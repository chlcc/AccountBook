package com.sup2is.accountbook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.model.Account;

import java.util.ArrayList;

public class DailyRecycleViewAdapter extends RecyclerView.Adapter<DailyRecycleViewAdapter.BaseViewHolder> {


    private final String TAG =   "###" + DailyRecycleViewAdapter.class.getSimpleName() + " : ";

    private Context context;
    private ArrayList<Account> accounts;


    public DailyRecycleViewAdapter(Context context ,ArrayList<Account> accounts) {
        this.context = context;
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaseViewHolder viewHolder = null;
        View view;

        switch (viewType) {
            case 0 :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_listview_header,parent,false);
                viewHolder = new HeaderViewHolder(view);
                break;
            case 1 :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_listview_body,parent,false);
                viewHolder = new BodyViewHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {

        Account account = accounts.get(position);
        if (baseViewHolder instanceof BodyViewHolder) {

            Log.d(TAG , "viewholder type is body");
            Log.d(TAG , "item day is " + account.getDateBundle().getDay());
            BodyViewHolder bodyViewHolder = (BodyViewHolder) baseViewHolder;
            bodyViewHolder.tv_time.setText(account.getDateBundle().getHour() + ":" + account.getDateBundle().getMinute());
            bodyViewHolder.tv_spending.setText(account.getSpending());
            bodyViewHolder.tv_group.setText(account.getGroup());
            bodyViewHolder.tv_money.setText(account.getMoney());
            bodyViewHolder.tv_content.setText(account.getContent());
        }

        if (baseViewHolder instanceof HeaderViewHolder) {
            Log.d(TAG , "viewholder type is header");
            Log.d(TAG , "item day is " + account.getDateBundle().getDay());
            long incoming = 0;
            long spending = 0;
            int currentDay = Integer.parseInt(account.getDateBundle().getDay());

            for (Account temp : accounts) {
                if (currentDay == Integer.parseInt(temp.getDateBundle().getDay())) {
                    if (temp.getMethod().equals("수입")) {
                        incoming += Long.parseLong(temp.getMoney());
                    }

                    if (temp.getMethod().equals("지출")) {
                        spending += Long.parseLong(temp.getMoney());
                    }
                }
            }

            HeaderViewHolder headerViewHolder = (HeaderViewHolder) baseViewHolder;
            headerViewHolder.tv_day.setText(account.getDateBundle().getDay());
            headerViewHolder.tv_date.setText(account.getDateBundle().getYear().substring(2) +"."+ account.getDateBundle().getMonth());
            headerViewHolder.tv_day_of_week.setText(account.getDateBundle().getDayOfWeek());
            headerViewHolder.tv_spending_money.setText(spending +"");
            headerViewHolder.tv_incoming_money.setText(incoming +"");
            headerViewHolder.tv_total_money.setText(incoming - spending + "");

            if((incoming - spending) > 0) {
                headerViewHolder.tv_total_money.setTextColor(context.getResources().getColor(R.color.incoming));
            }else {
                headerViewHolder.tv_total_money.setTextColor(context.getResources().getColor(R.color.spending));
            }
            headerViewHolder.tv_time.setText(account.getDateBundle().getHour() + ":" + account.getDateBundle().getMinute());
            headerViewHolder.tv_spending.setText(account.getSpending());
            headerViewHolder.tv_group.setText(account.getGroup());
            headerViewHolder.tv_money.setText(account.getMoney());
            headerViewHolder.tv_content.setText(account.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(accounts.get(position).getType());
    }

    abstract class  BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends  BaseViewHolder {

        private TextView tv_day;
        private TextView tv_date;
        private TextView tv_day_of_week;
        private TextView tv_incoming_money;
        private TextView tv_spending_money;
        private TextView tv_total_money;
        private TextView tv_time;
        private TextView tv_spending;
        private TextView tv_group;
        private TextView tv_money;
        private TextView tv_content;


        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_day = itemView.findViewById(R.id.tv_day);
            this.tv_date = itemView.findViewById(R.id.tv_date);
            this.tv_day_of_week = itemView.findViewById(R.id.tv_day_of_week);
            this.tv_incoming_money = itemView.findViewById(R.id.tv_incoming_money);
            this.tv_spending_money = itemView.findViewById(R.id.tv_spending_money);
            this.tv_total_money = itemView.findViewById(R.id.tv_total_money);
            this.tv_time = itemView.findViewById(R.id.tv_time);
            this.tv_spending = itemView.findViewById(R.id.tv_spending);
            this.tv_group = itemView.findViewById(R.id.tv_group);
            this.tv_money = itemView.findViewById(R.id.tv_money);
            this.tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    class BodyViewHolder extends  BaseViewHolder {

        private TextView tv_time;
        private TextView tv_spending;
        private TextView tv_group;
        private TextView tv_money;
        private TextView tv_content;

        public BodyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_time = itemView.findViewById(R.id.tv_time);
            this.tv_spending = itemView.findViewById(R.id.tv_spending);
            this.tv_group = itemView.findViewById(R.id.tv_group);
            this.tv_money = itemView.findViewById(R.id.tv_money);
            this.tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    public void updateList(ArrayList<Account> newList) {
        this.accounts.clear();
        this.accounts = newList;
        this.notifyDataSetChanged();
    }
}

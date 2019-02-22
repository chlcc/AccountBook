package com.sup2is.accountbook.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.activity.MainActivity;
import com.sup2is.accountbook.fragment.InputFormDialogFragment;
import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.util.CommaFormatter;

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
                view.setOnLongClickListener(listener);
                viewHolder = new HeaderViewHolder(view);
                break;
            case 1 :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_listview_body,parent,false);
                view.setOnLongClickListener(listener);
                viewHolder = new BodyViewHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {

        Account account = accounts.get(position);
        if (baseViewHolder instanceof BodyViewHolder) {

            BodyViewHolder bodyViewHolder = (BodyViewHolder) baseViewHolder;
            bodyViewHolder.tv_id.setText(account.getIdx() + "");
            bodyViewHolder.tv_time.setText(account.getDateBundle().getHour() + ":" + account.getDateBundle().getMinute());
            bodyViewHolder.tv_spending.setText(account.getSpending());
            bodyViewHolder.tv_group.setText(account.getGroup());
            bodyViewHolder.tv_money.setText(CommaFormatter.comma(Long.parseLong(account.getMoney())) + " 원");
            bodyViewHolder.tv_content.setText(account.getContent());
            if(account.getMethod().equals("수입")) {
                bodyViewHolder.tv_money.setTextColor(context.getResources().getColor(R.color.incoming));
            }else {
                bodyViewHolder.tv_money.setTextColor(context.getResources().getColor(R.color.spending));
            }
        }

        if (baseViewHolder instanceof HeaderViewHolder) {
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
            headerViewHolder.tv_id.setText(account.getIdx() + "");
            headerViewHolder.tv_day.setText(account.getDateBundle().getDay());
            headerViewHolder.tv_date.setText(account.getDateBundle().getYear().substring(2) +"."+ account.getDateBundle().getMonth());
            headerViewHolder.tv_day_of_week.setText(account.getDateBundle().getDayOfWeek());
            headerViewHolder.tv_spending_money.setText(CommaFormatter.comma(spending) + " 원");
            headerViewHolder.tv_incoming_money.setText(CommaFormatter.comma(incoming) + " 원");
            headerViewHolder.tv_total_money.setText(CommaFormatter.comma(incoming - spending) + " 원");

            if((incoming - spending) > 0) {
                headerViewHolder.tv_total_money.setTextColor(context.getResources().getColor(R.color.incoming));
            }else {
                headerViewHolder.tv_total_money.setTextColor(context.getResources().getColor(R.color.spending));
            }
            headerViewHolder.tv_time.setText(account.getDateBundle().getHour() + ":" + account.getDateBundle().getMinute());
            headerViewHolder.tv_spending.setText(account.getSpending());
            headerViewHolder.tv_group.setText(account.getGroup());


            //수입일 경우 spending 에 incoming
            if(account.getMethod().equals("수입")) {
                headerViewHolder.tv_money.setTextColor(context.getResources().getColor(R.color.incoming));
            }else {
                headerViewHolder.tv_money.setTextColor(context.getResources().getColor(R.color.spending));
            }

            headerViewHolder.tv_money.setText(CommaFormatter.comma(Long.parseLong(account.getMoney())) + " 원");
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

    public void clearList() {
        this.accounts.clear();
        this.notifyDataSetChanged();
    }

    abstract class  BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends  BaseViewHolder {

        private TextView tv_id;
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
            this.tv_id = itemView.findViewById(R.id.tv_id);
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

        private TextView tv_id;
        private TextView tv_time;
        private TextView tv_spending;
        private TextView tv_group;
        private TextView tv_money;
        private TextView tv_content;

        public BodyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_id = itemView.findViewById(R.id.tv_id);
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

    private final View.OnLongClickListener listener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int idx = Integer.parseInt(((TextView)v.findViewById(R.id.tv_id)).getText().toString());
            FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();
            InputFormDialogFragment inputFormDialogFragment = new InputFormDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("idx",idx);
            inputFormDialogFragment.setArguments(bundle);
            inputFormDialogFragment.show(fm,"input");
            return true;
        }
    };

}


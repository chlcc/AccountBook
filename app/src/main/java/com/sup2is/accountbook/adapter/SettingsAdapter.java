package com.sup2is.accountbook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.model.Setting;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private ArrayList<Setting> settings;

    public SettingsAdapter(Context context , ArrayList<Setting> settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_setting,parent,false);
        BaseViewHolder viewHolder = new SettingsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {

        Setting setting = settings.get(position);
        if(baseViewHolder instanceof SettingsViewHolder) {
            SettingsViewHolder settingsViewHolder = (SettingsViewHolder) baseViewHolder;
            settingsViewHolder.tv_title.setText(setting.getTitle());
//            settingsViewHolder.iv_icon.setImageResource(setting.getIconResource());
        }
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    private class SettingsViewHolder extends BaseViewHolder {

        private ImageView iv_icon;
        private TextView tv_title;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_icon = itemView.findViewById(R.id.iv_icon);
            this.tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

}

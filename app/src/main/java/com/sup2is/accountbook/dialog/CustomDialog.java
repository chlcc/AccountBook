package com.sup2is.accountbook.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.databinding.LayoutCustomDialogBinding;

public class CustomDialog extends Dialog implements View.OnClickListener {


    private LayoutCustomDialogBinding customDialogBinding;

    private final Context context;

    private final String hint;
    private final String title;

    public CustomDialog(@NonNull Context context, Builder builder) {
        super(context);
        this.context = context;
        this.hint = builder.hint;
        this.title = builder.title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog,null,false);

        setContentView(view);
        customDialogBinding = DataBindingUtil.bind(view);
        customDialogBinding.tvTitle.setText(title);
        customDialogBinding.etInput.setHint(hint);
        customDialogBinding.btnCancel.setOnClickListener(this);
        customDialogBinding.btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel :
                dismiss();
                break;

            case R.id.btn_ok :
                //todo db에 저장하기 !
                dismiss();
                break;
        }
    }

    public static class Builder{
        private String hint;
        private String title;

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
    }
}

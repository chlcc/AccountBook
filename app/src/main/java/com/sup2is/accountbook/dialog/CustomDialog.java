package com.sup2is.accountbook.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.activity.MainActivity;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.database.DBHelper;
import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.databinding.LayoutCustomDialogBinding;
import com.sup2is.accountbook.fragment.InputFormDialogFragment;
import com.sup2is.accountbook.model.Category;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private AccountBookApplication application;

    private LayoutCustomDialogBinding customDialogBinding;

    private DBManager dbManager;

    private final Context context;

    private final String hint;
    private final String title;
    private final int type;
    private final int maxLength;

    public CustomDialog(@NonNull Context context, Builder builder) {
        super(context);
        this.context = context;
        this.hint = builder.hint;
        this.title = builder.title;
        this.type = builder.type;
        this.maxLength = builder.maxLength;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (AccountBookApplication) context.getApplicationContext();
        dbManager = application.getDbManager();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog,null,false);

        setContentView(view);
        customDialogBinding = DataBindingUtil.bind(view);
        customDialogBinding.tvTitle.setText(title);
        customDialogBinding.etInput.setHint(hint);

        if(maxLength != 0) {
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(maxLength);
            customDialogBinding.etInput.setFilters(filters);
        }

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
                String value = customDialogBinding.etInput.getText().toString();


                //InputForm에서 분류관련 spinner 업데이트
                if(type == DBHelper.METHOD_TYPE || type == DBHelper.GROUP_TYPE || type == DBHelper.SPENDING_TYPE|| type == DBHelper.INCOMING_TYPE) {
                    Category category = new Category(dbManager.getNextAutoIncrement(DBHelper.TBL_CATEGORY),dbManager.getNextCategorySeq(type),type,value);
                    dbManager.insertCategory(category);
                    FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();
                    InputFormDialogFragment inputForm = (InputFormDialogFragment) fm.findFragmentByTag("input");
                    inputForm.refreshSpinnerAdapter(type,value);
                }

                if(type == DBHelper.ADD_PHONE_NUMBER) {
                    Toast.makeText(getContext(), "폰번호 db에 저장" + value, Toast.LENGTH_SHORT).show();
                }

                dismiss();
                break;
        }
    }

    public static class Builder{
        private String hint;
        private String title;
        private int type;
        private int maxLength;

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }
    }
}

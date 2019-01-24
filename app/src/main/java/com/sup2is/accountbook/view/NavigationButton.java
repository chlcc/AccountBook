package com.sup2is.accountbook.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.databinding.LayoutActionbarNaviBtnBinding;

public class NavigationButton extends RelativeLayout {

    private LayoutActionbarNaviBtnBinding binding;

    public NavigationButton(Context context) {
        super(context);
        initView();
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);

    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_actionbar_navi_btn,this,false);
        addView(view);
        binding = DataBindingUtil.bind(view);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Custom_Navigation_Bar);
        setTypeArray(typedArray);

    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Custom_Navigation_Bar,defStyle,0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        int iv_image_id = typedArray.getResourceId(R.styleable.Custom_Navigation_Bar_src, 0);
        binding.ivImage.setImageResource(iv_image_id);
        String tv_btn_name = typedArray.getString(R.styleable.Custom_Navigation_Bar_text);
        binding.tvBtnName.setText(tv_btn_name);
        typedArray.recycle();
    }

}

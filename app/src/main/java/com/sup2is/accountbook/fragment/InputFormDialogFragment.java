package com.sup2is.accountbook.fragment;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.databinding.FragmentInputFormBinding;

public class InputFormDialogFragment extends DialogFragment {


    FragmentInputFormBinding inputFormBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER| Gravity.BOTTOM);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_input_form,container,false);
        inputFormBinding = DataBindingUtil.bind(view);

        return view;
    }
}

package com.example.youome;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class FragmentSignUp2Tele extends BottomSheetDialogFragment {

    TextView skt,lg,kt,skt_e,lg_e,kt_e;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signuptele, container, false);
        TelecomClickListener tcl = new TelecomClickListener(this);

        skt = (TextView)view.findViewById(R.id.t_skt);
        skt.setOnClickListener(tcl);
        kt = (TextView)view.findViewById(R.id.t_kt);
        kt.setOnClickListener(tcl);
        lg = (TextView)view.findViewById(R.id.t_lg);
        lg.setOnClickListener(tcl);
        skt_e = (TextView)view.findViewById(R.id.t_skt_e);
        skt_e.setOnClickListener(tcl);
        kt_e = (TextView)view.findViewById(R.id.t_kt_e);
        kt_e.setOnClickListener(tcl);
        lg_e = (TextView)view.findViewById(R.id.t_lg_e);
        lg_e.setOnClickListener(tcl);

        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            // Add a remove transaction before each add transaction to prevent continuous add
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            // The same instance will use different tags will be an exception, capture here
            e.printStackTrace();
        }
    }

    private class TelecomClickListener implements View.OnClickListener{
        FragmentSignUp2Tele parent;
        public TelecomClickListener(FragmentSignUp2Tele parent) {
            this.parent = parent;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.t_skt:
                    ((ActivitySignUp)getActivity()).textInputLayout.setPrefixText("SKT  ");
                    parent.dismiss();
                    break;
                case R.id.t_kt:
                    ((ActivitySignUp)getActivity()).textInputLayout.setPrefixText("KT  ");
                    parent.dismiss();
                    break;
                case R.id.t_lg:
                    ((ActivitySignUp)getActivity()).textInputLayout.setPrefixText("LG U+  ");
                    parent.dismiss();
                    break;
                case R.id.t_skt_e:
                    ((ActivitySignUp)getActivity()).textInputLayout.setPrefixText("SKT 알뜰폰  ");
                    parent.dismiss();
                    break;
                case R.id.t_kt_e:
                    ((ActivitySignUp)getActivity()).textInputLayout.setPrefixText("KT 알뜰폰  ");
                    parent.dismiss();
                    break;
                case R.id.t_lg_e:
                    ((ActivitySignUp)getActivity()).textInputLayout.setPrefixText("LG U+ 알뜰폰  ");
                    parent.dismiss();
                    break;
            }
        }
    }

}

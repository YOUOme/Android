package com.example.youome;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youome.loanholder.ActivityLoan_v2;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class FragmentBotPayment extends BottomSheetDialogFragment {

    TextView d1,d2,d3;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) { return super.onCreateDialog(savedInstanceState); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bot_payment, container, false);

        d1 = (TextView)view.findViewById(R.id.bt_firstday);
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityLoan_v2)getActivity()).tx_payment.setText("매월 1일");
                FragmentBotPayment.this.dismiss();
            }
        });
        d2 = (TextView)view.findViewById(R.id.bt_15th);
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityLoan_v2)getActivity()).tx_payment.setText("매월 15일");
                FragmentBotPayment.this.dismiss();
            }
        });
        d3 = (TextView)view.findViewById(R.id.bt_lastday);
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityLoan_v2)getActivity()).tx_payment.setText("매월 말일");
                FragmentBotPayment.this.dismiss();
            }
        });


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
}
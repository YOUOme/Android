package com.example.youome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTab1Home extends Fragment {
    private TextView bt_omypay,tx_OmyPay,bt_loan;
    private int myMoney = 47870;        // db get
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount, null);

        bt_omypay = (TextView) view.findViewById(R.id.bt_omypay);
        bt_omypay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityOmypay.class);
                intent.putExtra("money",myMoney);
                startActivity(intent);
            }
        });
        bt_loan = (TextView)view.findViewById(R.id.bt_loan);
        bt_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityTransfer.class);
                intent.putExtra("money",myMoney);
                startActivity(intent);
            }
        });

        tx_OmyPay = (TextView) view.findViewById(R.id.tx_omypay);
        String s = null;
        try { s = String.format("%,d", myMoney); } catch (NumberFormatException e) { }
        tx_OmyPay.setText(s);


        return view;
    }
}

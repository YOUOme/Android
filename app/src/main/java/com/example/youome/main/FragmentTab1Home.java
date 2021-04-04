package com.example.youome.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youome.ActivityOmypay;
import com.example.youome.ActivityTransfer;
import com.example.youome.R;
import com.example.youome.loanholder.ActivityAddLoan;

public class FragmentTab1Home extends Fragment {
    private TextView bt_omypay,tx_OmyPay,bt_loan,bt_lend1;
    private ImageView bt_lend2;
    private int myMoney = 500000;        // db get
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, null);
        bt_lend1 = (TextView)view.findViewById(R.id.bt_lend);
        bt_lend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityAddLoan.class);
                startActivity(intent);
            }
        });

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
        tx_OmyPay.setText(s+" 원");


        ArrayList<ItemTab2> list2 = new ArrayList<>();
        list2.add(new ItemTab2(1,"김오미","20.07.29 수","10,000원/20,000원"));
        list2.add(new ItemTab2(1,"이오미","20.08.30 일","100,000원/200,000원"));
        list2.add(new ItemTab2(2,"박오미","20.09.01 화","10,000원/20,000원"));
        list2.add(new ItemTab2(2,"윤오미","20.09.23 수","10,000원/20,000원"));
        list2.add(new ItemTab2(2,"서오미","20.10.01 목","100,000원/20,000원"));

        RecyclerView recyclerView2 = view.findViewById(R.id.recycler1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setHorizontalScrollBarEnabled(false);

        AdapterTab2 adapter2 = new AdapterTab2(getContext(),list2,0,2);
        recyclerView2.setAdapter(adapter2);

        return view;
    }
}

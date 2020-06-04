package com.example.youome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3Repay extends Fragment {
    ListView repayListView, completeRepayListView;
    AdapterFragItem adapter1,adapter2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repay,null);

        adapter1 = new AdapterFragItem(R.color.youome_background);
        adapter2 = new AdapterFragItem();

        //dummy data
        adapter1.addItem("김오미","20.01.04 토","20000원");
        adapter1.addItem("김오미","20.01.04 토","20000원");

        adapter2.addItem("김오미","20.01.04 토","10000원");
        adapter2.addItem("김오미","20.01.04 토","10000원");
        adapter2.addItem("김오미","20.01.04 토","10000원");
        adapter2.addItem("김오미","20.01.04 토","10000원");
        adapter2.addItem("김오미","20.01.04 토","10000원");
        adapter2.addItem("김오미","20.01.04 토","10000원");
        adapter2.addItem("김오미","20.01.04 토","10000원");

        repayListView = (ListView)view.findViewById(R.id.repay_list);
        repayListView.setAdapter(adapter1);
        completeRepayListView = (ListView)view.findViewById(R.id.complete_repay_list);
        completeRepayListView.setAdapter(adapter2);

        return view;
    }
}

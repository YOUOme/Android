package com.example.youome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment2Loan extends Fragment{
    TextView bt_addlend;
    //ListView lendListView, completeLendListView;
    //AdapterFragItem adapter1,adapter2;
    EditText et_search;
    ImageView bt_loan_search;

    //ArrayList<AdapterFragItem.ItemData> originalData1,originalData2;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan,null);
        bt_addlend = (TextView)view.findViewById(R.id.bt_addlend);
        bt_addlend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityAddLoan.class);
                startActivity(intent);
            }
        });

        //dummy
        ArrayList<ItemTrade> list = new ArrayList<>();
        list.add(new ItemTrade(1,"김오미","20.07.29 수","10,000원/20,000원"));
        list.add(new ItemTrade(1,"김오미","20.07.10 월","상환완료/30,000원"));
        list.add(new ItemTrade(1,"이오미","20.01.04 토","상환완료/10,000원"));
        list.add(new ItemTrade(2,"이오미","20.01.04 토","상환완료/10,000원"));
        list.add(new ItemTrade(2,"최오미","20.01.04 토","상환완료/10,000원"));
        list.add(new ItemTrade(2,"박오미","20.01.04 토","상환완료/10,000원"));
        list.add(new ItemTrade(1,"배오미","20.01.04 토","상환완료/10,000원"));
        list.add(new ItemTrade(1,"박오미","20.01.04 토","상환완료/10,000원"));

        RecyclerView recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterTrade1 adapter = new AdapterTrade1(getContext(),list,0,1);
        recyclerView.setAdapter(adapter);

        ArrayList<ItemTrade> list2 = new ArrayList<>();
        list2.add(new ItemTrade(1,"김오미","20.07.29 수","10,000원/20,000원"));
        list2.add(new ItemTrade(1,"이오미","20.08.30 일","100,000원/200,000원"));
        list2.add(new ItemTrade(2,"박오미","20.09.01 화","10,000원/20,000원"));
        list2.add(new ItemTrade(2,"윤오미","20.09.23 수","10,000원/20,000원"));
        list2.add(new ItemTrade(2,"서오미","20.10.01 목","100,000원/20,000원"));

        RecyclerView recyclerView2 = view.findViewById(R.id.recycler2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterTrade1 adapter2 = new AdapterTrade1(getContext(),list2,R.color.youome_background,1);
        recyclerView2.setAdapter(adapter2);



        bt_loan_search = (ImageView)view.findViewById(R.id.bt_loan_search);
        bt_loan_search.setOnClickListener(new searchClickListener());
        et_search = (EditText)view.findViewById(R.id.loan_search);

        return view;
    }

    private class searchClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

        }
    }
}

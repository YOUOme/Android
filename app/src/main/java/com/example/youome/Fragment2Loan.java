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

public class Fragment2Loan extends Fragment{
    TextView bt_addlend;
    ListView lendListView, completeLendListView;
    AdapterFragItem adapter1,adapter2;
    EditText et_search;
    ImageView bt_loan_search;

    ArrayList<AdapterFragItem.ItemData> originalData1,saveArrayList2;

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
        adapter1 = new AdapterFragItem(getContext(),R.color.youome_background,1);
        adapter2 = new AdapterFragItem(getContext(),1);

        originalData1 = new ArrayList<AdapterFragItem.ItemData>();
        saveArrayList2 = new ArrayList<AdapterFragItem.ItemData>();

        bt_loan_search = (ImageView)view.findViewById(R.id.bt_loan_search);
        bt_loan_search.setOnClickListener(new searchClickListener());

        //dummy data
        adapter1.addItem("김오미","20.01.04 토","20000원");
        adapter1.addItem("박오미","20.01.04 토","20000원");

        adapter2.addItem("조오미","20.01.04 토","10000원");
        adapter2.addItem("나오미","20.01.04 토","10000원");
        adapter2.addItem("이오미","20.01.04 토","10000원");
        adapter2.addItem("최오미","20.01.04 토","10000원");
        adapter2.addItem("박오미","20.01.04 토","10000원");
        adapter2.addItem("배오미","20.01.04 토","10000원");
        adapter2.addItem("박오미","20.01.04 토","10000원");

        lendListView = (ListView)view.findViewById(R.id.lend_list);
        lendListView.setAdapter(adapter1);
        completeLendListView = (ListView)view.findViewById(R.id.complete_lend_list);
        completeLendListView.setAdapter(adapter2);

        et_search = (EditText)view.findViewById(R.id.loan_search);

        originalData1.addAll(adapter1.getArrayList());
        return view;
    }

    private class searchClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String searchText;
            ArrayList<AdapterFragItem.ItemData> searchedData = new ArrayList<AdapterFragItem.ItemData>();
            int length;

            if(!et_search.getText().toString().isEmpty()) {
                searchText = et_search.getText().toString();
                length = searchText.length();

                for(int i=0;i<originalData1.size();i++)                // 매우 리소스 비효율적.(수정요망)
                    if (searchText.equals(originalData1.get(i).getName().substring(0, length)))
                        searchedData.add(originalData1.get(i));

                adapter1.setArrayList(searchedData);
                adapter1.notifyDataSetChanged();
            }
            else {
                adapter1.setArrayList(originalData1);
                adapter1.notifyDataSetChanged();
            }
        }
    }
}

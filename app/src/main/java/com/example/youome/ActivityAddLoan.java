package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityAddLoan extends AppCompatActivity {
    ListView recentLendListView;
    AdapterLoanItem adapter;
    EditText et_recent_search;
    TextView tx_recent;

    String myName;
    int money = 40000; // 잔액을 전달받아야함.

    ArrayList<AdapterLoanItem.ItemData> originalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        originalData = new ArrayList<AdapterLoanItem.ItemData>();
        adapter = new AdapterLoanItem(getSupportFragmentManager());
        //dummy data
        adapter.addItem("김오미","010-1111-1111","유오미 A등급 | 나이스 3등급");
        adapter.addItem("나오미","010-1111-1111","유오미 A등급 | 나이스 1등급");
        adapter.addItem("박오미","010-1111-1111","유오미 C등급 | 나이스 5등급");
        adapter.addItem("이오미","010-1111-1111","유오미 A등급 | 나이스 3등급");
        adapter.addItem("조오미","010-1111-1111","유오미 A등급 | 나이스 3등급");
        adapter.addItem("윤오미","010-1111-1111","유오미 A등급 | 나이스 3등급");
        adapter.addItem("최오미","010-1111-1111","유오미 A등급 | 나이스 3등급");
        adapter.addItem("고오미","010-1111-1111","유오미 A등급 | 나이스 3등급");


        recentLendListView = (ListView)findViewById(R.id.recent_lend);
        recentLendListView.setAdapter(adapter);

        et_recent_search = (EditText)findViewById(R.id.et_recent_search);
        tx_recent = (TextView)findViewById(R.id.tx_recent);

        originalData.addAll(adapter.getArrayList());
    }

    public void onRecentSearchClick(View view){
        String searchText;
        ArrayList<AdapterLoanItem.ItemData> searchedData = new ArrayList<AdapterLoanItem.ItemData>();
        int length;

        if(!et_recent_search.getText().toString().isEmpty()) {
            tx_recent.setText("검색 결과");
            searchText = et_recent_search.getText().toString();
            length = searchText.length();

            for(AdapterLoanItem.ItemData data : originalData){
                if(searchText.equals(data.getName().substring(0,length)))
                    searchedData.add(data);
            }

            adapter.setArrayList(searchedData);
            adapter.notifyDataSetChanged();
        }
        else {
            tx_recent.setText("최근 거래");
            adapter.setArrayList(originalData);
            adapter.notifyDataSetChanged();
        }
    }

}
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

    ArrayList<AdapterLoanItem.ItemData> saveArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        saveArrayList = new ArrayList<AdapterLoanItem.ItemData>();
        adapter = new AdapterLoanItem(getSupportFragmentManager());
        //dummy data
        adapter.addItem("김오미","010-1111-1111","신용도 AAA");
        adapter.addItem("나오미","010-1111-1111","신용도 AAA");
        adapter.addItem("박오미","010-1111-1111","신용도 AAA");
        adapter.addItem("이오미","010-1111-1111","신용도 AAA");
        adapter.addItem("조오미","010-1111-1111","신용도 AAA");
        adapter.addItem("윤오미","010-1111-1111","신용도 AAA");
        adapter.addItem("최오미","010-1111-1111","신용도 AAA");
        adapter.addItem("고오미","010-1111-1111","신용도 AAA");


        recentLendListView = (ListView)findViewById(R.id.recent_lend);
        recentLendListView.setAdapter(adapter);

        et_recent_search = (EditText)findViewById(R.id.et_recent_search);
        tx_recent = (TextView)findViewById(R.id.tx_recent);
    }

    public void onRecentSearchClick(View view){
        String searchText;
        saveArrayList.addAll(adapter.getArrayList()); // deep copy
        int length,size;

        if(!et_recent_search.getText().toString().isEmpty()) {
            tx_recent.setText("검색 결과");
            searchText = et_recent_search.getText().toString();
            length = searchText.length();

            size = adapter.getArrayList().size();
            for(int i=0;i<size;i++) {                  // 매우 리소스 비효율적.(수정요망)
                if (!searchText.equals(adapter.getArrayList().get(i).getName().substring(0, length))) {
                    adapter.getArrayList().remove(i);
                    i--;size--;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else {
            tx_recent.setText("최근 거래");
            adapter.setArrayList(saveArrayList);
            adapter.notifyDataSetChanged();
        }
    }

}
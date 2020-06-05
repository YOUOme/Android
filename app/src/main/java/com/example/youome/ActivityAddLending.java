package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

public class ActivityAddLending extends AppCompatActivity {
    ListView recentLendListView;
    AdapterLendItem adapter;

    String selectedName;
    int money; // 잔액을 전달받아야함.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lending);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        adapter = new AdapterLendItem();
        //dummy data
        adapter.addItem("김오미","010-1111-1111","신용도 AAA");
        adapter.addItem("나오미","010-1111-1111","신용도 AAA");
        adapter.addItem("박오미","010-1111-1111","신용도 AAA");
        adapter.addItem("이오미","010-1111-1111","신용도 AAA");
        adapter.addItem("조오미","010-1111-1111","신용도 AAA");

        recentLendListView = (ListView)findViewById(R.id.recent_lend);
        recentLendListView.setAdapter(adapter);
    }
}
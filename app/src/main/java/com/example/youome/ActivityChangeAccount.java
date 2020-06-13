package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

public class ActivityChangeAccount extends AppCompatActivity {

    ListView accountListView;
    AdapterAccountItem adpater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        accountListView = (ListView)findViewById(R.id.account_list);
        adpater = new AdapterAccountItem();

        //dummy
        adpater.addItem("[농협은행] 유오미","0000000000000");
        adpater.addItem("[카카오뱅크] 유오미","0000000000000");
        adpater.addItem("[국민은행] 유오미","0000000000000");
        adpater.addItem("[하나은행] 유오미","0000000000000");
        adpater.addItem("[신한은행] 유오미","0000000000000");
        accountListView.setAdapter(adpater);
    }
}
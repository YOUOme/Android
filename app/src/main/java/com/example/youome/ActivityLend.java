package com.example.youome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ActivityLend extends AppCompatActivity {
    TextView tName,tMoney,tAccount,tAccountNum,bt_complete;
    CalendarView cv;
    BottomSheetBehavior mBottomSheetBehavior;
    View nestedBottomSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);

        tName = (TextView)findViewById(R.id.tx_name);
        tName.setText(getIntent().getStringExtra("name"));
        tMoney = (TextView)findViewById(R.id.tx_money);
        tMoney.setText(getIntent().getIntExtra("money",0)+"원");
        tAccount = (TextView)findViewById(R.id.tx_account);
        tAccountNum = (TextView)findViewById(R.id.tx_acoount_num);

        bt_complete = (TextView)findViewById(R.id.bt_complete);
        bt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityIOU.class);
                startActivity(intent);
            }
        });

        nestedBottomSheet = findViewById(R.id.bottomsheet);

        mBottomSheetBehavior =BottomSheetBehavior.from(nestedBottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if(i == BottomSheetBehavior.STATE_EXPANDED){
                    tAccount.setText("농협");                        // ---- db
                    tAccountNum.setText("000 - 0000 - 0000 - 00");   // ---- db
                }
                else if(i == BottomSheetBehavior.STATE_COLLAPSED){
                    tAccount.setText("계좌");
                    tAccountNum.setText("최근 정보 불러오기");
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) { }
        });
        //cv.findViewById(R.id.calendarView);
        //cv.setDate();
    }
}
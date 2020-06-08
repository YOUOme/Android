package com.example.youome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ActivityLoan extends AppCompatActivity {
    TextView tName, tMoney, tAccount, tAccountNum, bt_complete;
    CalendarView cv;
    BottomSheetBehavior mBottomSheetBehavior;
    View nestedBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        tName = (TextView) findViewById(R.id.tx_name);
        tName.setText(getIntent().getStringExtra("name"));
        tMoney = (TextView) findViewById(R.id.tx_money);
        tMoney.setText(getIntent().getIntExtra("money", 0) + "원");
        tAccount = (TextView) findViewById(R.id.tx_account);
        tAccountNum = (TextView) findViewById(R.id.tx_acoount_num);

        nestedBottomSheet = findViewById(R.id.bottomsheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(nestedBottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    tAccount.setText("농협");                        // ---- db
                    tAccountNum.setText("000 - 0000 - 0000 - 00");   // ---- db
                } else if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    tAccount.setText("계좌");
                    tAccountNum.setText("최근 정보 불러오기");
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });
        //cv.findViewById(R.id.calendarView);
        //cv.setDate();
    }

    public void onCompleteClick(View view) {
        View dlgView = View.inflate(this, R.layout.dialog_trade, null);
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(dlgView);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button ok = (Button) dlgView.findViewById(R.id.bt_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityIOU.class);
                startActivity(intent);
            }
        });

        ImageView cancel = (ImageView) dlgView.findViewById(R.id.bt_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.cancel();
            }
        });

        dlg.show();
    }
}

package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityLoan_v2 extends AppCompatActivity {
    TextView bt_due_plus,bt_due_minus,tx_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templete_loan);
        bt_due_plus = (TextView)findViewById(R.id.bt_due_plus);
        bt_due_minus = (TextView)findViewById(R.id.bt_due_minus);
        bt_due_plus.setVisibility(View.GONE);
        bt_due_minus.setVisibility(View.GONE);
        tx_detail = (TextView)findViewById(R.id.tx_detail);
        tx_detail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
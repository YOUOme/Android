package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityLoan_v2 extends AppCompatActivity {
    TextView bt_due_plus,bt_due_minus,tx_detail,templete_loan_title1,tx_money;
    Intent parnetIntent;
    public int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templete_loan);

        parnetIntent = getIntent();
        bt_due_plus = (TextView)findViewById(R.id.bt_due_plus);
        bt_due_minus = (TextView)findViewById(R.id.bt_due_minus);
        bt_due_plus.setVisibility(View.GONE);
        bt_due_minus.setVisibility(View.GONE);

        tx_detail = (TextView)findViewById(R.id.tx_detail);
        tx_detail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        templete_loan_title1 = (TextView)findViewById(R.id.templete_loan_title1);
        templete_loan_title1.setText(parnetIntent.getStringExtra("name"));

        money = parnetIntent.getIntExtra("money", 0);
        tx_money = (TextView)findViewById(R.id.tx_money);
        tx_money.setText(String.format("%,d",parnetIntent.getIntExtra("money", 0)) + "원");
        tx_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentAddLoan faddlend = new FragmentAddLoan(parnetIntent.getStringExtra("name"),2);
                faddlend.show(getSupportFragmentManager(),faddlend.getTag());
            }
        });
    }
}
package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ActivityLoan_v2 extends AppCompatActivity {
    private TextView bt_due_plus,bt_due_minus,tx_detail,templete_loan_title1,bt_done;
    private TextView bt_interest_plus,bt_interest_minus,bt_calendar,tx_interest;
    private int interest_rate = 10;
    public TextView tx_payment,tx_money;
    private Intent parnetIntent;
    public int money;

    FragmentBotPayment botPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templete_loan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        bt_done = (TextView)findViewById(R.id.bt_done);
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityIOU.class);
                startActivity(intent);
                finish();
            }
        });

        tx_payment = (TextView)findViewById(R.id.tx_payment);


        // ---- 이자율 ----
        tx_interest = (TextView)findViewById(R.id.percent_interest);
        bt_interest_plus = (TextView)findViewById(R.id.bt_plus);
        bt_interest_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interest_rate < 20)
                    interest_rate++;
                tx_interest.setText(interest_rate+"%");
            }
        });

        bt_interest_minus = (TextView)findViewById(R.id.bt_minus);
        bt_interest_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interest_rate > 0)
                    interest_rate--;
                tx_interest.setText(interest_rate+"%");
            }
        });
    }

    public void onSelectPayment(View view){
        FragmentBotPayment botPayment = new FragmentBotPayment();
        botPayment.show(getSupportFragmentManager(),botPayment.getTag());
    }
}
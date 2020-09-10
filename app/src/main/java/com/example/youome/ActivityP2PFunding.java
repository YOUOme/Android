package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityP2PFunding extends AppCompatActivity {
    private TextView title1,title2,done,main,due_plus,due_minus,due_date;
    private ImageView bt_calander;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templete_loan);

        title1 = (TextView)findViewById(R.id.templete_loan_title1);
        title1.setText("P2P 펀딩");
        title2 = (TextView)findViewById(R.id.templete_loan_title2);
        title2.setText(" 추가하기");

        done = (TextView)findViewById(R.id.tx_done);
        done.setText("추가하기");
        done.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.youome_red));

        main = (TextView)findViewById(R.id.templete_loan_main);
        main.setText("P2P펀딩");

        due_plus = (TextView)findViewById(R.id.bt_due_plus);
        due_minus = (TextView)findViewById(R.id.bt_due_minus);

        bt_calander = (ImageView)findViewById(R.id.bt_calander);
        bt_calander.setVisibility(View.GONE);

        due_date = (TextView)findViewById(R.id.templete_due_date);
        due_date.setText("1개월");
    }
}
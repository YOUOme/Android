package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ActivityIOU extends AppCompatActivity {

    TextView transText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_o_u);

        transText = (TextView)findViewById(R.id.bt_iou_alarm);
        if(getIntent().getIntExtra("mode",1) == 1)
            transText.setText("알림");
        else
            transText.setText("상환하기");
    }
}
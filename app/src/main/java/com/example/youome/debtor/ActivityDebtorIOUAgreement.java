package com.example.youome.debtor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youome.R;

public class ActivityDebtorIOUAgreement extends AppCompatActivity {

    Button agreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtor_i_o_u_agreement);

        Intent intent = getIntent();
//        if(intent != null) {
//            String notificationData = intent.getStringExtra("test");
//            if(notificationData != null)
//                Log.d("FCM_TEST", notificationData);
//        }

        agreeButton = (Button)findViewById(R.id.debtor_bt_agree);
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityDebtorIOUTransfer.class);
                startActivity(intent);
            }
        });
    }
}
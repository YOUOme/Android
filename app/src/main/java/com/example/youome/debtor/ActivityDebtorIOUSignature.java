package com.example.youome.debtor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youome.R;
import com.example.youome.debtor.ActivityDebtorIOUAgreement;

public class ActivityDebtorIOUSignature extends AppCompatActivity {

    Button loanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtor_i_o_u_signature);

        Intent intent = getIntent();
//        if(intent != null) {
//            String notificationData = intent.getStringExtra("test");
//            if(notificationData != null)
//                Log.d("FCM_TEST", notificationData);
//        }

        loanButton = (Button)findViewById(R.id.bt_loan);
        loanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityDebtorIOUAgreement.class);
                startActivity(intent);
            }
        });
    }
}
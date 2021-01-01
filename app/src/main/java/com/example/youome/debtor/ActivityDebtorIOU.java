package com.example.youome.debtor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youome.R;

public class ActivityDebtorIOU extends AppCompatActivity {

    Button signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtor_i_o_u);

        Intent intent = getIntent();
//        if(intent != null) {
//            String notificationData = intent.getStringExtra("test");
//            if(notificationData != null)
//                Log.d("FCM_TEST", notificationData);
//        }

        signButton = (Button)findViewById(R.id.debtor_bt_sign);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityDebtorIOUSignature.class);
                startActivity(intent);
            }
        });
    }
}
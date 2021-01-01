package com.example.youome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ActivityDebtor extends AppCompatActivity {

    Button rejectButton;
    Button checkIOUButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtor);

        Intent intent = getIntent();
//        if(intent != null) {//푸시알림을 선택해서 실행한것이 아닌경우 예외처리
//            String notificationData = intent.getStringExtra("test");
//            if(notificationData != null)
//                Log.d("FCM_TEST", notificationData);
//        }

        rejectButton = (Button)findViewById(R.id.bt_reject);
        checkIOUButton = (Button)findViewById(R.id.bt_check_iou);
        checkIOUButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ActivityDebtorIOU.class);
                startActivity(intent);
            }
        });
    }
}
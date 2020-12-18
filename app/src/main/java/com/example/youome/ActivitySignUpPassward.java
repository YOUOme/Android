package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySignUpPassward extends AppCompatActivity {
    TextView[] tv;
    TextView re,tx_mention,tx_password_type;
    ImageView[] iv;
    ImageView back;
    int num[],touchCount = 0,prossCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_passward);
        tv = new TextView[10];
        num = new int[10];
        iv = new ImageView[6];
        tv[0] = (TextView)findViewById(R.id.bt_pass0);
        tv[1] = (TextView)findViewById(R.id.bt_pass1);
        tv[2] = (TextView)findViewById(R.id.bt_pass2);
        tv[3] = (TextView)findViewById(R.id.bt_pass3);
        tv[4] = (TextView)findViewById(R.id.bt_pass4);
        tv[5] = (TextView)findViewById(R.id.bt_pass5);
        tv[6] = (TextView)findViewById(R.id.bt_pass6);
        tv[7] = (TextView)findViewById(R.id.bt_pass7);
        tv[8] = (TextView)findViewById(R.id.bt_pass8);
        tv[9] = (TextView)findViewById(R.id.bt_pass9);
        iv[0] = (ImageView)findViewById(R.id.oval1);
        iv[1] = (ImageView)findViewById(R.id.oval2);
        iv[2] = (ImageView)findViewById(R.id.oval3);
        iv[3] = (ImageView)findViewById(R.id.oval4);
        iv[4] = (ImageView)findViewById(R.id.oval5);
        iv[5] = (ImageView)findViewById(R.id.oval6);
        for(int i=0;i<10;i++) tv[i].setOnClickListener(new PasswordClickListener());

        tx_mention = (TextView)findViewById(R.id.tx_mention);
        tx_password_type = (TextView)findViewById(R.id.tx_password_type);
        back = (ImageView)findViewById(R.id.bt_back_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(touchCount > 0)
                    touchCount--;
                render();
            }
        });
    }

    private class PasswordClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            touchCount++;
            render();
            if(touchCount == 6) {
                touchCount = 0;
                prossCount++;
                tx_mention.setText("결제비밀번호를 한 번 더 입력해주세요.");
                tx_password_type.setText("결제비밀번호 확인");
                if(prossCount == 2){
                    render();
                    final Intent intent = new Intent(getApplicationContext(),ActivityYOUOme.class);
                    final ProgressDialog progressDialog = new ProgressDialog(ActivitySignUpPassward.this);
                    progressDialog.setMessage("결제 비밀번호를 등록하는 중...");
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try{
                                Thread.sleep(2500);}
                            catch (Exception e){}
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }
                    };
                    thread.start();

                }
                render();
            }
        }
    }

    private void render(){
        for(int i=0;i<6;i++)
            iv[i].setImageResource(R.drawable.ic_oval2);
        for(int i=0;i<touchCount;i++)
            iv[i].setImageResource(R.drawable.ic_oval);
    }
}
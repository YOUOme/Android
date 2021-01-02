package com.example.youome.loanholder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youome.R;

public class ActivityIOUtransfer extends AppCompatActivity {

    private Button bt_ok;
    private TextView bt_done,tx_mention,bt_cancel;
    private LinearLayout ani_loading;
    private ImageView oval_load1,oval_load2,oval_load3,img_cancel;
    private Handler mHandlerMainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_o_u_transfer);

        bt_ok = (Button)findViewById(R.id.bt_ok);
        bt_ok.setVisibility(View.GONE);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_cancel = (TextView)findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tx_mention = (TextView)findViewById(R.id.tx_mention);
        tx_mention.setText("김오미의 승인을 기다리는 중입니다.");  // ToDo : connection

        bt_done = (TextView)findViewById(R.id.bt_done);
        bt_done.setVisibility(View.GONE);

        ani_loading = (LinearLayout)findViewById(R.id.ani_loading);
        oval_load1 = (ImageView)findViewById(R.id.oval_load1);
        oval_load2 = (ImageView)findViewById(R.id.oval_load2);
        oval_load3 = (ImageView)findViewById(R.id.oval_load3);
        img_cancel = (ImageView)findViewById(R.id.img_cancel);

        mHandlerMainThread = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                completeTransfer();
                super.handleMessage(msg);
            }
        };
        progressAni();
    }

    private int epoch=0;
    private void progressAni(){
        final Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                // ToDo : api callback
                for(int i=0;;i++) {
                    oval_load1.setImageResource(R.drawable.ic_oval2);
                    oval_load2.setImageResource(R.drawable.ic_oval2);
                    oval_load3.setImageResource(R.drawable.ic_oval2);
                    if (i == 1)
                        oval_load1.setImageResource(R.drawable.ic_oval);
                    else if (i == 2)
                        oval_load2.setImageResource(R.drawable.ic_oval);
                    else if (i == 3)
                        oval_load3.setImageResource(R.drawable.ic_oval);
                    try {
                        Thread.sleep(800);
                    } catch (Exception e) {
                    }
                    if (i % 3 == 0) i = 0;
                    epoch++;
                    if (epoch == 10) {      // ToDo - Complete Callback
                        mHandlerMainThread.sendEmptyMessage(0);
                        break;
                    }
                }
            }
        };
        thread.start();
    }

    private void completeTransfer(){
        tx_mention.setText("김오미와의 거래가 생성되었습니다.");  // ToDo : connection
        bt_done.setVisibility(View.VISIBLE);
        ani_loading.setVisibility(View.GONE);
        bt_ok.setVisibility(View.VISIBLE);
        bt_cancel.setVisibility(View.GONE);
        img_cancel.setVisibility(View.GONE);
    }
}
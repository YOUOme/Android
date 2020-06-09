package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityAlarm extends AppCompatActivity {

    ListView recentListView, pastListView;
    AdapterAlarmItem adapter1,adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recentListView = (ListView)findViewById(R.id.recent_listview);
        pastListView = (ListView)findViewById(R.id.past_listview);
        adapter1 = new AdapterAlarmItem(getApplicationContext(),R.color.youome_background);
        adapter2 = new AdapterAlarmItem(getApplicationContext());

        //dummy
        adapter1.addItem("더치페이가 생성되었습니다.","20.01.05.일","5000원","김유미");
        adapter1.addItem("상한 기한 15일 전입니다.","20.01.20.일","20000원","김유미");

        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");
        adapter2.addItem("더치페이가 생성되었습니다.","20.01.04.일","20000원","김유미");

        recentListView.setAdapter(adapter1);
        pastListView.setAdapter(adapter2);
    }
}
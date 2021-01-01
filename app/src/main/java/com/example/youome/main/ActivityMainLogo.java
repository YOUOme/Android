package com.example.youome.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youome.ActivityDebtor;
import com.example.youome.ActivityIOU;
import com.example.youome.ActivitySignUp;
import com.example.youome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ActivityMainLogo extends AppCompatActivity {

    TextView bt_login,touch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        Log.d("intent : ", intent.toString());
        if(intent.getFlags() == 0x14400000 || intent.getFlags() == 0x14000000){
            String notificationData = intent.getStringExtra("test");
            if(notificationData != null)
                Log.d("FCM_TEST", notificationData);

            // ToDo - compass0
            // 로그인 api를 통해서 로그인 한 후에
            // Activity 전환(뒤로가기해도 이전 엑티비티로 가지 않고 앱이 꺼지도록 만들기.) 즉, 전환했을 때의 액티비티가 앱의 제일 처음 액티비티처럼 행동하도록.
            intent = new Intent(getApplicationContext(), ActivityDebtor.class);
            startActivity(intent);
        }

        bt_login = (TextView)findViewById(R.id.kakao_login);
        touch = (TextView)findViewById(R.id.touchme);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivitySignUp.class);
                startActivity(intent);
            }
        });

        /*FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                Log.d("dddd", "onComplete Call");
                if(!task.isSuccessful()){

                }
                String token = task.getResult().getToken();
            }
        });*/

        // 기기고유id
        String regId = FirebaseInstanceId.getInstance().getToken();
        Log.d("dddd",regId);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @SuppressLint("JavascriptInterface")
    public void onSignupClick(View view){
        View dlgView = View.inflate(this, R.layout.dialog_openbanking_auth, null);
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(dlgView);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dlg.setCancelable(false);

        /* 다양한 디바이스 해상도 적응 */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Window window = dlg.getWindow();
        int x = (int)(size.x *0.93f);
        int y = (int)(size.y *0.8f);
        window.setLayout(x,y);
        /*~~~~~~~~~~~~~~~~~~~~~~~*/
        ImageView back = (ImageView) dlgView.findViewById(R.id.bt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.cancel();
            }
        });

        WebView webView = (WebView)dlgView.findViewById(R.id.openbanking_auth);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSaveFormData(true);

        webView.addJavascriptInterface(new AndroidBridge(),"BRIDGE");
        webView.loadUrl("https://testapi.openbanking.or.kr/oauth/2.0/authorize?auth_type=0&response_type=code&client_id=JfSpiq1QR1xFqZS3z1yhEL4E2PKYDDcou7DG85Tl&redirect_uri=http://220.70.46.145:8001/api/auth/openbank&state=abcdefghijklmnopqrstuvwxyz123456&scope=login inquiry transfer&client_info=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlZzTjF4cHdWcmttS0tCai9RZUNJNG9kdlRKbz0iLCJpYXQiOjE2MDgzMTg0NjQsImV4cCI6MTYwODkyMzI2NCwiaXNzIjoieW91b21lLmNvbSIsInN1YiI6InVzZXJJbmZvIn0.tpI4uyUeZ1WDbjVsXIZtfuMH_0RmwD5jOXqJZGITfnY");
        //webView.loadUrl("http://220.70.46.145:8001/bridgeTest");
        webView.setWebViewClient(new MyWebViewClient());
        dlg.show();
    }

    final class AndroidBridge{
        @JavascriptInterface
        public void CallFinishAuth(){
            Intent intent = new Intent(getApplicationContext(),ActivitySignUp.class);
            Log.d("dddd","~~~~~~~~~~~~~~~~bridge!!!");
            startActivity(intent);
        }
    }
}

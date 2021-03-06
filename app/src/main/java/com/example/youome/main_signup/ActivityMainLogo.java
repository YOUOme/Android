package com.example.youome.main_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youome.debtor.ActivityDebtor;
import com.example.youome.R;
import com.example.youome.network.NetworkAPI;
import com.example.youome.network.NetworkRectrofitClient;
import com.example.youome.network.RegisterPayload;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMainLogo extends AppCompatActivity {

    private TextView bt_login,touch;

    private NetworkAPI youmeapi;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;

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
            intent = new Intent(getApplicationContext(), ActivityDebtor.class);
            startActivity(intent);
            finish();
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
        //Log.d("dddd",regId);

        youmeapi = NetworkRectrofitClient.getClient().create(NetworkAPI.class);
        startRequest(new RegisterPayload.ReqData(regId));
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

    private void startRequest(RegisterPayload.ReqData data){
        youmeapi.callDataRegister(data).enqueue(new Callback<RegisterPayload.ResData>() {
            @Override
            public void onResponse(Call<RegisterPayload.ResData> call, Response<RegisterPayload.ResData> response) {
                RegisterPayload.ResData result = response.body();

                Log.d("dddd",result.getMessage()+", "+result.getYoume_token());

                if(result.getMessage().equals("registered successfully")){
                    sf = getSharedPreferences("access",MODE_PRIVATE);
                    editor = sf.edit();
                    editor.putString("token",result.getYoume_token());
                    editor.commit();
                    return;
                }


            }
            @Override
            public void onFailure(Call<RegisterPayload.ResData> call, Throwable t) {
                t.printStackTrace();
                Log.d("dddd","fail!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        });
    }
}

package com.example.youome.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youome.ActivitySignUp;
import com.example.youome.R;

public class ActivityMainLogo extends AppCompatActivity {

    TextView bt_login,touch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bt_login = (TextView)findViewById(R.id.kakao_login);
        touch = (TextView)findViewById(R.id.touchme);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivitySignUp.class);
                startActivity(intent);
            }
        });
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
        webView.loadUrl("https://testapi.openbanking.or.kr/oauth/2.0/authorize?response_type=code&client_id=JfSpiq1QR1xFqZS3z1yhEL4E2PKYDDcou7DG85Tl&redirect_uri=https://220.70.46.145:8000/authResult&scope=login inquiry transfer&state=abcdefghijklmnopqrstuvwxyz123456&auth_type=0");
        webView.setWebViewClient(new MyWebViewClient());
        dlg.show();
    }

    final class AndroidBridge{
        public void CallFinishAuth(){
            Intent intent = new Intent(getApplicationContext(),ActivitySignUp.class);
            startActivity(intent);
        }
    }
}

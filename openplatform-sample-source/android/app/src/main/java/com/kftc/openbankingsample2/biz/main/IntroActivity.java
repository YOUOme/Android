package com.kftc.openbankingsample2.biz.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kftc.openbankingsample2.R;

import java.util.List;

import timber.log.Timber;

/**
 * 앱실행시 가장먼저 진입하는 초기화 화면
 */
public class IntroActivity extends AppCompatActivity {

    // context
    private Context context;

    // view
    // ...

    // data
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        context = this;
        args = getIntent().getExtras();
        if (args == null) args = new Bundle();

        // Status Bar 제거한 전체화면
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // actionBar 제거
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        initView();
    }

    void initView() {
        initData();
    }


    void initData() {
        // 앱 실행 필수권한 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            callPermissions();
        } else {
            init();
        }
    }

    void callPermissions() {
        TedPermission.with(context)
                .setPermissions(Manifest.permission.READ_PHONE_STATE)

                .setDeniedMessage("필수권한에 동의하지 않으면 앱을 사용할 수 없습니다.")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        init();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        finishAffinity();
                    }
                })
                .check();
    }

    void init() {

        // 로깅 초기화
        Timber.plant(new Timber.DebugTree());

        goNext();
    }

    void goNext() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(args);
        startActivity(intent);
    }
}

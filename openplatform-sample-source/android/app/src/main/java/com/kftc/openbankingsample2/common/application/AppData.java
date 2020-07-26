package com.kftc.openbankingsample2.common.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.data.AccessToken;
import com.kftc.openbankingsample2.common.data.BankAccount;
import com.kftc.openbankingsample2.common.http.KmProgressBar;

import java.util.ArrayList;
import java.util.List;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * 어플리케이션 클래스
 */
public class AppData extends Application {

    public static Context appContext;

    public static List<AccessToken> centerAuthAccessTokenList = new ArrayList<>();
    public static List<BankAccount> centerAuthBankAccountList = new ArrayList<>();
    public static List<AccessToken> selfAuthAccessTokenList = new ArrayList<>();
    public static List<BankAccount> selfAuthBankAccountList = new ArrayList<>();

    public static String[] scopeList = {"login", "inquiry", "transfer"};
    public static String[] scopeInqTranList = {"inquiry", "transfer"};

    private static KmProgressBar progressBar;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        // 최초 로딩시 저장된 accessToken 로딩
        centerAuthAccessTokenList = CenterAuthUtils.getSavedCenterAuthTokenList();
        selfAuthAccessTokenList = SelfAuthUtils.getSavedSelfAuthTokenList();

        // 최초 로딩시 저장된 bank account 로딩
        centerAuthBankAccountList = CenterAuthUtils.getSavedCenterAuthBankAccountList();
        selfAuthBankAccountList = SelfAuthUtils.getSavedSelfAuthBankAccountList();

        // 설정 기본값 로딩(XXXXSettingFragment 기본값 적용)
        PreferenceManager.setDefaultValues(appContext, R.xml.fragment_center_auth_setting, true);
        PreferenceManager.setDefaultValues(appContext, R.xml.fragment_self_auth_setting, true);

        /*
            유용한 오픈소스를 소개한다.
            CaocConfig 오픈 라이브러리를 사용하면 앱의 오류를 즉시 확인하기에 유용하다.
            정의되지 않은 오류발생시 오류화면을 제공(Thread.setDefaultUncaughtExceptionHandler 이용)
            * application class 또는 error activity에서의 crash 발생시에는 에러화면이 보여지지 않는다.

            옵션은 웹페이지(https://github.com/Ereza/CustomActivityOnCrash)에서 확인이 가능하고,
            아래 errorActivity에 커스터마이징된 액티비티를 추가하는 경우에는 androidmanifest 에도 등록해 줘야 한다(android:process 필수)
            <activity android:name="com.mycompany.CustomCrashActivity"
                  android:label="오류"
                  android:process=":error_activity"/>

            gradle 에는 다음을 추가한다.
            implementation 'cat.ereza:customactivityoncrash:2.2.0'
        */
        CaocConfig.Builder.create()
                .showErrorDetails(true)     // 오류내용을 볼 수 있는 버튼을 보여줄지 여부
                .trackActivities(true)      // registerActivityLifecycleCallbacks() 사용하여 Activity 로그 추가
                .apply();

    }

    // 환경설정값을 읽어들인다. CenterAuthSettingFragment.class 에서 저장되는 값과 동일한 저장공간을 사용하기 위해서 PreferenceManager 의 저장공간을 선택한다.
    public static SharedPreferences getPref() {
        return appContext.getSharedPreferences(appContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    public static Context getAppContext() {
        return appContext;
    }

    // 토큰 리스트
    public static List<AccessToken> getCenterAuthAccessTokenList() {
        return getCenterAuthAccessTokenList(null);
    }

    // 토큰 리스트
    public static String getCenterAuthAccessToken(Scope scope) {
        List<AccessToken> accessTokenList = getCenterAuthAccessTokenList(scope);
        if (accessTokenList.isEmpty()) {
            return "";
        }

        return accessTokenList.get(0).getAccess_token();
    }

    // 토큰 리스트
    public static List<AccessToken> getCenterAuthAccessTokenList(Scope scope) {
        if (scope == null) {
            return centerAuthAccessTokenList;
        }
        ArrayList<AccessToken> result = new ArrayList<>();
        for (AccessToken accessToken : centerAuthAccessTokenList) {
            if (accessToken.hasScope(scope)) {
                result.add(accessToken);
            }
        }
        return result;
    }

    // 토큰 리스트
    public static List<AccessToken> getSelfAuthAccessTokenList() {
        return getSelfAuthAccessTokenList(null);
    }

    // 토큰 리스트
    public static String getSelfAuthAccessToken(Scope scope) {
        List<AccessToken> accessTokenList = getSelfAuthAccessTokenList(scope);
        if (accessTokenList.isEmpty()) {
            return "";
        }

        return accessTokenList.get(0).getAccess_token();
    }

    // 토큰 리스트
    public static List<AccessToken> getSelfAuthAccessTokenList(Scope scope) {
        if (scope == null) {
            return selfAuthAccessTokenList;
        }
        ArrayList<AccessToken> result = new ArrayList<>();
        for (AccessToken accessToken : selfAuthAccessTokenList) {
            if (accessToken.hasScope(scope)) {
                result.add(accessToken);
            }
        }
        return result;
    }
}

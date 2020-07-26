package com.kftc.openbankingsample2.biz.self_auth.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.AccessToken;
import com.kftc.openbankingsample2.common.data.BankAccount;
import com.kftc.openbankingsample2.common.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * 자체인증 유틸
 */
public class SelfAuthUtils {

    // 토큰저장. 최근 토큰리스트는 5개까지만 저장한다.
    public static void saveSelfAuthToken(AccessToken accessToken) {
        accessToken.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));
        List<AccessToken> accessTokenList = AppData.selfAuthAccessTokenList;
        accessTokenList.add(0, accessToken);
        if (accessTokenList.size() > 5) {
            AppData.selfAuthAccessTokenList.remove(5);
        }

        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN_LIST, new Gson().toJson(accessTokenList));
        editor.apply();

        // 최근 데이터로 하나 저장
        Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken.getAccess_token());
    }

    // 저장된 토큰리스트를 가져온다.
    public static List<AccessToken> getSavedSelfAuthTokenList() {
        String json = AppData.getPref().getString(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN_LIST, "");
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        try {
            return new Gson().fromJson(json, new TypeToken<List<AccessToken>>() {}.getType());
        } catch (Exception e) {
            Timber.e(e);
            return new ArrayList<>();
        }
    }

    // 계좌정보 저장
    public static void saveSelfAuthBankAccountList(List<BankAccount> bankAccountList) {

        if (bankAccountList == null || bankAccountList.size() == 0) {
            return;
        }

        AppData.selfAuthBankAccountList = bankAccountList;

        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString(SelfAuthConst.SELF_AUTH_BANK_ACCOUNT_LIST, new Gson().toJson(bankAccountList));
        editor.apply();

        // 첫번째 데이터로 하나 저장. 이미 최근에 저장된게 있으면 스킵
        Utils.saveDataIfNotExist(SelfAuthConst.SELF_AUTH_FINTECH_USE_NUM, bankAccountList.get(0).getFintech_use_num());
        Utils.saveDataIfNotExist(SelfAuthConst.SELF_AUTH_BANK_CODE, bankAccountList.get(0).getBank_code_std());
        Utils.saveDataIfNotExist(SelfAuthConst.SELF_AUTH_ACCOUNT_NUM, bankAccountList.get(0).getAccountNum());
    }

    // 저장된 계좌정보리스트를 가져온다.
    public static List<BankAccount> getSavedSelfAuthBankAccountList() {
        String json = AppData.getPref().getString(SelfAuthConst.SELF_AUTH_BANK_ACCOUNT_LIST, "");
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        try {
            return new Gson().fromJson(json, new TypeToken<List<BankAccount>>() {}.getType());
        } catch (Exception e) {
            Timber.e(e);
            return new ArrayList<>();
        }
    }

    // 현재 설정돤 환경이 리얼인지 여부를 가져온다.
    public static boolean isReal() {
        SharedPreferences pref = AppData.getPref();
        return pref.getBoolean(SelfAuthConst.SELF_AUTH_IS_REAL, false);
    }

    // 현재 설정된 환경이 리얼인지 테스트인지를 가져온다.
    public static String getEnv(){
        return isReal() ? "REAL" : "TEST";
    }

    // setting 에서 저장된 값을 가져온다. _TEST, _REAL 을 구별해서 가져온다.
    public static String getSavedValueFromSetting(String key) {
        String keyForEnv = key;
        keyForEnv += "_" + getEnv();
        return AppData.getPref().getString(keyForEnv, "");
    }
}

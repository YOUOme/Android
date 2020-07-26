package com.kftc.openbankingsample2.biz.center_auth.util;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.AccessToken;
import com.kftc.openbankingsample2.common.data.BankAccount;
import com.kftc.openbankingsample2.common.util.Utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * 센터인증 유틸
 */
public class CenterAuthUtils {

    // 토큰저장. 최근 토큰리스트는 5개까지만 저장한다.
    public static void saveCenterAuthToken(AccessToken accessToken) {
        accessToken.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));
        List<AccessToken> accessTokenList = AppData.centerAuthAccessTokenList;
        accessTokenList.add(0, accessToken);
        if (accessTokenList.size() > 5) {
            AppData.centerAuthAccessTokenList.remove(5);
        }

        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN_LIST, new Gson().toJson(accessTokenList));
        editor.apply();

        // 최근 데이터로 하나 저장
        Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken.getAccess_token());
    }

    // 저장된 토큰리스트를 가져온다.
    public static List<AccessToken> getSavedCenterAuthTokenList() {
        String json = AppData.getPref().getString(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN_LIST, "");
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
    public static void saveCenterAuthBankAccountList(List<BankAccount> bankAccountList) {

        if (bankAccountList == null || bankAccountList.size() == 0) {
            return;
        }

        AppData.centerAuthBankAccountList = bankAccountList;

        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString(CenterAuthConst.CENTER_AUTH_BANK_ACCOUNT_LIST, new Gson().toJson(bankAccountList));
        editor.apply();

        // 첫번째 데이터로 하나 저장. 이미 최근에 저장된게 있으면 스킵
        Utils.saveDataIfNotExist(CenterAuthConst.CENTER_AUTH_FINTECH_USE_NUM, bankAccountList.get(0).getFintech_use_num());
        Utils.saveDataIfNotExist(CenterAuthConst.CENTER_AUTH_BANK_CODE, bankAccountList.get(0).getBank_code_std());
        Utils.saveDataIfNotExist(CenterAuthConst.CENTER_AUTH_ACCOUNT_NUM, bankAccountList.get(0).getAccountNum());
    }

    // 저장된 계좌정보리스트를 가져온다.
    public static List<BankAccount> getSavedCenterAuthBankAccountList() {
        String json = AppData.getPref().getString(CenterAuthConst.CENTER_AUTH_BANK_ACCOUNT_LIST, "");
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

    // view 하위 tableLayout > tablerow 내의 edittext에서 id를 key로, edittext의 문자열값을 값으로 저장
    public static void saveFormData(View rootView, int tableLayoutId) {
        SharedPreferences.Editor editor = AppData.getPref().edit();
        String env = getEnv();
        editor.putString("ENV", env);
        String es = "_" + env;

        TableLayout tableLayout = rootView.findViewById(tableLayoutId);
        TableRow tableRow;
        View tmpView;
        EditText et;
        String id, key, val;
        for(int i=0; i<tableLayout.getChildCount(); i++){
            tableRow = (TableRow)tableLayout.getChildAt(i);
            for(int j=0; j<tableRow.getChildCount(); j++){
                tmpView = tableRow.getChildAt(j);
                if(tmpView instanceof EditText){
                    et = (EditText)tmpView;
                    id = et.getResources().getResourceEntryName(et.getId());
                    key = id.substring(3); // id에서 "et_" 제거
                    editor.putString(key + es, et.getText().toString());
                } else if (tmpView instanceof LinearLayout) {
                    LinearLayout layout = (LinearLayout) tmpView;
                    View tmpView2;
                    EditText et2;
                    String id2, key2;
                    for (int k=0; k <  layout.getChildCount(); k++) {
                        tmpView2 = layout.getChildAt(k);
                        if (tmpView2 instanceof EditText) {
                            et2 = (EditText) tmpView2;
                            id2 = et2.getResources().getResourceEntryName(et2.getId());
                            key2 = id2.substring(3); // id에서 "et_" 제거
                            editor.putString(key2 + es, et2.getText().toString());
                        }
                    }

                }
            }
        }
        editor.apply();
    }

    // 앱에 저장된 값을 view 하위 tableLayout > tablerow 내의 edittext에 문자열값을 설정한다.
    public static void setEditTextFromSavedValue(View rootView, int tableLayoutId) {
        TableLayout tableLayout = rootView.findViewById(tableLayoutId);
        TableRow tableRow;
        View tmpView;
        EditText et;
        String id, key, val;
        for(int i=0; i<tableLayout.getChildCount(); i++){
            tableRow = (TableRow)tableLayout.getChildAt(i);
            for(int j=0; j<tableRow.getChildCount(); j++){
                tmpView = tableRow.getChildAt(j);
                if(tmpView instanceof EditText){
                    et = (EditText)tmpView;
                    id = et.getResources().getResourceEntryName(et.getId());
                    key = id.substring(3);                  // id에서 "et_" 제거
                    val = getSavedValueOrGetConstant(key);  // 뒤에 _TEST 또는 _REAL 추가한뒤 저장된 값을 가져온다.
                    et.setText(val);
                    //Log.d("##", "EditText id:["+id+"], key:["+key+"], val:["+val+"]");
                } else if (tmpView instanceof LinearLayout) {
                    LinearLayout layout = (LinearLayout) tmpView;
                    View tmpView2;
                    EditText et2;
                    String id2, val2, key2;
                    for (int k=0; k <  layout.getChildCount(); k++) {
                        tmpView2 = layout.getChildAt(k);
                        if (tmpView2 instanceof EditText) {
                            et2 = (EditText) tmpView2;
                            id2 = et2.getResources().getResourceEntryName(et2.getId());
                            key2 = id2.substring(3); // id에서 "et_" 제거
                            val2 = getSavedValueOrGetConstant(key2);  // 뒤에 _TEST 또는 _REAL 추가한뒤 저장된 값을 가져온다.
                            et2.setText(val2);
                        }
                    }
                }
            }
        }
    }

    public static String getSavedValueOrGetConstant(String key) {
        String keyForEnv = key + "_" + getEnv();
        return AppData.getPref().getString(keyForEnv, getConstantsVal(keyForEnv));
    }

    // 기본값을 가져온다.
    public static String getConstantsVal(String constantsFieldName) {

        Field field;
        Object value = null;
        try {
            field = CenterAuthConst.class.getDeclaredField(constantsFieldName);
            field.setAccessible(true);
            value = field.get(null);    // Constants는 interface 여야함
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
        String ret = Utils.defaultString(value);
        return ret;
    }

    // 기본값을 가져온다(리얼/테스트 자동구분)
    public static String getConstantsValEnv(String constantsFieldName) {
        String constantsFieldNameForEnv = constantsFieldName + "_" + getEnv();
        return getConstantsVal(constantsFieldNameForEnv);
    }

    // 현재 설정돤 환경이 리얼인지 여부를 가져온다.
    public static boolean isReal() {
        SharedPreferences pref = AppData.getPref();
        return pref.getBoolean(CenterAuthConst.CENTER_AUTH_IS_REAL, false);
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


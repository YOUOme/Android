package com.kftc.openbankingsample2.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IntDef;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.kftc.openbankingsample2.common.application.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Pattern;

import timber.log.Timber;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Utils {

    // Json 으로부터 값을 추출한다.
    public static String getValueFromJson(String jsonStr, String key) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonObject.getString(key);
        } catch (JSONException e) {
            Timber.d(key + " 값이 없음");
            return "";
        }
    }

    // 파일에 값을 저장한다.
    public static void saveData(String key, String value) {
        if (value == null || value.trim().length() == 0) {
            return;
        }

        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString(key, value);
        editor.apply();
    }

    // 파일에 값을 저장한다.
    public static void saveIntData(String key, int value) {
        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    // 값이 없을경우에만 파일에 저장한다.
    public static boolean saveDataIfNotExist(String key, String value) {
        SharedPreferences pref = AppData.getPref();
        if (pref.contains(key)) {
            return false;
        }

        saveData(key, value);
        return true;
    }

    // 앱내 공간에 저장된 값을 가져온다.
    public static String getSavedValue(String key) {
        return AppData.getPref().getString(key, "");
    }

    // 앱내 공간에 저장된 값을 가져온다.
    public static int getSavedIntValue(String key) {
        return AppData.getPref().getInt(key, 0);
    }

    // 앱내 공간에 저장된 값을 가져온다.
    public static boolean getSavedBoolValue(String key) {
        return AppData.getPref().getBoolean(key, false);
    }

    // 앱내 공간에 저장된 값을 가져오고, 값이 없는 경우 기본값을 사용한다.
    public static String getSavedValueOrDefault(String key, String defaultValue) {
        return AppData.getPref().getString(key, defaultValue);
    }

    // url 문자열에 포함된 특정 파라미터의 값을 리턴한다.
    public static String getParamValFromUrlString(String url, String paramKey){

        Log.d("## url", url);
        String[] urlParamPair = url.split("\\?");
        if(urlParamPair.length < 2){
            Log.d("##", "파라미터가 존재하지 않는 URL 입니다.");
            return "";
        }
        String queryString = urlParamPair[1];
        Log.d("## queryString", queryString);
        StringTokenizer st = new StringTokenizer(queryString, "&");
        while(st.hasMoreTokens()){
            String pair = st.nextToken();
            Log.d("## pair", pair);
            String[] pairArr = pair.split("=");
            if(paramKey.equals(pairArr[0])){
                return pairArr[1]; // 찾았을 경우
            }
        }
        return "";
    }

    // URL encoding wrapper
    public static String urlEncode(String src){

        String ret = "";
        try {
            ret = URLEncoder.encode(defaultString(src), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // URL decode wrapper
    public static String urlDecode(String src){
        String ret = "";
        try {
            ret = URLDecoder.decode(defaultString(src), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // Map 데이터를 url query 문자열로 변환(url encoding 포함)
    public static String convertMapToQuerystring(Map<?, ?> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> e : paramMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", urlEncode(defaultString(e.getKey())), urlEncode(defaultString(e.getValue()))));
        }
        return sb.toString();
    }

    // Map 데이터를 url query 문자열로 변환(url encoding 미포함. 로그용)
    public static String convertMapToString(Map<?, ?> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> e : paramMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", defaultString(e.getKey()), defaultString(e.getValue())));
        }
        return sb.toString();
    }

    // String null 체크
    public static String defaultString(final String str) {
        return str == null ? "" : str;
    }

    // Object 를 string 으로 변환
    public static String defaultString(Object src){
        return defaultString(src, null);
    }

    // Object 를 string 으로 변환
    public static String defaultString(Object src, final String defaultStr){
        if(src != null){
            if(src instanceof String){
                return (String)src;
            }else{
                return String.valueOf(src);
            }
        }else{
            return (defaultStr == null) ? "" : defaultStr;
        }
    }

    // 오늘로부터 며칠차이가 나는 날짜를 yyyyMMdd 로 표시
    public static String getDateString8(int dayDiff) {
        TimeZone jst = TimeZone.getTimeZone("Asia/Seoul");
        Calendar cal = Calendar.getInstance(jst);
        cal.add(Calendar.DATE, dayDiff);
        return new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(cal.getTime());
    }

    // 현재 시각으로부터 몇분차이가 나는 시간을 HHmmss 로 표시
    public static String getTimeString8(int minDiff) {
        TimeZone jst = TimeZone.getTimeZone("Asia/Seoul");
        Calendar cal = Calendar.getInstance(jst);
        cal.add(Calendar.MINUTE, minDiff);
        return new SimpleDateFormat("HHmmss", Locale.KOREA).format(cal.getTime());
    }

    // 현재 시간/분/초/밀리세컨드를 9자리 문자열로 표현
    public static String getCurrentTime() {
        return new SimpleDateFormat("HHmmssSSS", Locale.KOREA).format(new Date());
    }

    // 숫자를 원단위로 표시
    public static String moneyForm(int data) {
        return moneyForm(String.valueOf(data));
    }

    // 문자열을 원단위로 표시
    public static String moneyForm(String data) {
        if (data == null) {
            Timber.e("data is NULL");
            return "";
        }

        data = data.trim();
        if ("".equals(data)) {
            Timber.e("data is empty string");
            return "";
        }

        data = delChr(data, ',');
        if (!isStringDouble(data)) {
            return data;
        }

        // Changes current default locale into Locale.ENGLISH because of number
        // format error in some locales
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        String result;
        if (data.contains(".")) {
            ((DecimalFormat) numberFormat).applyPattern(",##0.##");
            result = numberFormat.format(Double.parseDouble(data));
        } else {
            ((DecimalFormat) numberFormat).applyPattern(",###");
            result = numberFormat.format(Long.parseLong(data));
        }
        return result;
    }

    // 날짜포맷. 20180101 -> 2018.01.01 또는 2018-01-01 등으로 표현
    public static String dateForm(String date, String delim) {
        if(date == null || "".equals(date.trim())|| "0".equals(date))
            return "";

        if (date.length() < 8) {
            return date;
        }
        return date.substring(0, 4) + delim + date.substring(4, 6) + delim + date.substring(6);
    }

    // 앱 재시작
    public static void restartApp(Context context) {

        // launcher activity 찾기
        Class<? extends Activity> clazz = getLaucherActivity(context);
        if (clazz == null) return;

        // 재시작
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // 현재 프로세스 죽이기
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    // 앱 설정 초기화
    public static void removePref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().clear().apply();
    }

    // launcher activity 찾기
    public static Class<? extends Activity> getLaucherActivity(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null && intent.getComponent() != null) {
            try {
                return (Class<? extends Activity>) Class.forName(intent.getComponent().getClassName());
            } catch (ClassNotFoundException e) {
                Timber.e("[KM Util] 오류!!! launcher activity 찾기 실패.");
            }
        }
        return null;
    }

    // 문자열에 지정된 값을 더해서 다시 문자열로 반환
    public static String plusString(String src, int add) {
        try {
            int srcInt = Integer.parseInt(src);
            return String.valueOf(srcInt + add);
        } catch (NumberFormatException e) {
            Timber.e(e);
            return "";
        }
    }

    private static String delChr(String str, char delChr) {
        if(str==null||"".equals(str)){
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for ( int i = 0; i < str.length(); ++i ) {
            if ( str.charAt(i) != delChr ) {
                buffer.append(str.charAt(i));
            }
        }
        return buffer.toString();
    }

    private static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 폰번호 가져오기
    @Retention(SOURCE)
    @IntDef({DIGIT, HYPHEN, DOT, SPACE})
    public @interface PhoneNumberFormat { }
    public static final int HYPHEN = 0; // 010-2222-3333
    public static final int DIGIT = 1;  // 01022223333
    public static final int DOT = 2;    // 010.2222.3333
    public static final int SPACE = 3;  // 010 2222 3333

    public static String getPhoneNumber(@PhoneNumberFormat int format) {
        Context context = AppData.getAppContext();
        String phoneNumber;

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return "";
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            phoneNumber = telephonyManager.getLine1Number();
        } else {
            return "";
        }
        if (phoneNumber == null) {
            return "";
        }

        // +821022223333 등의 국제번호가 포함되어있으면 이를 제거한뒤 010-2222-3333 또는 01022223333 으로 리턴
        // android.telephony.PhoneNumberUtils를 사용하면 SDK 21 이상이 필요하므로, 구글 오픈소스 PhoneNumberUtil 사용
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber pn = phoneNumberUtil.parse(phoneNumber, "KR");

            // 한국 번호가 아닌 경우에는 null 리턴
            if(pn.getCountryCode() != phoneNumberUtil.getCountryCodeForRegion("KR")) {
                return "";
            }

            // 기본이 Hyphen으로 표시됨. if(format == HYPHEN)
            phoneNumber = phoneNumberUtil.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);

            if(format == DIGIT)
                phoneNumber = normalizePhoneNumber(phoneNumber);

            else if(format == DOT)
                phoneNumber = phoneNumber.replace('-', '.');

            else if(format == SPACE)
                phoneNumber = phoneNumber.replace('-', ' ');

        } catch (NumberParseException e) {
            Timber.d("-----[KM 유틸] getPhoneNumber 오류----- : NumberParseException");
            return null;
        }

        return phoneNumber;
    }

    /**
     * 전화번호에 포함된 문자열을 삭제하고 숫자만 리턴
     * (android.telephony.PhoneNumberUtils(SDK 21이상)의 normalizeNumber 함수를 그대로 Copy)
     *
     * Normalize a phone number by removing the characters other than digits. If
     * the given number has keypad letters, the letters will be converted to
     * digits first.
     *
     * @param phoneNumber the number to be normalized.
     * @return the normalized number.
     */
    public static String normalizePhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int len = phoneNumber.length();
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            // Character.digit() supports ASCII and Unicode digits (fullwidth, Arabic-Indic, etc.)
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (sb.length() == 0 && c == '+') {
                sb.append(c);
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                return normalizePhoneNumber(PhoneNumberUtils.convertKeypadLettersToDigits(phoneNumber));
            }
        }
        return sb.toString();
    }

    // html 태그 사용여부
    public static boolean isHtml(CharSequence str) {
        return !TextUtils.isEmpty(str) && isHtml(str.toString());
    }

    /**
     * html 태크 사용여부
     * 출처 : https://stackoverflow.com/a/22581832/8910486
     * @param str
     * @return
     */
    public static boolean isHtml(String str) {
        if (TextUtils.isEmpty(str)) return false;

        String tagStart= "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
        String tagEnd= "\\</\\w+\\>";
        String tagSelfClosing= "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
        String htmlEntity= "&[a-zA-Z][a-zA-Z0-9]+;";
        Pattern htmlPattern=Pattern.compile("("+tagStart+".*"+tagEnd+")|("+tagSelfClosing+")|("+htmlEntity+")", Pattern.DOTALL);
        return htmlPattern.matcher(str).find();
    }

}

package com.kftc.openbankingsample2.biz.center_auth.http;

import androidx.viewpager.widget.PagerAdapter;

import com.google.gson.GsonBuilder;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.common.http.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 센터인증 https 통신 어댑터
 */
public class CenterAuthApiRetrofitAdapter {

    // 일반적인 json 통신용
    private static CenterAuthApiRetrofitInterface apiCallRetrofitInterface;
    private static OkHttpClient okHttpClient;

    public synchronized static CenterAuthApiRetrofitInterface getInstance() {
        if (apiCallRetrofitInterface != null) {
            return apiCallRetrofitInterface;
        }

        // 쿠키매니저
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        // client
        okHttpClient = configureClient(new OkHttpClient().newBuilder()) // 인증서 무시 설정 적용
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        // retrofit
        apiCallRetrofitInterface = new Retrofit.Builder()
                .baseUrl(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_BASE_URI))
                .client(okHttpClient)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().enableComplexMapKeySerialization().create()))
                .build().create(CenterAuthApiRetrofitInterface.class);

        return apiCallRetrofitInterface;
    }

    // 변환없이 웹페이지 그대로 로드하고 싶을때 사용용(웹뷰 페이지 로딩 전 테스트용)
    private static CenterAuthApiRetrofitInterface apiCallRetrofitInterface2;
    private static OkHttpClient okHttpClient2;

    public synchronized static CenterAuthApiRetrofitInterface getInstance2() {
        if (apiCallRetrofitInterface2 != null) {
            return apiCallRetrofitInterface2;
        }

        okHttpClient2 = new OkHttpClient().newBuilder().build();
        apiCallRetrofitInterface2 = new Retrofit.Builder()
                .baseUrl(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_BASE_URI))
                .client(okHttpClient2)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return (Converter<ResponseBody, Object>) ResponseBody::string;
                    }
                })
                .build().create(CenterAuthApiRetrofitInterface.class);

        return apiCallRetrofitInterface2;

    }

    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder){

        final TrustManager[] certs = new TrustManager[]{ new X509TrustManager() {
            @Override public void checkClientTrusted(X509Certificate[] chain, String authType) {}
            @Override public void checkServerTrusted(X509Certificate[] chain, String authType) {}
            @Override public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        try {
            SSLContext ctx;
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());  // 인증서 유효성 검증안함
            builder.sslSocketFactory(ctx.getSocketFactory(), (X509TrustManager) certs[0]);
            builder.hostnameVerifier((hostname, session) -> true); // hostname 검증안함
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return builder;
    }

}

package com.kftc.openbankingsample2.biz.self_auth.http;

import com.google.gson.GsonBuilder;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.http.HttpLoggingInterceptor;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 자체인증 https 통신 어댑터
 */
public class SelfAuthApiRetrofitAdapter {

    private static SelfAuthApiRetrofitInterface apiCallRetrofitInterface;
    private static OkHttpClient okHttpClient;

    public synchronized static SelfAuthApiRetrofitInterface getInstance() {
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
                .baseUrl(SelfAuthUtils.getSavedValueFromSetting(SelfAuthConst.SELF_AUTH_BASE_URI))
                .client(okHttpClient)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().enableComplexMapKeySerialization().create()))
                .build().create(SelfAuthApiRetrofitInterface.class);

        return apiCallRetrofitInterface;
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

package com.kftc.openbankingsample2.biz.center_auth.http;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 센터인증 호출 API
 */
public interface CenterAuthApiRetrofitInterface {

    // 본인확인 웹뷰 호출시(사용자인증)
    String authorizeUrl = "/oauth/2.0/authorize";

    // 본인확인 웹뷰 호출시(계좌등록확인)
    String authorizeAccountUrl = "/oauth/2.0/authorize_account";

    // 본인확인 웹뷰 호출전 정상유무 테스트용
    @GET(authorizeUrl)
    Call<Map> authorize(@HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> params);

    // access_token 획득
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Call<Map> token(@FieldMap Map<String, String> params);

    // 사용자 정보조회
    @GET("/v2.0/user/me")
    Call<Map> userMe(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 잔액조회
    @GET("/v2.0/account/balance/fin_num")
    Call<Map> accountBalanceFinNum(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 거래내역조회
    @GET("/v2.0/account/transaction_list/fin_num")
    //@GET("/v2.0/account/transaction_list")
    Call<Map> accountTrasactionListFinNum(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 출금이체
    @POST("/v2.0/transfer/withdraw/fin_num")
    Call<Map> transferWithdrawFinNum(@Header("Authorization") String token, @Body Map<String, String> params);

    // 계좌실명조회
    @POST("/v2.0/inquiry/real_name")
    Call<Map> inquiryRealName(@Header("Authorization") String token, @Body Map<String, String> params);

    // 입금이체
    @POST("/v2.0/transfer/deposit/fin_num")
    Call<Map> transferDepositFinNum(@Header("Authorization") String token, @Body Map<String, Object> params);

    // 등록계좌조회
    @GET("/v2.0/account/list")
    Call<Map> accountList(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 계좌해지
    @POST("/v2.0/account/cancel")
    Call<Map> accountCancel(@Header("Authorization") String token, @Body Map<String, String> params);

    // 이체결과조회
    @POST("/v2.0/transfer/result")
    Call<Map> transferResult(@Header("Authorization") String token, @Body Map<String, Object> params);

    // 송금인정보조회
    @POST("/v2.0/inquiry/remit_list")
    Call<Map> inquiryRemitList(@Header("Authorization") String token, @Body Map<String, String> params);

    // 수취조회
    @POST("/v2.0/inquiry/receive")
    Call<Map> inquiryReceive(@Header("Authorization") String token, @Body Map<String, String> params);

    // 자금반환청구
    @POST("/v2.0/return/claim")
    Call<Map> returnClaim(@Header("Authorization") String token, @Body Map<String, String> params);

    // 자금반환결과조회
    @POST("/v2.0/return/result")
    Call<Map> returnResult(@Header("Authorization") String token, @Body Map<String, String> params);

}

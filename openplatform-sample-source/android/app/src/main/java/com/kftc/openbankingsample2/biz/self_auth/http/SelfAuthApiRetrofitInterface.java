package com.kftc.openbankingsample2.biz.self_auth.http;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 자체인증 호출 API
 */
public interface SelfAuthApiRetrofitInterface {

    // access_token 획득
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Call<Map> token(@FieldMap Map<String, String> params);

    // 계좌등록(자체인증만)
    @POST("/v2.0/user/register")
    Call<Map> userRegister(@Header("Authorization") String token, @Body Map<String, String> params);

    // 사용자 정보조회
    @GET("/v2.0/user/me")
    Call<Map> userMe(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 잔액조회(핀테크이용번호 사용)
    @GET("/v2.0/account/balance/fin_num")
    Call<Map> accountBalanceFinNum(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 잔액조회(계좌번호 사용, 자체인증)
    @POST("/v2.0/account/balance/acnt_num")
    Call<Map> accountBalanceAcntNum(@Header("Authorization") String token, @Body Map<String, String> params);

    // 거래내역조회(핀테크이용번호 사용)
    @GET("/v2.0/account/transaction_list/fin_num")
    Call<Map> accountTrasactionListFinNum(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 거래내역조회(계좌번호 사용)
    @POST("/v2.0/account/transaction_list/acnt_num")
    Call<Map> accountTrasactionListAcntNum(@Header("Authorization") String token, @Body Map<String, String> params);

    // 출금이체(핀테크이용번호 사용)
    @POST("/v2.0/transfer/withdraw/fin_num")
    Call<Map> transferWithdrawFinNum(@Header("Authorization") String token, @Body Map<String, String> params);

    // 출금이체(계좌번호 사용)
    @POST("/v2.0/transfer/withdraw/acnt_num")
    Call<Map> transferWithdrawAcntNum(@Header("Authorization") String token, @Body Map<String, String> params);

    // 계좌실명조회
    @POST("/v2.0/inquiry/real_name")
    Call<Map> inquiryRealName(@Header("Authorization") String token, @Body Map<String, String> params);

    // 입금이체(핀테크이용번호)
    @POST("/v2.0/transfer/deposit/fin_num")
    Call<Map> transferDepositFinNum(@Header("Authorization") String token, @Body Map<String, Object> params);

    // 입금이체(계좌번호 사용)
    @POST("/v2.0/transfer/deposit/acnt_num")
    Call<Map> transferDepositAcntNum(@Header("Authorization") String token, @Body Map<String, Object> params);

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

    // 이상금융거래탐지 내역조회
    @GET("/v2.0/inquiry/fds_detect")
    Call<Map> inquiryFdsDetect(@Header("Authorization") String token, @QueryMap Map<String, String> params);
}

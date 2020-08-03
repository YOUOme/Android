package com.example.youome;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkAPI {
    @POST("/member/join")
    Call<ActivitySignUp.NetworkJoinRes> callData(@Body ActivitySignUp.NetworkJoinReq data);

    //@POST("/member/login")
    //Call<ActivitySignUp.NetworkJoinRes> callData(@Body ActivitySignUp.NetworkJoinReq data);
}

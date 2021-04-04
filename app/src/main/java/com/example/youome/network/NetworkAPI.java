package com.example.youome.network;

import com.example.youome.main_signup.ActivitySignUp;
import com.example.youome.loanholder.ActivityIOU;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetworkAPI {
    //@POST("/member/join")
    //Call<ActivitySignUp.NetworkJoinRes> callData(@Body ActivitySignUp.NetworkJoinReq data);

    //@POST("/member/login")
    //Call<ActivitySignUp.NetworkJoinRes> callData(@Body ActivitySignUp.NetworkJoinReq data);

    @Headers("x-access-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlZzTjF4cHdWcmttS0tCai9RZUNJNG9kdlRKbz0iLCJpYXQiOjE2MDgzMTg0NjQsImV4cCI6MTYwODkyMzI2NCwiaXNzIjoieW91b21lLmNvbSIsInN1YiI6InVzZXJJbmZvIn0.tpI4uyUeZ1WDbjVsXIZtfuMH_0RmwD5jOXqJZGITfnY")
    @POST("/api/transfer/user")
    Call<ActivityIOU.ResData> callData(@Body ActivityIOU.ReqData data);

    @POST("/api/auth/register")
    Call<RegisterPayload.ResData> callData(@Body RegisterPayload.ReqData data);
}

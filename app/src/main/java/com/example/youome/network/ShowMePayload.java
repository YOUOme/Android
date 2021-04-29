package com.example.youome.network;

import com.google.gson.annotations.SerializedName;

public class ShowMePayload {
    public static class ReqData{
        @SerializedName("id")
        String id;

        public ReqData(String id){ this.id = id; }
    }

    public class ResData{
        @SerializedName("message")
        String message;
        @SerializedName("user_name")
        String user_name;
        @SerializedName("account_number")
        String account_number;

        public ResData(String message, String user_name, String account_number){
            this.message = message;
            this.user_name = user_name;
            this.account_number = account_number;
        }

        public String getMessage() {
            return message;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getAccount_number() {
            return account_number;
        }
    }
}

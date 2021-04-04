package com.example.youome.network;

import com.google.gson.annotations.SerializedName;

public class RegisterPayload {
    public class ReqData{
        @SerializedName("id")
        String id;

        public ReqData(String id){ this.id = id; }
    }

    public class ResData{
        @SerializedName("message")
        String message;
        @SerializedName("youme_token")
        String youme_token;

        public ResData(String message, String youme_token){
             this.message = message;
             this.youme_token = youme_token;
        }

        public String getMessage(){ return message;}
        public String getYoume_token(){return youme_token;}
    }

}

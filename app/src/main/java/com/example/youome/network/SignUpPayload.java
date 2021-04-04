package com.example.youome.network;

import com.google.gson.annotations.SerializedName;

public class SignUpPayload {
    public class NetworkJoinReq {
        @SerializedName("memberEmail")
        String memberEmail;
        @SerializedName("memberPwd")
        String memberPwd;
        @SerializedName("memberName")
        String memberName;

        public NetworkJoinReq(String memberEmail, String memberPwd, String memberName) {
            this.memberEmail = memberEmail;
            this.memberPwd = memberPwd;
            this.memberName = memberName;
        }
    }

    public class NetworkJoinRes {
        @SerializedName("resultCode")
        int resultCode;
        @SerializedName("message")
        String message;
        @SerializedName("memberToken")
        int memberToken;

        public NetworkJoinRes(int resultCode, String message, int memberToken) {
            this.resultCode = resultCode;
            this.message = message;
            this.memberToken = memberToken;
        }
        public int getResultCode() {
            return resultCode;
        }
        public String getMessage() {
            return message;
        }
        public int getMemberToken() {
            return memberToken;
        }
    }
}

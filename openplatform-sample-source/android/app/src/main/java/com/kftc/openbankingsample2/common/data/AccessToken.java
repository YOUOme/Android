package com.kftc.openbankingsample2.common.data;

import com.kftc.openbankingsample2.common.Scope;

public class AccessToken {

    String access_token;
    String token_type;
    String expires_in;
    String refresh_token;
    String scope;
    String user_seq_no;     // 사용자일련번호(3-legged)
    String client_use_code; // 이용기관코드(2-legged)
    String dateTime;        // 토큰발급 날짜시간

    public AccessToken(String access_token, String token_type, String expires_in, String refresh_token, String scope, String user_seq_no) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.user_seq_no = user_seq_no;
    }

    public String getAccess_token() {
        return access_token == null ? "" : access_token;
    }

    public String getAccess_token_alias() {
        return access_token.length() > 13 ? access_token.substring(0, 13) + "..." : access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type == null ? "" : token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in == null ? "" : expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token == null ? "" : refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope == null ? "" : scope;
    }

    public boolean hasScope(Scope scope) {
        String[] scopeList = getScope().split(" ");
        for (String str : scopeList) {
            if (str.equals(scope.getNameVal())) {
                return true;
            }
        }
        return false;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUser_seq_no() {
        return user_seq_no == null ? "" : user_seq_no;
    }

    public void setUser_seq_no(String user_seq_no) {
        this.user_seq_no = user_seq_no;
    }

    public String getClient_use_code() {
        return client_use_code == null ? "" : client_use_code;
    }

    public void setClient_use_code(String client_use_code) {
        this.client_use_code = client_use_code;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "발급일시: " + dateTime +
                "\nAccess 토큰: " + getAccess_token_alias() +
                "\nScope: " + scope +
                (!getUser_seq_no().isEmpty() ? ("\nUser_Seq_No: " + user_seq_no) : ("\nClient_Use_Code: " + getClient_use_code()));
    }
}

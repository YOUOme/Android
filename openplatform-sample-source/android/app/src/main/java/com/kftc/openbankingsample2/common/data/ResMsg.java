package com.kftc.openbankingsample2.common.data;

import java.util.ArrayList;

/**
 * 응답메시지
 */
public class ResMsg {

    String rsp_code;
    String rsp_message;
    String bank_rsp_code;
    String bank_rsp_message;
    ArrayList<BankResMsg> res_list;

    public String getRsp_code() {
        return rsp_code  == null ? "" : rsp_code;
    }

    public String getRsp_message() {
        return rsp_message == null ? "" : rsp_message;
    }

    public String getBank_rsp_code() {
        return bank_rsp_code == null ? "" : bank_rsp_code;
    }

    public String getBank_rsp_message() {
        return bank_rsp_message == null ? "" : bank_rsp_message;
    }

    public ArrayList<BankResMsg> getRes_list() {
        return res_list;
    }

    public boolean isSuccess() {

        // 사용자인증의 경우 정상이면 rsp_code 값이 존재하지 않는다.
        // 사용자인증 외 API는 정상이면 A0000
        return "".equals(getRsp_code()) || "A0000".equals(getRsp_code());
    }

    public class BankResMsg {
        String bank_rsp_code;
        String bank_rsp_message;

        public String getBank_res_code() {
            return bank_rsp_code == null ? "" : bank_rsp_code;
        }

        public String getBank_res_message() {
            return bank_rsp_message == null ? "" : bank_rsp_message;
        }
    }

}

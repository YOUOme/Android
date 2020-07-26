package com.kftc.openbankingsample2.common.data;

import java.util.ArrayList;

/**
 * 입금이체 응답 DTO
 */
public class ApiCallTransferDepositResponse {

    private String api_tran_id;
    private String api_tran_dtm;
    private String rsp_code;
    private String rsp_message;
    private String wd_bank_code_std;
    private String wd_bank_code_sub;
    private String wd_bank_name;
    private String wd_account_num_masked;
    private String wd_print_content;
    private String wd_account_holder_name;
    private String res_cnt;
    private ArrayList<Transfer> res_list;

    public String getApi_tran_id() {
        return api_tran_id;
    }

    public String getApi_tran_dtm() {
        return api_tran_dtm;
    }

    public String getRsp_code() {
        return rsp_code;
    }

    public String getRsp_message() {
        return rsp_message;
    }

    public String getWd_bank_code_std() {
        return wd_bank_code_std;
    }

    public String getWd_bank_code_sub() {
        return wd_bank_code_sub;
    }

    public String getWd_bank_name() {
        return wd_bank_name;
    }

    public String getWd_account_num_masked() {
        return wd_account_num_masked;
    }

    public String getWd_print_content() {
        return wd_print_content;
    }

    public String getWd_account_holder_name() {
        return wd_account_holder_name;
    }

    public String getRes_cnt() {
        return res_cnt;
    }

    public ArrayList<Transfer> getRes_list() {
        return res_list;
    }
}

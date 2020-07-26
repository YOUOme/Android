package com.kftc.openbankingsample2.common.data;

import timber.log.Timber;

/**
 * 입금이체 DTO
 */
public class Transfer {

    private String tran_no;
    private String bank_tran_id;
    private String bank_tran_date;
    private String bank_code_tran;
    private String bank_rsp_code;
    private String bank_rsp_message;
    private String fintech_use_num;
    private String account_alias;
    private String bank_code_std;
    private String bank_code_sub;
    private String bank_name;
    private String account_num_masked;
    private String print_content;
    private String account_holder_name;
    private String tran_amt;

    public String getTran_no() {
        return tran_no;
    }

    public String getBank_tran_id() {
        return bank_tran_id;
    }

    public String getBank_tran_date() {
        return bank_tran_date;
    }

    public String getBank_code_tran() {
        return bank_code_tran;
    }

    public String getBank_rsp_code() {
        return bank_rsp_code;
    }

    public String getBank_rsp_message() {
        return bank_rsp_message;
    }

    public String getFintech_use_num() {
        return fintech_use_num;
    }

    public String getAccount_alias() {
        return account_alias;
    }

    public String getBank_code_std() {
        return bank_code_std;
    }

    public String getBank_code_sub() {
        return bank_code_sub;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getAccount_num_masked() {
        return account_num_masked;
    }

    public String getPrint_content() {
        return print_content;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public String getTran_amt() {
        return tran_amt == null ? "" : tran_amt;
    }

    public double getTranAmtDouble() {
        try {
            return Double.valueOf(getTran_amt());
        } catch (NumberFormatException e) {
            Timber.e(e);
            return 0;
        }
    }
}

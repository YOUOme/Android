package com.kftc.openbankingsample2.common.data;

import timber.log.Timber;

/**
 * 이체결과 반복부 DTO
 */
public class TransferResult {
    private String tran_no;
    private String bank_tran_id;
    private String bank_tran_date;
    private String bank_code_tran;
    private String bank_rsp_code;
    private String bank_rsp_message;
    private String wd_bank_code_std;
    private String wd_bank_code_sub;
    private String wd_bank_name;
    private String wd_fintech_use_num;
    private String wd_account_num_masked;
    private String wd_print_content;
    private String wd_account_holder_name;
    private String dps_bank_code_std;
    private String dps_bank_code_sub;
    private String dps_bank_name;
    private String dps_fintech_use_num;
    private String dps_account_num_masked;
    private String dps_print_content;
    private String dps_account_holder_name;
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

    public String getWd_bank_code_std() {
        return wd_bank_code_std;
    }

    public String getWd_bank_code_sub() {
        return wd_bank_code_sub;
    }

    public String getWd_bank_name() {
        return wd_bank_name;
    }

    public String getWd_fintech_use_num() {
        return wd_fintech_use_num;
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

    public String getDps_bank_code_std() {
        return dps_bank_code_std;
    }

    public String getDps_bank_code_sub() {
        return dps_bank_code_sub;
    }

    public String getDps_bank_name() {
        return dps_bank_name;
    }

    public String getDps_fintech_use_num() {
        return dps_fintech_use_num;
    }

    public String getDps_account_num_masked() {
        return dps_account_num_masked;
    }

    public String getDps_print_content() {
        return dps_print_content;
    }

    public String getDps_account_holder_name() {
        return dps_account_holder_name;
    }

    public String getTran_amt() {
        return tran_amt;
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

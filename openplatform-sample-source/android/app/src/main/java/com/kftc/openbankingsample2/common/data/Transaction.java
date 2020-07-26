package com.kftc.openbankingsample2.common.data;

/**
 * 거래내역조회 결과 반복부 DTO, 송금인정보조회 결과 반복부 DTO
 */
public class Transaction {

    String tran_date;
    String tran_time;
    String inout_type;
    String tran_type;
    String print_content;
    String tran_amt;
    String after_balance_amt;
    String branch_name;

    // 송금인정보조회
    String remitter_name;
    String remitter_bank_code;
    String remitter_account_num;

    public String getTran_date() {
        return tran_date;
    }

    public String getTran_time() {
        return tran_time;
    }

    public String getInout_type() {
        return inout_type;
    }

    public String getTran_type() {
        return tran_type;
    }

    public String getPrint_content() {
        return print_content;
    }

    public String getTran_amt() {
        return tran_amt;
    }

    public String getAfter_balance_amt() {
        return after_balance_amt;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public String getRemitter_name() {
        return remitter_name;
    }

    public String getRemitter_bank_code() {
        return remitter_bank_code;
    }

    public String getRemitter_account_num() {
        return remitter_account_num;
    }
}

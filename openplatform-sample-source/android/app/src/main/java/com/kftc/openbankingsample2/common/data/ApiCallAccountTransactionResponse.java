package com.kftc.openbankingsample2.common.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * 거래내역조회 결과 DTO, 송금인정보조회 결과 DTO
 */
public class ApiCallAccountTransactionResponse implements Parcelable {

    private String api_tran_id;
    private String api_tran_dtm;
    private String rsp_code;
    private String rsp_message;
    private String bank_tran_id;
    private String bank_tran_date;
    private String bank_code_tran;
    private String bank_rsp_code;
    private String bank_rsp_message;

    // 거래내역조회
    private String fintech_use_num;

    // 송금인정보조회
    private String bank_code_std;
    private String account_num;

    private String balance_amt;
    private String page_record_cnt;
    private String next_page_yn;
    private String befor_inquiry_trace_info;
    private ArrayList<Transaction> res_list;

    protected ApiCallAccountTransactionResponse(Parcel in) {
        api_tran_id = in.readString();
        api_tran_dtm = in.readString();
        rsp_code = in.readString();
        rsp_message = in.readString();
        bank_tran_id = in.readString();
        bank_tran_date = in.readString();
        bank_code_tran = in.readString();
        bank_rsp_code = in.readString();
        bank_rsp_message = in.readString();
        fintech_use_num = in.readString();
        balance_amt = in.readString();
        page_record_cnt = in.readString();
        next_page_yn = in.readString();
        befor_inquiry_trace_info = in.readString();
    }

    public static final Creator<ApiCallAccountTransactionResponse> CREATOR = new Creator<ApiCallAccountTransactionResponse>() {
        @Override
        public ApiCallAccountTransactionResponse createFromParcel(Parcel in) {
            return new ApiCallAccountTransactionResponse(in);
        }

        @Override
        public ApiCallAccountTransactionResponse[] newArray(int size) {
            return new ApiCallAccountTransactionResponse[size];
        }
    };

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

    public String getBank_code_std() {
        return bank_code_std;
    }

    public String getAccount_num() {
        return account_num;
    }

    public String getBalance_amt() {
        return balance_amt;
    }

    public String getPage_record_cnt() {
        return page_record_cnt == null ? "" : page_record_cnt;
    }

    public int getPageRecordCntInt() {
        try {
            return Integer.parseInt(getPage_record_cnt());
        } catch (NumberFormatException e) {
            Timber.e(e);
            return 0;
        }
    }


    public String getNext_page_yn() {
        return next_page_yn;
    }

    public boolean isNextPage() {
        return "Y".equals(next_page_yn);
    }

    public String getBefor_inquiry_trace_info() {
        return befor_inquiry_trace_info == null ? "" : befor_inquiry_trace_info;
    }

    public ArrayList<Transaction> getRes_list() {
        return res_list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(api_tran_id);
        dest.writeString(api_tran_dtm);
        dest.writeString(rsp_code);
        dest.writeString(rsp_message);
        dest.writeString(bank_tran_id);
        dest.writeString(bank_tran_date);
        dest.writeString(bank_code_tran);
        dest.writeString(bank_rsp_code);
        dest.writeString(bank_rsp_message);
        dest.writeString(fintech_use_num);
        dest.writeString(balance_amt);
        dest.writeString(page_record_cnt);
        dest.writeString(next_page_yn);
        dest.writeString(befor_inquiry_trace_info);
    }
}

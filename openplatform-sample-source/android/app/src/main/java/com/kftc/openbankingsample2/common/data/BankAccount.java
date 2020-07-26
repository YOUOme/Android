package com.kftc.openbankingsample2.common.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 계좌정보. 사용저정보조회 및 등록계좌조회에서 사용
 */
public class BankAccount implements Parcelable {

    private String fintech_use_num;
    private String account_alias;
    private String bank_code_std;
    private String bank_code_sub;
    private String bank_name;
    private String account_num;
    private String account_num_masked;
    private String account_holder_name;
    private String account_type;
    private String inquiry_agree_yn;
    private String inquiry_agree_dtime;
    private String transfer_agree_yn;
    private String transfer_agree_dtime;
    private String payer_num;               // 사용자정보조회 에서만 사용
    private String account_state;           // 등록계좌조회 에서만 사용

    protected BankAccount(Parcel in) {
        fintech_use_num = in.readString();
        account_alias = in.readString();
        bank_code_std = in.readString();
        bank_code_sub = in.readString();
        bank_name = in.readString();
        account_num = in.readString();
        account_num_masked = in.readString();
        account_holder_name = in.readString();
        account_type = in.readString();
        inquiry_agree_yn = in.readString();
        inquiry_agree_dtime = in.readString();
        transfer_agree_yn = in.readString();
        transfer_agree_dtime = in.readString();
        payer_num = in.readString();
        account_state = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fintech_use_num);
        dest.writeString(account_alias);
        dest.writeString(bank_code_std);
        dest.writeString(bank_code_sub);
        dest.writeString(bank_name);
        dest.writeString(account_num);
        dest.writeString(account_num_masked);
        dest.writeString(account_holder_name);
        dest.writeString(account_type);
        dest.writeString(inquiry_agree_yn);
        dest.writeString(inquiry_agree_dtime);
        dest.writeString(transfer_agree_yn);
        dest.writeString(transfer_agree_dtime);
        dest.writeString(payer_num);
        dest.writeString(account_state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BankAccount> CREATOR = new Creator<BankAccount>() {
        @Override
        public BankAccount createFromParcel(Parcel in) {
            return new BankAccount(in);
        }

        @Override
        public BankAccount[] newArray(int size) {
            return new BankAccount[size];
        }
    };

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

    public String getAccount_num() {
        return account_num;
    }

    public String getAccount_num_masked() {
        return account_num_masked;
    }

    public String getAccountNum() {
        if (TextUtils.isEmpty(getAccount_num())) {
            return getAccount_num_masked();
        } else {
            return getAccount_num();
        }
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getInquiry_agree_yn() {
        return inquiry_agree_yn;
    }

    public boolean isInquiry_agree_yn() {
        return "Y".equals(getInquiry_agree_yn());
    }

    public String getInquiry_agree_dtime() {
        return inquiry_agree_dtime;
    }

    public String getTransfer_agree_yn() {
        return transfer_agree_yn;
    }

    public boolean isTransfer_agree_yn() {
        return "Y".equals(getTransfer_agree_yn());
    }

    public String getTransfer_agree_dtime() {
        return transfer_agree_dtime;
    }

    public String getPayer_num() {
        return payer_num;
    }

    public String getAccount_state() {
        return account_state == null ? "" : account_state;
    }

    public String getAccountState() {

        String accountState = account_state.equals("01") ? "사용" :
                account_state.equals("03") ? "계좌등록요청" :
                account_state.equals("05") ? "해지요청" :
                account_state.equals("09") ? "해지" : "";

        return accountState.isEmpty() ? "" : accountState;
    }

    @Override
    public String toString() {
        return "핀테크이용번호: " + fintech_use_num +
                "\n은행정보: " + String.format("%s(%s)", getBank_name(), getBank_code_std()) +
                "\n계좌정보: " + String.format("%s  %s", getAccountNum(), account_holder_name) +
                "\n동의여부: " + String.format("조회: %s, 출금: %s", isInquiry_agree_yn() ? "동의" : "미동의", isTransfer_agree_yn() ? "동의" : "미동의") +
                (getAccount_state().isEmpty() ? "" : ", \n계좌상태: " + getAccountState());

    }
}

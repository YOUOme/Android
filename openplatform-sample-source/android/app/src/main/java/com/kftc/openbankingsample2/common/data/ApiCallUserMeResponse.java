package com.kftc.openbankingsample2.common.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * 사용자정보조회 및 등록계좌정보 응답메시지
 */
public class ApiCallUserMeResponse implements Parcelable {
    private String api_tran_id;
    private String api_tran_dtm;
    private String rsp_code;
    private String rsp_message;
    private String user_seq_no;     // 사용자정보조회 에서만 사용
    private String user_ci;         // 사용자정보조회 에서만 사용
    private String user_name;
    private String user_info;       // 사용자정보조회 에서만 사용
    private String user_gender;     // 사용자정보조회 에서만 사용
    private String user_cell_no;    // 사용자정보조회 에서만 사용
    private String user_email;      // 사용자정보조회 에서만 사용
    private String res_cnt;
    private ArrayList<BankAccount> res_list;

    protected ApiCallUserMeResponse(Parcel in) {
        api_tran_id = in.readString();
        api_tran_dtm = in.readString();
        rsp_code = in.readString();
        rsp_message = in.readString();
        user_seq_no = in.readString();
        user_ci = in.readString();
        user_name = in.readString();
        user_info = in.readString();
        user_gender = in.readString();
        user_cell_no = in.readString();
        user_email = in.readString();
        res_cnt = in.readString();
    }

    public static final Creator<ApiCallUserMeResponse> CREATOR = new Creator<ApiCallUserMeResponse>() {
        @Override
        public ApiCallUserMeResponse createFromParcel(Parcel in) {
            return new ApiCallUserMeResponse(in);
        }

        @Override
        public ApiCallUserMeResponse[] newArray(int size) {
            return new ApiCallUserMeResponse[size];
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

    public String getUser_seq_no() {
        return user_seq_no;
    }

    public String getUser_ci() {
        return user_ci;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_info() {
        return user_info;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_cell_no() {
        return user_cell_no;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getRes_cnt() {
        return res_cnt == null ? "" : res_cnt;
    }

    public int getResCntInt() {
        try {
            return Integer.parseInt(getRes_cnt());
        } catch (NumberFormatException e) {
            Timber.e(e);
            return 0;
        }
    }

    public ArrayList<BankAccount> getRes_list() {
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
        dest.writeString(user_seq_no);
        dest.writeString(user_ci);
        dest.writeString(user_name);
        dest.writeString(user_info);
        dest.writeString(user_gender);
        dest.writeString(user_cell_no);
        dest.writeString(user_email);
        dest.writeString(res_cnt);
    }
}

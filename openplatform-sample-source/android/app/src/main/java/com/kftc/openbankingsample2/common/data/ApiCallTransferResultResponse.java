package com.kftc.openbankingsample2.common.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 이체결과조회 응답 DTO
 */
public class ApiCallTransferResultResponse implements Parcelable {

    private String api_tran_id;
    private String api_tran_dtm;
    private String rsp_code;
    private String rsp_message;
    private String res_cnt;
    private ArrayList<TransferResult> res_list;


    protected ApiCallTransferResultResponse(Parcel in) {
        api_tran_id = in.readString();
        api_tran_dtm = in.readString();
        rsp_code = in.readString();
        rsp_message = in.readString();
        res_cnt = in.readString();
    }

    public static final Creator<ApiCallTransferResultResponse> CREATOR = new Creator<ApiCallTransferResultResponse>() {
        @Override
        public ApiCallTransferResultResponse createFromParcel(Parcel in) {
            return new ApiCallTransferResultResponse(in);
        }

        @Override
        public ApiCallTransferResultResponse[] newArray(int size) {
            return new ApiCallTransferResultResponse[size];
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

    public String getRes_cnt() {
        return res_cnt;
    }

    public ArrayList<TransferResult> getRes_list() {
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
        dest.writeString(res_cnt);
    }
}

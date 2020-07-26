package com.kftc.openbankingsample2.common.data;

/**
 * 통신 에러메시지
 */
public class HttpErrorMsg {
    String timestamp;
    String status;
    String error;
    String message;
    String path;

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        if (status == null || status.equals("")) {
            return "모름";
        }
        return status;
    }

    public String getError() {
        if (error == null || error.equals("")) {
            return "내용없음";
        }
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}

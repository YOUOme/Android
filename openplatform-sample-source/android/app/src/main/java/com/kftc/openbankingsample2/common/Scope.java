package com.kftc.openbankingsample2.common;

/**
 * 토큰범위 상수
 */
public enum Scope {
    LOGIN("login"),
    INQUIRY("inquiry"),
    TRANSFER("transfer"),
    OOB("oob"),
    SA("sa");

    private String nameVal;

    Scope(String nameVal) {
        this.nameVal = nameVal;
    }

    public String getNameVal() {
        return nameVal;
    }
}

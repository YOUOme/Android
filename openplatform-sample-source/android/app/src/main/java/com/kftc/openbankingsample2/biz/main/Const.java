package com.kftc.openbankingsample2.biz.main;

/**
 * 센터인증과 자체인증에서 공통으로 사용하는 상수값
 */
public interface Const {

    // 설정 화면에서 저장되는 환경값
    // ex) 리얼이면 CLIENT_ID_REAL, 테스트이면 CLIENT_ID_TEST 로 저장된다.
    // 기본값은 XXXXSettingFragment.class 에서 설정하고, 앱구동시 AppData.class 의 PreferenceManager.setDefaultValues 에 의해 불려진다.
    // 따라서 여기 값이 변경되면 XXXXSettingFragment.class 의 xml 파일에 있는 key값도 같이 변경되어야 한다.

    String IS_DEV_LANG = "IS_DEV_LANG";
}

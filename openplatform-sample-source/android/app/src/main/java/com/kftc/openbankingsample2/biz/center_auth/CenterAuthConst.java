package com.kftc.openbankingsample2.biz.center_auth;

/**
 * 센터인증 상수값 정의
 */
public interface CenterAuthConst {

    public static String ACTIONBAR_TITLE = "ACTIONBAR_TITLE";


    // 설정 화면에서 저장되는 환경값
    // ex) 리얼이면 CLIENT_ID_REAL, 테스트이면 CLIENT_ID_TEST 로 저장된다.
    // 기본값은 XXXXSettingFragment.class 에서 설정하고, 앱구동시 AppData.class 의 PreferenceManager.setDefaultValues 에 의해 불려진다.
    // 따라서 여기 값이 변경되면 XXXXSettingFragment.class 의 xml 파일에 있는 key값도 같이 변경되어야 한다.
    String CENTER_AUTH_IS_REAL = "CENTER_AUTH_IS_REAL";
    String CENTER_AUTH_BASE_URI = "CENTER_AUTH_BASE_URI";
    String CENTER_AUTH_CLIENT_ID = "CENTER_AUTH_CLIENT_ID";
    String CENTER_AUTH_CLIENT_SECRET = "CENTER_AUTH_CLIENT_SECRET";
    String CENTER_AUTH_CLIENT_USE_CODE = "CENTER_AUTH_CLIENT_USE_CODE";
    String CENTER_AUTH_REDIRECT_URI = "CENTER_AUTH_REDIRECT_URI";
    String CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM = "CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM";
    String CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM = "CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM";

    // 기타 화면에서 저장되는 환경값
    String CENTER_AUTH_ACCESS_TOKEN = "CENTER_AUTH_ACCESS_TOKEN";
    String CENTER_AUTH_ACCESS_TOKEN_LIST = "CENTER_AUTH_ACCESS_TOKEN_LIST";
    String CENTER_AUTH_BANK_ACCOUNT_LIST = "CENTER_AUTH_BANK_ACCOUNT_LIST";
    String CENTER_AUTH_FINTECH_USE_NUM = "CENTER_AUTH_FINTECH_USE_NUM";
    String CENTER_AUTH_CNTR_ACCOUNT_NUM = "CENTER_AUTH_CNTR_ACCOUNT_NUM";
    String CENTER_AUTH_BANK_CODE = "CENTER_AUTH_BANK_CODE";
    String CENTER_AUTH_ACCOUNT_NUM = "CENTER_AUTH_ACCOUNT_NUM";
    String CENTER_AUTH_USER_SEQ_NO = "CENTER_AUTH_USER_SEQ_NO";
    String CENTER_AUTH_ACCOUNT_HOLDER_INFO_TYPE_POSITION = "CENTER_AUTH_ACCOUNT_HOLDER_INFO_TYPE_POSITION";
    String CENTER_AUTH_ACCOUNT_HOLDER_INFO = "CENTER_AUTH_ACCOUNT_HOLDER_INFO";
    String CENTER_AUTH_USER_CI = "CENTER_AUTH_USER_CI";
    String CENTER_AUTH_USER_INFO = "CENTER_AUTH_USER_INFO";
    String CENTER_AUTH_USER_NAME = "CENTER_AUTH_USER_NAME";
    String CENTER_AUTH_USER_EMAIL = "CENTER_AUTH_USER_EMAIL";
    String CENTER_AUTH_REQ_CLIENT_NAME = "CENTER_AUTH_REQ_CLIENT_NAME";
    String CENTER_AUTH_REQ_CLIENT_BANK_CODE = "CENTER_AUTH_REQ_CLIENT_BANK_CODE";
    String CENTER_AUTH_REQ_CLIENT_ACCOUNT_NUM = "CENTER_AUTH_REQ_CLIENT_ACCOUNT_NUM";
    String CENTER_AUTH_BANK_TRAN_DATE = "CENTER_AUTH_BANK_TRAN_DATE";
    String CENTER_AUTH_ORG_BANK_TRAN_DATE = "CENTER_AUTH_ORG_BANK_TRAN_DATE";

    // 번들 데이터 key
    String BUNDLE_KEY_URI = "BUNDLE_KEY_URI";
    String BUNDLE_KEY_OAUTH_TYPE = "BUNDLE_KEY_OAUTH_TYPE";                                           // 사용자인증 or 계좌등록확인 구분
    String BUNDLE_DATA_OAUTH_TYPE_AUTHORIZE = "BUNDLE_DATA_OAUTH_TYPE_AUTHORIZE";                     // 사용자인증
    String BUNDLE_DATA_OAUTH_TYPE_AUTHORIZE_ACCOUNT = "BUNDLE_DATA_OAUTH_TYPE_AUTHORIZE_ACCOUNT";     // 계좌등록확인
    String BUNDLE_KEY_ACCESS_TOKEN = "BUNDLE_KEY_ACCESS_TOKEN";
    String BUNDLE_KEY_STATE = "BUNDLE_KEY_STATE";


    //=========================================== 사용자정의 설정 기본값 (사용자인증) - start ==========================================
    // ANW (AuthNewWeb) => 사용자인증 개선버전 의 이니셜
    // scope
    String ANW_SCOPE_TEST = "login transfer inquiry"; // 테스트
    String ANW_SCOPE_REAL = "login transfer inquiry"; // 운영

    // client_info
    String ANW_CLIENT_INFO_TEST = "[test] whatever you want";
    String ANW_CLIENT_INFO_REAL = "[prd] whatever you want";

    // state
    String ANW_STATE_TEST = "abcdefghijklmnopqrstuvwxyz123456";
    String ANW_STATE_REAL = "abcdefghijklmnopqrstuvwxyz123456";

    // lang
    String ANW_LANG_TEST = "kor";
    String ANW_LANG_REAL = "kor";

    // account_hole_auth_yn
    String ANW_ACCOUNT_HOLD_AUTH_YN_TEST = "N";
    String ANW_ACCOUNT_HOLD_AUTH_YN_REAL = "N";

    /*// edit_option
    String ANW_EDIT_OPTION_TEST = "on";
    String ANW_EDIT_OPTION_REAL = "on";

    // bg_color
    String ANW_BG_COLOR_TEST = "#FBEFF2";
    String ANW_BG_COLOR_REAL = "#FBEFF2";

    // txt_color
    String ANW_TXT_COLOR_TEST = "#088A08";
    String ANW_TXT_COLOR_REAL = "#088A08";

    // btn1_color
    String ANW_BTN1_COLOR_TEST = "#FF8000";
    String ANW_BTN1_COLOR_REAL = "#FF8000";

    // btn2_color
    String ANW_BTN2_COLOR_TEST = "#F3E2A9";
    String ANW_BTN2_COLOR_REAL = "#F3E2A9";*/


    // Kftc-Bfop-UserSeqNo
    String ANW_USER_SEQ_NO_TEST = "";
    String ANW_USER_SEQ_NO_REAL = "";

    // Kftc-Bfop-UserCI
    String ANW_USER_CI_TEST = "";
    String ANW_USER_CI_REAL = "";

    /*// Kftc-Bfop-UserName
    String ANW_USER_NAME_TEST = "김핀텍";
    String ANW_USER_NAME_REAL = "김핀텍";

    // Kftc-Bfop-UserInfo
    String ANW_USER_INFO_TEST = "197707071";
    String ANW_USER_INFO_REAL = "197707071";

    // Kftc-Bfop-PhoneCarrier
    String ANW_PHONE_CARRIER_TEST = "ktf";
    String ANW_PHONE_CARRIER_REAL = "ktf";

    // Kftc-Bfop-UserEmail
    String ANW_USER_EMAIL_TEST = "abc@inter.net";
    String ANW_USER_EMAIL_REAL = "abc@inter.net";


    // Kftc-Bfop-BankCodeStd
    String ANW_BANK_CODE_STD_TEST = "097";
    String ANW_BANK_CODE_STD_REAL = "097";

    // Kftc-Bfop-AccountNum
    String ANW_ACCOUNT_NUM_TEST = "1234567890";
    String ANW_ACCOUNT_NUM_REAL = "1234567890";*/

    //=========================================== 사용자정의 설정 기본값 (사용자인증) - end ============================================

}

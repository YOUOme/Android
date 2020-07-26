package com.kftc.openbankingsample2.biz.center_auth;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.biz.main.AbstractMainFragment;
import com.kftc.openbankingsample2.biz.main.MainActivity;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.AccessToken;
import com.kftc.openbankingsample2.common.data.BankAccount;
import com.kftc.openbankingsample2.common.util.Utils;

import java.util.List;

/**
 * 센터인증 프래그먼트 추상 클래스
 */
public class AbstractCenterAuthMainFragment extends AbstractMainFragment {

    // context
    private Context context;
    protected MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        // 메인액티빅티 개체 연결
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 메인액티빅티 개체 연결
        activity = (MainActivity) getActivity();

    }

    // 토큰선택창
    protected void showTokenDialog(EditText etToken) {
        showTokenDialog(etToken, null, null);
    }

    // 토큰선택창
    protected void showTokenDialog(EditText etToken, Scope scope) {
        showTokenDialog(etToken, null, scope);
    }

    // 토큰선택창
    protected void showTokenDialog(EditText etToken, EditText etUserSeqNo, Scope scope) {
        List<AccessToken> accessTokenList = AppData.getCenterAuthAccessTokenList(scope);
        ArrayAdapter<AccessToken> accessTokenAdapter = new ArrayAdapter<>(context, R.layout.simple_list_item_divider, R.id.text1, accessTokenList);
        showAlertToken(accessTokenAdapter, (parent, view, position, id) -> {
            AccessToken accessToken = accessTokenAdapter.getItem(position);
            if (accessToken != null) {
                etToken.setText(accessToken.getAccess_token());

                if (etUserSeqNo != null) {
                    etUserSeqNo.setText(accessToken.getUser_seq_no());
                }
            }
        });
    }

    /*
        사용자인증 토큰발급 수행시에만 해당. 필요한 데이터를 저장
        3-legged 응답형식
        {"access_token":"9c439c0c-32a2-4cd2-b0c3-74846b4828c8", "token_type":"Bearer",
        "expires_in":"7776000.0", "refresh_token":"f692......", "scope":"login transfer", "user_seq_no":"1100033516"}
        2-legged 응답형식
        {"access_token":"9c439c0c-32a2-4cd2-b0c3-74846b4828c8", "token_type":"Bearer",
         "expires_in":"7776000.0", "scope":"oob", "client_use_code":"201000"}
    */
    @Override
    protected void saveAuthData(String responseJson) {
        AccessToken accessToken = new Gson().fromJson(responseJson, AccessToken.class);
        if (accessToken.getAccess_token().isEmpty()) {
            return;
        }

        CenterAuthUtils.saveCenterAuthToken(accessToken);
        Toast.makeText(context, "센터인증 access token 저장됨", Toast.LENGTH_SHORT).show();

        // 다른 메뉴에서 user_seq_no 를 사용할 수 있도록 저장
        String userSeqNo = Utils.getValueFromJson(responseJson, "user_seq_no");
        Utils.saveData(CenterAuthConst.CENTER_AUTH_USER_SEQ_NO, userSeqNo);

        // 다른 메뉴에서 client_use_code 를 사용할 수 있도록 저장
        // 설정값에서 읽어오도록 변경(센터인증의 경우 은행거래고유번호를 생성하려면 미리 알아야 함)
        /*String clientUseCode = Utils.getValueFromJson(responseJson, "client_use_code");
        Utils.saveData(CenterAuthConst.CLIENT_USE_CODE, clientUseCode);*/
    }

    // 핀테크이용번호 선택창
    protected void showAccountDialog(EditText etFintechUseNum) {
        showAccountDialog(etFintechUseNum, null, null);
    }

    // 계좌번호 선택창
    protected void showAccountDialog(EditText etBankCode, EditText etAccountNum) {
        showAccountDialog(null, etBankCode, etAccountNum);
    }

    // 핀테크이용번호, 계좌번호 선택창
    protected void showAccountDialog(EditText etFintechUseNum, EditText etBankCode, EditText etAccountNum) {
        ArrayAdapter<BankAccount> bankAccountAdapter = new ArrayAdapter<>(context, R.layout.simple_list_item_divider, R.id.text1, AppData.centerAuthBankAccountList);
        showAlertAccount(bankAccountAdapter, (parent, view, position, id) -> {

            // 선택되면 해당 EditText 에 값을 입력.
            BankAccount bankAccount = bankAccountAdapter.getItem(position);
            if (bankAccount != null) {
                if (etFintechUseNum != null) {
                    etFintechUseNum.setText(bankAccount.getFintech_use_num());
                }

                if (etBankCode != null) {
                    etBankCode.setText(bankAccount.getBank_code_std());
                }

                if (etAccountNum != null) {
                    etAccountNum.setText(bankAccount.getAccountNum());
                }
            }
        });
    }

    // 은행거래고유번호(20자리)
    // 하루동안 유일성이 보장되어야함. 이용기관번호(10자리) + 생성주체구분코드(1자리, U:이용기관, O:오픈뱅킹) + 이용기관 부여번호(9자리)
    protected String setRandomBankTranId(EditText etBankTranId) {
        String clientUseCode = CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_USE_CODE);
        String randomUnique9String = Utils.getCurrentTime();    // 이용기관 부여번호를 임시로 시간데이터 사용
        String result = String.format("%sU%s", clientUseCode, randomUnique9String);
        if (etBankTranId != null) {
            etBankTranId.setText(result);
        }
        return result;
    }

    // 은행거래고유번호(20자리)
    protected String getRandomBankTranId() {
        return setRandomBankTranId(null);
    }
}

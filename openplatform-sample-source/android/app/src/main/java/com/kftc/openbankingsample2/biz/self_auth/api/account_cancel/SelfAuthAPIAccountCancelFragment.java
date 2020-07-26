package com.kftc.openbankingsample2.biz.self_auth.api.account_cancel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.http.SelfAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.util.Utils;

import java.util.HashMap;

/**
 * 계좌해지
 */
public class SelfAuthAPIAccountCancelFragment extends AbstractSelfAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_self_auth_api_account_cancel, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getSelfAuthAccessToken(Scope.SA));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.SA));

        // 은행거래고유번호(20자리)
        EditText etBankTranId = view.findViewById(R.id.etBankTranId);
        setRandomBankTranId(etBankTranId);
        view.findViewById(R.id.btnGenBankTranId).setOnClickListener(v -> setRandomBankTranId(etBankTranId));

        // 핀테크이용번호 : 최근 계좌로 기본 설정
        EditText etFintechUseNum = view.findViewById(R.id.etFintechUseNum);
        etFintechUseNum.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_FINTECH_USE_NUM));

        // 핀테크이용번호 : 기존 계좌에서 선택
        View.OnClickListener onClickListener = v -> showAccountDialog(etFintechUseNum);
        view.findViewById(R.id.btnSelectFintechUseNum).setOnClickListener(onClickListener);

        // scope
        EditText etScope = view.findViewById(R.id.etScope);
        view.findViewById(R.id.btnScope).setOnClickListener(v -> showScopeSingleDialog(etScope, AppData.scopeInqTranList, null));

        // 계좌해지 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken);
            String fintechUseNum = etFintechUseNum.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_FINTECH_USE_NUM, fintechUseNum);

            // 요청전문
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
            paramMap.put("scope", etScope.getText().toString().trim());
            paramMap.put("fintech_use_num", fintechUseNum);

            showProgress();
            SelfAuthApiRetrofitAdapter.getInstance()
                    .accountCancel("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse(null, "정상적으로 계좌가 해지되었습니다."));
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }
}

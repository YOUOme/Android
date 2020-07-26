package com.kftc.openbankingsample2.biz.self_auth.api.account_balance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * 잔액조회
 */
public class SelfAuthAPIAccountBalanceFragment extends AbstractSelfAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_self_auth_api_account_balance, container, false);
        initView();
        return view;
    }

    void initView() {

        // 핀테크이용번호 조회 or 계좌번호 조회 선택
        TableRow trFinTechUseNum = view.findViewById(R.id.trFinTechUseNum);
        TableRow trBankCode = view.findViewById(R.id.trBankCode);
        TableRow trAccountNum = view.findViewById(R.id.trAccountNum);
        TableRow trUserSeqNo = view.findViewById(R.id.trUserSeqNo);
        RadioGroup rgFintechAccountType = view.findViewById(R.id.rgFintechAccountType);
        RadioButton rbFintechNum = view.findViewById(R.id.rbFintechNum);
        rgFintechAccountType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbFintechNum:
                    trFinTechUseNum.setVisibility(View.VISIBLE);
                    trBankCode.setVisibility(View.GONE);
                    trAccountNum.setVisibility(View.GONE);
                    trUserSeqNo.setVisibility(View.GONE);
                    break;
                case R.id.rbAccountNum:
                    trFinTechUseNum.setVisibility(View.GONE);
                    trBankCode.setVisibility(View.VISIBLE);
                    trAccountNum.setVisibility(View.VISIBLE);
                    trUserSeqNo.setVisibility(View.VISIBLE);
                    break;
            }
        });

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
        // 계좌번호 : 최근 계좌로 기본 설정
        EditText etFintechUseNum = view.findViewById(R.id.etFintechUseNum);
        etFintechUseNum.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_FINTECH_USE_NUM));
        EditText etBankCode = view.findViewById(R.id.etBankCode);
        etBankCode.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_BANK_CODE));
        EditText etAccountNum = view.findViewById(R.id.etAccountNum);
        etAccountNum.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_ACCOUNT_NUM));

        // 핀테크이용번호 : 기존 계좌에서 선택
        // 계좌번호 : 기존 계좌에서 선택
        View.OnClickListener onClickListener = v -> showAccountDialog(etFintechUseNum, etBankCode, etAccountNum);
        view.findViewById(R.id.btnSelectFintechUseNum).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnSelectBankAccount).setOnClickListener(onClickListener);

        // user_seq_no : 가장 최근 사용자 일련번호로 기본 설정
        EditText etUserSeqNo = view.findViewById(R.id.etUserSeqNo);
        etUserSeqNo.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_USER_SEQ_NO));

        // 거래일시
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // 잔액조회 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken);
            String fintechUseNum = etFintechUseNum.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_FINTECH_USE_NUM, fintechUseNum);
            String bankCode = etBankCode.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_BANK_CODE, bankCode);
            String accountNum = etAccountNum.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCOUNT_NUM, accountNum);
            String userSeqNo = etUserSeqNo.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_USER_SEQ_NO, userSeqNo);

            // 요청전문
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
            paramMap.put("tran_dtime", etTranDtime.getText().toString());

            showProgress();

            // 핀테크이용번호 사용
            if (rbFintechNum.isChecked()) {
                paramMap.put("fintech_use_num", fintechUseNum);
                SelfAuthApiRetrofitAdapter.getInstance()
                        .accountBalanceFinNum("Bearer " + accessToken, paramMap)
                        .enqueue(super.handleResponse("balance_amt", "잔액"));
            }

            // 계좌번호 사용
            else {
                paramMap.put("bank_code_std", bankCode);
                paramMap.put("account_num", accountNum);
                paramMap.put("user_seq_no", userSeqNo);
                SelfAuthApiRetrofitAdapter.getInstance()
                        .accountBalanceAcntNum("Bearer " + accessToken, paramMap)
                        .enqueue(super.handleResponse("balance_amt", "잔액"));
            }
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }
}

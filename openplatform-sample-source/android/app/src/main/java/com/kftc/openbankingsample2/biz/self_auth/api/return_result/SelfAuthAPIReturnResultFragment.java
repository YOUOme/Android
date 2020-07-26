package com.kftc.openbankingsample2.biz.self_auth.api.return_result;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
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
 * 자금반환결과조회
 */
public class SelfAuthAPIReturnResultFragment extends AbstractSelfAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_center_auth_api_return_result, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getSelfAuthAccessToken(Scope.SA));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.SA));

        // 청구요청 거래일자
        EditText etBankTranDate = view.findViewById(R.id.etBankTranDate);
        etBankTranDate.setText(Utils.getSavedValueOrDefault(SelfAuthConst.SELF_AUTH_BANK_TRAN_DATE, etBankTranDate.getText().toString()));

        // 청구요청 거래고유번호
        String clientUseCode = SelfAuthUtils.getSavedValueFromSetting(SelfAuthConst.SELF_AUTH_CLIENT_USE_CODE);
        String unique9String = "123456789";    // 이용기관 부여번호를 시뮬레이터 데이터로 채움
        EditText etBankTranId = view.findViewById(R.id.etBankTranId);
        etBankTranId.setText(String.format("%sU%s", clientUseCode, unique9String));

        // 자금반환 결과조회 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken);
            String bankTranDate = etBankTranDate.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_BANK_TRAN_DATE, bankTranDate);

            // 요청전문
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("claim_bank_tran_date", bankTranDate);
            paramMap.put("claim_bank_tran_id", etBankTranId.getText().toString());

            showProgress();
            SelfAuthApiRetrofitAdapter.getInstance()
                    .returnResult("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("claim_normal_yn", "청구요청 정상여부"));

        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }

}

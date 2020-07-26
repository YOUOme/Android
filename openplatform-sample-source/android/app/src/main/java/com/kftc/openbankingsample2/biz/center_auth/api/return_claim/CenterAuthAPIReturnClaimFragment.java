package com.kftc.openbankingsample2.biz.center_auth.api.return_claim;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.util.TwoString;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.KmUtilMoneyEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 자금반환청구
 */
public class CenterAuthAPIReturnClaimFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String claimCode;
    private String totalReturnYn;

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
        view = inflater.inflate(R.layout.fragment_center_auth_api_return_claim, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 액세스 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getCenterAuthAccessToken(Scope.OOB));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.OOB));

        // 은행거래고유번호(20자리)
        EditText etBankTranId = view.findViewById(R.id.etBankTranId);
        setRandomBankTranId(etBankTranId);
        view.findViewById(R.id.btnGenBankTranId).setOnClickListener(v -> setRandomBankTranId(etBankTranId));

        // 원거래 거래일자
        EditText etOrgBankTranDate = view.findViewById(R.id.etOrgBankTranDate);
        etOrgBankTranDate.setText(Utils.getSavedValueOrDefault(CenterAuthConst.CENTER_AUTH_ORG_BANK_TRAN_DATE, etOrgBankTranDate.getText().toString()));

        // 원거래 거래고유번호
        String clientUseCode = CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_USE_CODE);
        String unique9String = "123456789";    // 이용기관 부여번호를 시뮬레이터 데이터로 채움
        EditText etOrgBankTranId = view.findViewById(R.id.etOrgBankTranId);
        etOrgBankTranId.setText(String.format("%sU%s", clientUseCode, unique9String));

        // 원거래 입금기관 표준코드
        EditText etOrgDpsBankCodeStd = view.findViewById(R.id.etOrgDpsBankCodeStd);
        etOrgDpsBankCodeStd.setText("097");

        // 원거래 입금계좌번호
        EditText etOrgDpsAccountNum = view.findViewById(R.id.etOrgDpsAccountNum);
        etOrgDpsAccountNum.setText("123456789");

        // 거래금액(청구금액)
        KmUtilMoneyEditText moneyOrgTranAmt = view.findViewById(R.id.moneyOrgTranAmt);

        // 원거래 출금기관 표준코드
        EditText etOrgWdBankCodeStd = view.findViewById(R.id.etOrgWdBankCodeStd);
        etOrgWdBankCodeStd.setText("097");

        // 청구사유코드
        List<TwoString> claimCodeMenuList = new ArrayList<>();
        claimCodeMenuList.add(new TwoString("02(계좌입력오류)", "02"));
        claimCodeMenuList.add(new TwoString("03(금액입력오류)", "03"));
        claimCodeMenuList.add(new TwoString("04(이중입금)", "04"));
        claimCodeMenuList.add(new TwoString("05(기타)", "05"));
        claimCodeMenuList.add(new TwoString("06(고객반환의사)", "06"));
        AppCompatSpinner spClaimCode = view.findViewById(R.id.spClaimCode);
        ArrayAdapter<TwoString> claimCodeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, claimCodeMenuList);
        spClaimCode.setAdapter(claimCodeAdapter);
        spClaimCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = claimCodeAdapter.getItem(position);
                if (twoString != null) {
                    claimCode = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 청구사유
        EditText etClaimMessage = view.findViewById(R.id.etClaimMessage);
        etClaimMessage.setText("계좌입력오류로 자금반환청구");

        // 전액반환여부
        List<TwoString> totalReturnYnMenuList = new ArrayList<>();
        totalReturnYnMenuList.add(new TwoString("Y(전액반환)", "Y"));
        totalReturnYnMenuList.add(new TwoString("N(일부반환)", "N"));
        AppCompatSpinner spTotalReturnYn = view.findViewById(R.id.spTotalReturnYn);
        ArrayAdapter<TwoString> totalReturnYnAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, totalReturnYnMenuList);
        spTotalReturnYn.setAdapter(totalReturnYnAdapter);
        spTotalReturnYn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = totalReturnYnAdapter.getItem(position);
                if (twoString != null) {
                    totalReturnYn = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 반환금액 입금계좌
        EditText etReturnAccountNum = view.findViewById(R.id.etReturnAccountNum);
        etReturnAccountNum.setText("123456789");

        // 이용기관 담당자 연락처
        EditText etUseOrgContact = view.findViewById(R.id.etUseOrgContact);
        etUseOrgContact.setText("02-531-1496");

        // 이용기관 담당자 이메일주소
        EditText etUseOrgEmail = view.findViewById(R.id.etUseOrgEmail);
        etUseOrgEmail.setText("kftc.and@gmail.com");

        // 자금반환 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken);
            String orgBankTranDate = etOrgBankTranDate.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ORG_BANK_TRAN_DATE, orgBankTranDate);

            // 요청전문
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
            paramMap.put("org_bank_tran_date", orgBankTranDate);
            paramMap.put("org_bank_tran_id", etOrgBankTranId.getText().toString());
            paramMap.put("org_dps_bank_code_std", etOrgDpsBankCodeStd.getText().toString());
            paramMap.put("org_dps_account_num", etOrgDpsAccountNum.getText().toString());
            paramMap.put("org_tran_amt",  moneyOrgTranAmt.getTextString());       // 쉼표(,)를 제외하고 추출
            paramMap.put("org_wd_bank_code_std", etOrgWdBankCodeStd.getText().toString());
            paramMap.put("claim_code", claimCode);
            paramMap.put("claim_message", etClaimMessage.getText().toString());
            paramMap.put("total_return_yn", totalReturnYn);
            paramMap.put("return_account_num", etReturnAccountNum.getText().toString());
            paramMap.put("use_org_contact", etUseOrgContact.getText().toString());
            paramMap.put("use_org_email", etUseOrgEmail.getText().toString());

            showProgress();
            CenterAuthApiRetrofitAdapter.getInstance()
                    .returnClaim("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("org_tran_amt", "원거래 거래금액"));
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }
}

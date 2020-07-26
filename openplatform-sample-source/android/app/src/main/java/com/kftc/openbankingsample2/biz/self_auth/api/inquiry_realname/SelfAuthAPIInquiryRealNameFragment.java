package com.kftc.openbankingsample2.biz.self_auth.api.inquiry_realname;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.http.SelfAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.util.TwoString;
import com.kftc.openbankingsample2.common.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 계좌실명조회
 */
public class SelfAuthAPIInquiryRealNameFragment extends AbstractSelfAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String accountHolderInfoType;       // 예금주 실명번호 구분코드
    private int accountHolderInfoTypePosition = -1;  // 예금주 실명번호 구분코드 위치

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
        view = inflater.inflate(R.layout.fragment_self_auth_api_inquiry_realname, container, false);
        initData();
        initView();
        return view;
    }

    void initData() {
        accountHolderInfoTypePosition = Utils.getSavedIntValue(SelfAuthConst.SELF_AUTH_ACCOUNT_HOLDER_INFO_TYPE_POSITION);
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

        // 계좌번호 : 최근 계좌로 기본 설정
        EditText etBankCode = view.findViewById(R.id.etBankCode);
        etBankCode.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_BANK_CODE));
        EditText etAccountNum = view.findViewById(R.id.etAccountNum);
        etAccountNum.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_ACCOUNT_NUM));

        // 계좌번호 : 기존 계좌에서 선택
        view.findViewById(R.id.btnSelectBankAccount).setOnClickListener(v -> showAccountDialog(etBankCode, etAccountNum));

        // 예금주 실명번호 구분코드 : 드랍다운 메뉴
        List<TwoString> accountHolderInfoTypeMenuList = new ArrayList<>();
        accountHolderInfoTypeMenuList.add(new TwoString("SPACE(생년월일 6자리)", " "));
        accountHolderInfoTypeMenuList.add(new TwoString("1(주민등록번호)", "1"));
        accountHolderInfoTypeMenuList.add(new TwoString("2(외국인등록번호)", "2"));
        accountHolderInfoTypeMenuList.add(new TwoString("3(국내거소신고번호)", "3"));
        accountHolderInfoTypeMenuList.add(new TwoString("4(조합주민번호)", "4"));
        accountHolderInfoTypeMenuList.add(new TwoString("5(여권번호)", "5"));
        accountHolderInfoTypeMenuList.add(new TwoString("6(사업자등록번호)", "6"));
        accountHolderInfoTypeMenuList.add(new TwoString("E(기타)", "E"));
        accountHolderInfoTypeMenuList.add(new TwoString("N(검사생략)", "N"));
        AppCompatSpinner spInquiryType = view.findViewById(R.id.spAccountHolderInfoType);
        ArrayAdapter<TwoString> inquiryTypeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, accountHolderInfoTypeMenuList);
        spInquiryType.setAdapter(inquiryTypeAdapter);
        if (accountHolderInfoTypePosition >= 0) {
            spInquiryType.setSelection(accountHolderInfoTypePosition);
        }
        spInquiryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = inquiryTypeAdapter.getItem(position);
                if (twoString != null) {
                    accountHolderInfoType = twoString.getSecond();
                    accountHolderInfoTypePosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 예금주 실명번호 구분코드 : 최근값으로 기본 설정
        spInquiryType.setSelection(Utils.getSavedIntValue(SelfAuthConst.SELF_AUTH_ACCOUNT_HOLDER_INFO_TYPE_POSITION));

        // 예금주 실명번호 : 최근값으로 기본 설정
        EditText etAccountHolderInfo = view.findViewById(R.id.etAccountHolderInfo);
        etAccountHolderInfo.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_ACCOUNT_HOLDER_INFO));

        // 요청일시
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // 계좌실명조회 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken);
            String bankCode = etBankCode.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_BANK_CODE, bankCode);
            String accountNum = etAccountNum.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCOUNT_NUM, accountNum);
            Utils.saveIntData(SelfAuthConst.SELF_AUTH_ACCOUNT_HOLDER_INFO_TYPE_POSITION, accountHolderInfoTypePosition);
            String accountHolderInfo = etAccountHolderInfo.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCOUNT_HOLDER_INFO, accountHolderInfo);

            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
            paramMap.put("bank_code_std", bankCode);
            paramMap.put("account_num", accountNum);
            paramMap.put("account_holder_info_type", accountHolderInfoType);
            paramMap.put("account_holder_info", accountHolderInfo);
            paramMap.put("tran_dtime", etTranDtime.getText().toString());

            showProgress();
            SelfAuthApiRetrofitAdapter.getInstance()
                    .inquiryRealName("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("account_holder_name", "예금주 성명"));
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }


}

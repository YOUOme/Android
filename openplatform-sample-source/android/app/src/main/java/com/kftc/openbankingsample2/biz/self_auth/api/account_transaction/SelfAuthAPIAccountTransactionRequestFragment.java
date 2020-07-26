package com.kftc.openbankingsample2.biz.self_auth.api.account_transaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.gson.Gson;
import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.http.SelfAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.ApiCallAccountTransactionResponse;
import com.kftc.openbankingsample2.common.util.TwoString;
import com.kftc.openbankingsample2.common.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 거래내역조회 요청
 */
public class SelfAuthAPIAccountTransactionRequestFragment extends AbstractSelfAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String inquiryType;     // 조회구분코드
    private String inquiryBase;     // 조회기준코드
    private String sortOrder;       // 정렬순서

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
        view = inflater.inflate(R.layout.fragment_self_auth_api_account_transaction_request, container, false);
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

        // 조회구분코드 드랍다운 메뉴
        List<TwoString> inquiryTypeMenuList = new ArrayList<>();
        inquiryTypeMenuList.add(new TwoString("A(All)", "A"));
        inquiryTypeMenuList.add(new TwoString("I(입금)", "I"));
        inquiryTypeMenuList.add(new TwoString("O(출금)", "O"));
        AppCompatSpinner spInquiryType = view.findViewById(R.id.spInquiryType);
        ArrayAdapter<TwoString> inquiryTypeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, inquiryTypeMenuList);
        spInquiryType.setAdapter(inquiryTypeAdapter);
        spInquiryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = inquiryTypeAdapter.getItem(position);
                if (twoString != null) {
                    inquiryType = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 조회기준코드 드랍다운 메뉴
        List<TwoString> inquiryBaseMenuList = new ArrayList<>();
        inquiryBaseMenuList.add(new TwoString("D(일자)", "D"));
        inquiryBaseMenuList.add(new TwoString("T(시간)", "T"));
        AppCompatSpinner spInquiryBase = view.findViewById(R.id.spInquiryBase);
        ArrayAdapter<TwoString> inquiryBaseAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, inquiryBaseMenuList);
        spInquiryBase.setAdapter(inquiryBaseAdapter);
        spInquiryBase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = inquiryBaseAdapter.getItem(position);
                if (twoString != null) {
                    inquiryBase = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 조회시작일자
        EditText etFromDate = view.findViewById(R.id.etFromDate);
        etFromDate.setText(Utils.getDateString8(-60));

        // 조회시작시간
        EditText etFromTime = view.findViewById(R.id.etFromTime);

        // 조회종료일자
        EditText etToDate = view.findViewById(R.id.etToDate);
        etToDate.setText(Utils.getDateString8(0));

        // 조회종료시간
        EditText etToTime = view.findViewById(R.id.etToTime);

        // 정렬순서
        List<TwoString> sortOrderMenuList = new ArrayList<>();
        sortOrderMenuList.add(new TwoString("D(Descending)", "D"));
        sortOrderMenuList.add(new TwoString("A(Ascending)", "A"));
        AppCompatSpinner spSortOrder = view.findViewById(R.id.spSortOrder);
        ArrayAdapter<TwoString> sortOrderAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, sortOrderMenuList);
        spSortOrder.setAdapter(sortOrderAdapter);
        spSortOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = sortOrderAdapter.getItem(position);
                if (twoString != null) {
                    sortOrder = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 요청일시
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // 직전조회추적번호
        EditText etBeforInquiryTraceInfo = view.findViewById(R.id.etBeforInquiryTraceInfo);
        etBeforInquiryTraceInfo.setText("123");

        // 거래내역조회 요청
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
            paramMap.put("inquiry_type", inquiryType);
            paramMap.put("inquiry_base", inquiryBase);
            paramMap.put("from_date", etFromDate.getText().toString().trim());
            paramMap.put("from_time", etFromTime.getText().toString().trim());
            paramMap.put("to_date", etToDate.getText().toString().trim());
            paramMap.put("to_time", etToTime.getText().toString().trim());
            paramMap.put("sort_order", sortOrder);
            paramMap.put("tran_dtime", etTranDtime.getText().toString());
            paramMap.put("befor_inquiry_trace_info", etBeforInquiryTraceInfo.getText().toString());

            showProgress();

            // 핀테크이용번호 사용
            if (rbFintechNum.isChecked()) {
                paramMap.put("fintech_use_num", fintechUseNum);
                SelfAuthApiRetrofitAdapter.getInstance()
                        .accountTrasactionListFinNum("Bearer " + accessToken, paramMap)
                        .enqueue(super.handleResponse("page_record_cnt", "현재페이지 조회건수", responseJson -> {

                            // 성공하면 결과화면으로 이동
                            ApiCallAccountTransactionResponse result =
                                    new Gson().fromJson(responseJson, ApiCallAccountTransactionResponse.class);
                            args.putParcelable("result", result);
                            args.putSerializable("request", paramMap);
                            args.putString(SelfAuthConst.BUNDLE_KEY_ACCESS_TOKEN, accessToken);
                            args.putBoolean(SelfAuthConst.BUNDLE_KEY_IS_FINTECH_USE_NUM, true);
                            goNext();
                        })
                );
            }

            // 계좌번호 사용(자체인증)
            else {
                paramMap.put("bank_code_std", bankCode);
                paramMap.put("account_num", accountNum);
                paramMap.put("user_seq_no", userSeqNo);
                SelfAuthApiRetrofitAdapter.getInstance()
                        .accountTrasactionListAcntNum("Bearer " + accessToken, paramMap)
                        .enqueue(super.handleResponse("page_record_cnt", "현재페이지 조회건수", responseJson -> {

                            // 성공하면 결과화면으로 이동
                            ApiCallAccountTransactionResponse result =
                                    new Gson().fromJson(responseJson, ApiCallAccountTransactionResponse.class);
                            args.putParcelable("result", result);
                            args.putSerializable("request", paramMap);
                            args.putString(SelfAuthConst.BUNDLE_KEY_ACCESS_TOKEN, accessToken);
                            args.putBoolean(SelfAuthConst.BUNDLE_KEY_IS_FINTECH_USE_NUM, false);
                            goNext();
                        })
                );
            }
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }

    void goNext() {
        startFragment(SelfAuthAPIAccountTransactionResultFragment.class, args, R.string.fragment_id_api_call_transaction);
    }

}

package com.kftc.openbankingsample2.biz.center_auth.api.inquiry_remitlist;

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

import com.google.gson.Gson;
import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.api.account_transaction.CenterAuthAPIAccountTransactionResultFragment;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
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
 * 송금인 정보조회
 */
public class CenterAuthAPIInquiryRemitListFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String sortOrder;

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
        view = inflater.inflate(R.layout.fragment_center_auth_api_inquiry_remitlist, container, false);
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

        // 계좌번호 : 최근 계좌로 기본 설정
        EditText etBankCode = view.findViewById(R.id.etBankCode);
        etBankCode.setText(Utils.getSavedValue(CenterAuthConst.CENTER_AUTH_BANK_CODE));
        EditText etAccountNum = view.findViewById(R.id.etAccountNum);
        etAccountNum.setText(Utils.getSavedValue(CenterAuthConst.CENTER_AUTH_ACCOUNT_NUM));

        // 계좌번호 : 기존 계좌에서 선택
        view.findViewById(R.id.btnSelectBankAccount).setOnClickListener(v -> showAccountDialog(etBankCode, etAccountNum));

        // 조회시작일자
        EditText etFromDate = view.findViewById(R.id.etFromDate);
        etFromDate.setText(Utils.getDateString8(-60));

        // 조회시작시간
        EditText etFromTime = view.findViewById(R.id.etFromTime);
        etFromTime.setText("143000");

        // 조회종료일자
        EditText etToDate = view.findViewById(R.id.etToDate);
        etToDate.setText(Utils.getDateString8(0));

        // 조회종료시간
        EditText etToTime = view.findViewById(R.id.etToTime);
        etToTime.setText("223000");

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

        // 직전조회추적번호
        EditText etBeforInquiryTraceInfo = view.findViewById(R.id.etBeforInquiryTraceInfo);
        etBeforInquiryTraceInfo.setText("123");

        // 요청일시
        EditText etTranDtime = view.findViewById(R.id.etTranDtime);
        etTranDtime.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()));

        // 송금인정보조회 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken);
            String bankCode = etBankCode.getText().toString();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_BANK_CODE, bankCode);
            String accountNum = etAccountNum.getText().toString();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCOUNT_NUM, accountNum);

            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("bank_tran_id", etBankTranId.getText().toString());
            paramMap.put("bank_code_std", bankCode);
            paramMap.put("account_num", accountNum);
            paramMap.put("from_date", etFromDate.getText().toString().trim());
            paramMap.put("from_time", etFromTime.getText().toString().trim());
            paramMap.put("to_date", etToDate.getText().toString().trim());
            paramMap.put("to_time", etToTime.getText().toString().trim());
            paramMap.put("sort_order", sortOrder);
            paramMap.put("befor_inquiry_trace_info", etBeforInquiryTraceInfo.getText().toString());
            paramMap.put("tran_dtime", etTranDtime.getText().toString());

            showProgress();
            CenterAuthApiRetrofitAdapter.getInstance()
                    .inquiryRemitList("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("page_record_cnt", "현재페이지 조회건수", responseJson -> {

                        // 성공하면 결과화면으로 이동
                        ApiCallAccountTransactionResponse result =
                                new Gson().fromJson(responseJson, ApiCallAccountTransactionResponse.class);
                        args.putParcelable("result", result);
                        args.putSerializable("request", paramMap);
                        args.putString(CenterAuthConst.BUNDLE_KEY_ACCESS_TOKEN, accessToken);
                        goNext();
                    })
            );
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }

    void goNext() {
        startFragment(CenterAuthAPIAccountTransactionResultFragment.class, args, R.string.fragment_id_api_call_transaction);
    }

}

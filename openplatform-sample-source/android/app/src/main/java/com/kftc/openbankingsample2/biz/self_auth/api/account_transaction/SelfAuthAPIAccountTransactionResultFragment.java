package com.kftc.openbankingsample2.biz.self_auth.api.account_transaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.api.SelfAuthAPIFragment;
import com.kftc.openbankingsample2.biz.self_auth.http.SelfAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.data.ApiCallAccountTransactionResponse;
import com.kftc.openbankingsample2.common.data.ResMsg;
import com.kftc.openbankingsample2.common.data.Transaction;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.recyclerview.KmRecyclerViewDividerHeight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 거래내역조회 결과
 */
public class SelfAuthAPIAccountTransactionResultFragment extends AbstractSelfAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;
    private RecyclerView recyclerView;
    private SelfAuthAPIAccountTransactionResultAdapter adapter;
    private TextView tvTotalRecordCnt;

    // data
    private Bundle args;
    private ApiCallAccountTransactionResponse result;
    private boolean isNextPage = false;         // 다음페이지 존재여부(1페이지당 25건만 조회)
    private String beforInquiryTraceInfo = "";  // 직전조회 추적정보
    private HashMap<String, String> paramMap;
    private String accessToken;
    private int totalRecordCnt;
    private boolean isFintechUseNum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();

        result = args.getParcelable("result");
        paramMap = (HashMap<String, String>) args.getSerializable("request");
        accessToken = args.getString(SelfAuthConst.BUNDLE_KEY_ACCESS_TOKEN, "");
        isFintechUseNum = args.getBoolean(SelfAuthConst.BUNDLE_KEY_IS_FINTECH_USE_NUM, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_self_auth_api_account_transaction_result, container, false);
        initView();
        return view;
    }

    void initView() {

        // 상단 정보
        totalRecordCnt = result.getPageRecordCntInt();
        tvTotalRecordCnt = view.findViewById(R.id.tvTotalRecordCnt);
        tvTotalRecordCnt.setText(String.format("%s건", Utils.moneyForm(totalRecordCnt)));

        // 거래내역(반복부)
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new KmRecyclerViewDividerHeight(30));

        view.findViewById(R.id.btnNext).setOnClickListener(v -> goNext());

        initData();
    }

    void initData() {

        // 다음 데이터가 더 있는지 확인
        beforInquiryTraceInfo = result.getBefor_inquiry_trace_info();
        isNextPage = result.isNextPage();

        // 리사이클러뷰에 어댑터 설정
        adapter = new SelfAuthAPIAccountTransactionResultAdapter(result.getRes_list());

        // 바닥까지 스크롤하면 다음 데이터 요청
        adapter.setBottomReachedListener(position -> {
            if (isNextPage /*&& !TextUtils.isEmpty(beforInquiryTraceInfo)*/) {
                requestMoreDate();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    // 거래내역조회 요청
    void requestMoreDate() {

        paramMap.put("bank_tran_id", getRandomBankTranId());
        paramMap.put("befor_inquiry_trace_info", beforInquiryTraceInfo);

        showProgress();

        // 핀테크이용번호 사용
        if (isFintechUseNum) {

            // 거래내역조회 요청
            SelfAuthApiRetrofitAdapter.getInstance()
                    .accountTrasactionListFinNum("Bearer " + accessToken, paramMap)
                    .enqueue(handleResponse);
        }

        // 계좌번호 사용(자체인증)
        else {

            // 거래내역조회 요청
            SelfAuthApiRetrofitAdapter.getInstance()
                    .accountTrasactionListAcntNum("Bearer " + accessToken, paramMap)
                    .enqueue(handleResponse);
        }
    }

    // 결과처리
    Callback<Map> handleResponse = new Callback<Map>() {
        @Override
        public void onResponse(Call<Map> call, Response<Map> response) {
            hideProgress();

            // http ok(200) 아닐경우
            if (!response.isSuccessful()) {
                handleHttpFailure(response);
                return;
            }

            String responseJson = new Gson().toJson(response.body());

            // json 안에 있는 응답코드를 확인
            ResMsg resMsg = new Gson().fromJson(responseJson, ResMsg.class);
            if (!resMsg.isSuccess()) {
                handleApiFailure(resMsg, responseJson);
                return;
            }

            // 응답메시지 파싱
            ApiCallAccountTransactionResponse nextResult =
                    new Gson().fromJson(responseJson, ApiCallAccountTransactionResponse.class);
            isNextPage = nextResult.isNextPage();
            beforInquiryTraceInfo = nextResult.getBefor_inquiry_trace_info();


            // 반복부 파싱
            ArrayList<Transaction> transactionList = nextResult.getRes_list();
            if (transactionList != null) {
                for (Transaction transaction : transactionList) {
                    adapter.addItem(transaction);
                }
                totalRecordCnt += nextResult.getPageRecordCntInt();
            }

            tvTotalRecordCnt.setText(String.format("%s건", Utils.moneyForm(totalRecordCnt)));
        }

        @Override
        public void onFailure(Call<Map> call, Throwable t) {
            hideProgress();
            showAlert("통신실패", "서버 접속에 실패하였습니다.", t.getMessage());
        }
    };

    void goNext() {
        startFragment(SelfAuthAPIFragment.class, null, R.string.fragment_id_self_api_call);
    }
}

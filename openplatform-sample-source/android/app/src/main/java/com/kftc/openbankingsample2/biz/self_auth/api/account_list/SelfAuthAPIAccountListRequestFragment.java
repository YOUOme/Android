package com.kftc.openbankingsample2.biz.self_auth.api.account_list;

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
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.http.SelfAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.ApiCallUserMeResponse;
import com.kftc.openbankingsample2.common.util.TwoString;
import com.kftc.openbankingsample2.common.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 등록계좌조회
 */
public class SelfAuthAPIAccountListRequestFragment extends AbstractSelfAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String includeCancelYn; // 해지계좌포함 여부
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
        view = inflater.inflate(R.layout.fragment_self_auth_api_account_list_request, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getSelfAuthAccessToken(Scope.SA));

        // user_seq_no : 가장 최근 사용자 일련번호로 기본 설정
        EditText etUserSeqNo = view.findViewById(R.id.etUserSeqNo);
        etUserSeqNo.setText(Utils.getSavedValue(SelfAuthConst.SELF_AUTH_USER_SEQ_NO));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, etUserSeqNo, Scope.SA));

        // 해지계좌포함 여부
        List<TwoString> includeCancelYnMenuList = new ArrayList<>();
        includeCancelYnMenuList.add(new TwoString("Y(해지계좌포함)", "Y"));
        includeCancelYnMenuList.add(new TwoString("N(해지계좌불포함)", "N"));
        AppCompatSpinner spIncludeCancelYn = view.findViewById(R.id.spIncludeCancelYn);
        ArrayAdapter<TwoString> includeCancelYnadapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, includeCancelYnMenuList);
        spIncludeCancelYn.setAdapter(includeCancelYnadapter);
        spIncludeCancelYn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TwoString twoString = includeCancelYnadapter.getItem(position);
                if (twoString != null) {
                    includeCancelYn = twoString.getSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        // 등록계좌 조회
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken);
            String userSeqNo = etUserSeqNo.getText().toString();
            Utils.saveData(SelfAuthConst.SELF_AUTH_USER_SEQ_NO, userSeqNo);

            // 요청전문
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("user_seq_no", userSeqNo);

            showProgress();
            SelfAuthApiRetrofitAdapter.getInstance()
                    .accountList("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("res_cnt", "등록계좌수", responseJson -> {

                        // 성공하면 결과화면으로 이동
                        ApiCallUserMeResponse result = new Gson().fromJson(responseJson, ApiCallUserMeResponse.class);
                        args.putParcelable("result", result);
                        goNext();
                    }));

        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }

    void goNext() {
        startFragment(SelfAuthAPIAccountListResultFragment.class, args, R.string.fragment_id_api_call_account);
    }
}

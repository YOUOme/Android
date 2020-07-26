package com.kftc.openbankingsample2.biz.self_auth.api.inquiry_fdsresult;

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
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * 이상금융거래 탐지내역 조회
 */
public class SelfAuthAPIInquiryFdsDetectFragment extends AbstractSelfAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_self_auth_api_inquiry_fdsdetect, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getSelfAuthAccessToken(Scope.SA));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, Scope.SA));

        // 조회일자
        EditText etInquiryDate = view.findViewById(R.id.etInquiryDate);
        etInquiryDate.setText(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));

        // 조회시작시간
        EditText etFromTime = view.findViewById(R.id.etFromTime);
        etFromTime.setText(Utils.getTimeString8(-11));

        // 조회종료시간
        EditText etToTime = view.findViewById(R.id.etToTime);
        etToTime.setText(Utils.getTimeString8(-1));

        // 자금반환 결과조회 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(SelfAuthConst.SELF_AUTH_ACCESS_TOKEN, accessToken);

            // 요청전문
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("inquiry_date", etInquiryDate.getText().toString());
            paramMap.put("from_time", etFromTime.getText().toString().trim());
            paramMap.put("to_time", etToTime.getText().toString().trim());

            showProgress();
            SelfAuthApiRetrofitAdapter.getInstance()
                    .inquiryFdsDetect("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("res_cnt", "이상거래 탐지건수"));

        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());

    }
}

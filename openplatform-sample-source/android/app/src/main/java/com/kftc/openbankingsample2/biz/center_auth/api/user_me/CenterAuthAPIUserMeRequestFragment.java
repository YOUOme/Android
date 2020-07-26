package com.kftc.openbankingsample2.biz.center_auth.api.user_me;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.common.Scope;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.data.ApiCallUserMeResponse;
import com.kftc.openbankingsample2.common.util.Utils;

import java.util.HashMap;

/**
 * 사용자정보조회 요청
 */
public class CenterAuthAPIUserMeRequestFragment extends AbstractCenterAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_center_auth_api_user_me_request, container, false);
        initView();
        return view;
    }

    void initView() {

        // access_token : 가장 최근 액세스 토큰으로 기본 설정
        EditText etToken = view.findViewById(R.id.etToken);
        etToken.setText(AppData.getCenterAuthAccessToken(Scope.LOGIN));

        // user_seq_no : 가장 최근 사용자 일련번호로 기본 설정
        EditText etUserSeqNo = view.findViewById(R.id.etUserSeqNo);
        etUserSeqNo.setText(Utils.getSavedValue(CenterAuthConst.CENTER_AUTH_USER_SEQ_NO));

        // access_token : 기존 토큰에서 선택
        view.findViewById(R.id.btnSelectToken).setOnClickListener(v -> showTokenDialog(etToken, etUserSeqNo, Scope.LOGIN));

        // 사용자정보 조회
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {

            // 직전내용 저장
            String accessToken = etToken.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_ACCESS_TOKEN, accessToken);
            String userSeqNo = etUserSeqNo.getText().toString().trim();
            Utils.saveData(CenterAuthConst.CENTER_AUTH_USER_SEQ_NO, userSeqNo);

            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("user_seq_no", userSeqNo);

            showProgress();
            CenterAuthApiRetrofitAdapter.getInstance()
                    .userMe("Bearer " + accessToken, paramMap)
                    .enqueue(super.handleResponse("res_cnt", "등록계좌수", responseJson -> {

                        // 성공하면 결과화면으로 이동
                        ApiCallUserMeResponse result = new Gson().fromJson(responseJson, ApiCallUserMeResponse.class);
                        args.putParcelable("result", result);
                        goNext();
                    })
            );
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());

    }

    void goNext() {
        startFragment(CenterAuthAPIUserMeResultFragment.class, args, R.string.fragment_id_api_call_userme);
    }
}

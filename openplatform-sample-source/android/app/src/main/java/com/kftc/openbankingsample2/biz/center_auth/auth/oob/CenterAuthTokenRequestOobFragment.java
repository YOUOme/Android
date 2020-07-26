package com.kftc.openbankingsample2.biz.center_auth.auth.oob;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthHomeFragment;
import com.kftc.openbankingsample2.biz.center_auth.auth.CenterAuthFragment;
import com.kftc.openbankingsample2.biz.center_auth.http.CenterAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.common.util.Utils;

import java.util.HashMap;

/**
 * 이용기관 토큰발급(2-legged) : oob
 */
public class CenterAuthTokenRequestOobFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String code;        // authorization code
    private String scope;
    private String client_info;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();

        code = args.getString("code", "");
        scope = Utils.urlDecode(args.getString("scope", ""));   // 다중스코프에서 space가 +기호가 되지 않도록 URLDecode 처리
        client_info = args.getString("client_info");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_token_request_oob, container, false);
        initView();
        return view;
    }

    void initView() {

        // client_id
        TextView tvClientId = view.findViewById(R.id.tvClientId);
        tvClientId.setText(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_ID));

        // client_secret
        TextView tvClientSecret = view.findViewById(R.id.tvClientSecret);
        tvClientSecret.setText(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_SECRET));

        // scope
        TextView tvScope = view.findViewById(R.id.tvScope);
        tvScope.setText("oob");

        // 토큰발급(2-legged) 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("client_id", CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_ID));
            paramMap.put("client_secret", CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_SECRET));
            paramMap.put("scope", tvScope.getText().toString());   // 고정값
            paramMap.put("grant_type", "client_credentials");   // 고정값

            showProgress();
            CenterAuthApiRetrofitAdapter.getInstance()
                    .token(paramMap)
                    .enqueue(super.handleResponse("access_token", "발급받은 액세스토큰",
                            responseJson -> startFragment(CenterAuthHomeFragment.class, null, R.string.fragment_id_center)));
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }
}

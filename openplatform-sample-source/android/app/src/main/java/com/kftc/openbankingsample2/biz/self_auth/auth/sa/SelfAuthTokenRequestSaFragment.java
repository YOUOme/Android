package com.kftc.openbankingsample2.biz.self_auth.auth.sa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthHomeFragment;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthConst;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthHomeFragment;
import com.kftc.openbankingsample2.biz.self_auth.auth.SelfAuthFragment;
import com.kftc.openbankingsample2.biz.self_auth.http.SelfAuthApiRetrofitAdapter;
import com.kftc.openbankingsample2.biz.self_auth.util.SelfAuthUtils;
import com.kftc.openbankingsample2.common.util.Utils;

import java.util.HashMap;

/**
 * 자체인증 토큰발급(2-legged) : sa
 */
public class SelfAuthTokenRequestSaFragment extends AbstractSelfAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_token_request_sa, container, false);
        initView();
        return view;
    }

    void initView() {

        // client_id
        TextView tvClientId = view.findViewById(R.id.tvClientId);
        tvClientId.setText(SelfAuthUtils.getSavedValueFromSetting(SelfAuthConst.SELF_AUTH_CLIENT_ID));

        // client_secret
        TextView tvClientSecret = view.findViewById(R.id.tvClientSecret);
        tvClientSecret.setText(SelfAuthUtils.getSavedValueFromSetting(SelfAuthConst.SELF_AUTH_CLIENT_SECRET));

        // scope
        TextView tvScope = view.findViewById(R.id.tvScope);
        tvScope.setText("sa");

        // 토큰발급(2-legged) 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("client_id", SelfAuthUtils.getSavedValueFromSetting(SelfAuthConst.SELF_AUTH_CLIENT_ID));
            paramMap.put("client_secret", SelfAuthUtils.getSavedValueFromSetting(SelfAuthConst.SELF_AUTH_CLIENT_SECRET));
            paramMap.put("scope", tvScope.getText().toString());   // 고정값
            paramMap.put("grant_type", "client_credentials");   // 고정값

            showProgress();
            SelfAuthApiRetrofitAdapter.getInstance()
                    .token(paramMap)
                    .enqueue(super.handleResponse("access_token", "발급받은 액세스토큰",
                            responseJson -> startFragment(SelfAuthHomeFragment.class, null, R.string.fragment_id_self)));
        });

        // 취소
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> onBackPressed());
    }
}

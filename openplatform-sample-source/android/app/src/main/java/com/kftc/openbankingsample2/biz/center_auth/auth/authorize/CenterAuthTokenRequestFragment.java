package com.kftc.openbankingsample2.biz.center_auth.auth.authorize;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * 앞단에서 받은 authorization code 를 사용하여 AccessToken 획득하는 과정. api 주소 : .../oauth/2.0/token
 */
public class CenterAuthTokenRequestFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private View view;

    // data
    private Bundle args;
    private String code;        // authorization code
    private String scope;
    private String client_info;
    private String state;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        args = getArguments();
        if (args == null) args = new Bundle();

        code = args.getString("code", "");
        scope = Utils.urlDecode(args.getString("scope", ""));   // 다중스코프에서 space가 +기호가 되지 않도록 URLDecode 처리
        client_info = args.getString("client_info");
        state = args.getString("state");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_center_auth_token_request, container, false);
        initView();
        return view;
    }

    void initView() {

        // 인증코드
        EditText etAuthorizationCode = view.findViewById(R.id.etAuthorizationCode);
        etAuthorizationCode.setText(code);

        // client_id
        TextView tvClientId = view.findViewById(R.id.tvClientId);
        tvClientId.setText(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_ID));

        // client_secret
        TextView tvClientSecret = view.findViewById(R.id.tvClientSecret);
        tvClientSecret.setText(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_CLIENT_SECRET));

        // redirect_uri
        TextView tvRedirectUri = view.findViewById(R.id.tvRedirectUri);
        tvRedirectUri.setText(CenterAuthUtils.getSavedValueFromSetting(CenterAuthConst.CENTER_AUTH_REDIRECT_URI));

        // scope
        EditText etScope = view.findViewById(R.id.etScope);
        etScope.setText(scope);

        // access token 발급 요청
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("code", etAuthorizationCode.getText().toString().trim());
            paramMap.put("client_id", tvClientId.getText().toString().trim());
            paramMap.put("client_secret", tvClientSecret.getText().toString().trim());
            paramMap.put("redirect_uri", tvRedirectUri.getText().toString().trim());
            paramMap.put("grant_type", "authorization_code");

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

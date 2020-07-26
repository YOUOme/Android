package com.kftc.openbankingsample2.biz.self_auth.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthHomeFragment;
import com.kftc.openbankingsample2.biz.self_auth.auth.user_register.SelfAuthAPIUserRegisterFragment;
import com.kftc.openbankingsample2.biz.self_auth.auth.sa.SelfAuthTokenRequestSaFragment;

/**
 * 자체인증 메뉴
 */
public class SelfAuthFragment extends AbstractSelfAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_self_auth, container, false);
        initView();
        return view;
    }

    void initView() {

        // 신규 계좌등록
        view.findViewById(R.id.btnUserRegister).setOnClickListener(v -> startFragment(SelfAuthAPIUserRegisterFragment.class, args, R.string.fragment_id_api_call_user_register));

        // 토큰발급(2-legged) 요청
        view.findViewById(R.id.btnAuthToken).setOnClickListener(v -> startFragment(SelfAuthTokenRequestSaFragment.class, args, R.string.fragment_id_token));

    }

    @Override
    public void onBackPressed() {
        startFragment(SelfAuthHomeFragment.class, args, R.string.fragment_id_self);
    }
}

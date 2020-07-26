package com.kftc.openbankingsample2.biz.center_auth.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthHomeFragment;
import com.kftc.openbankingsample2.biz.center_auth.auth.authorize.CenterAuthAuthorizeFragment;
import com.kftc.openbankingsample2.biz.center_auth.auth.authorize_account.CenterAuthAuthorizeAccountFragment;
import com.kftc.openbankingsample2.biz.center_auth.auth.oob.CenterAuthTokenRequestOobFragment;

/**
 * 센터인증 메뉴
 */
public class CenterAuthFragment extends AbstractCenterAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_center_auth, container, false);
        initView();
        return view;
    }

    void initView() {

        // 사용자 인증 개선버전
        view.findViewById(R.id.btnAuthNewWebAuth2).setOnClickListener(v -> startFragment(CenterAuthAuthorizeFragment.class, args, R.string.fragment_id_auth_authorize));

        // 계좌등록 확인 개선버전
        view.findViewById(R.id.btnAuthNewWebAuthAcnt2).setOnClickListener(v -> startFragment(CenterAuthAuthorizeAccountFragment.class, args, R.string.fragment_id_auth_authorize_account));

        // 토큰발급(2-legged) 요청
        view.findViewById(R.id.btnAuthToken).setOnClickListener(v -> startFragment(CenterAuthTokenRequestOobFragment.class, args, R.string.fragment_id_token));

    }

    @Override
    public void onBackPressed() {
        startFragment(CenterAuthHomeFragment.class, args, R.string.fragment_id_center);
    }
}

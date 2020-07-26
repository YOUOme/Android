package com.kftc.openbankingsample2.biz.self_auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.main.HomeFragment;
import com.kftc.openbankingsample2.biz.self_auth.api.SelfAuthAPIFragment;
import com.kftc.openbankingsample2.biz.self_auth.auth.SelfAuthFragment;

/**
 * 자체인증 메인화면
 */
public class SelfAuthHomeFragment extends AbstractSelfAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_self_auth_home, container, false);
        initView();
        return view;
    }

    private void initView() {

        // 계좌등록
        view.findViewById(R.id.btnAuthToken).setOnClickListener(v -> startFragment(SelfAuthFragment.class, args, R.string.fragment_id_self_auth));

        // API 거래
        view.findViewById(R.id.btnAPICallMenu).setOnClickListener(v -> startFragment(SelfAuthAPIFragment.class, args, R.string.fragment_id_self_api_call));

    }

    @Override
    public void onBackPressed() {
        startFragment(HomeFragment.class, args, R.string.fragment_id_home);
    }
}

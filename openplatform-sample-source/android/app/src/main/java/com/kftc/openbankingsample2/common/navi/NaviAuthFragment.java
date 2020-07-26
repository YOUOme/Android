package com.kftc.openbankingsample2.common.navi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.auth.authorize.CenterAuthAuthorizeFragment;
import com.kftc.openbankingsample2.biz.center_auth.auth.authorize_account.CenterAuthAuthorizeAccountFragment;

public class NaviAuthFragment extends AbstractNaviFragment {

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
        view = inflater.inflate(R.layout.fragment_navi_child_auth, container, false);
        initView();
        return view;
    }

    void initView() {

        view.findViewById(R.id.llAuthorize).setOnClickListener(v -> {
            activity.closeNavi();
            activity.startFragment(CenterAuthAuthorizeFragment.class, args, R.string.fragment_id_center);
        });

        view.findViewById(R.id.llAuthorizeAccount).setOnClickListener(v -> {
            activity.closeNavi();
            activity.startFragment(CenterAuthAuthorizeAccountFragment.class, args, R.string.fragment_id_auth_authorize);
        });
    }
}

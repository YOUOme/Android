package com.kftc.openbankingsample2.biz.main;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthHomeFragment;
import com.kftc.openbankingsample2.biz.self_auth.SelfAuthHomeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;
import com.kftc.openbankingsample2.BuildConfig;

/**
 * 센터인증과 자체인증을 선택하는 앱의 메인화면
 */
public class HomeFragment extends AbstractCenterAuthMainFragment {

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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;
    }

    private void initView() {

        // 센터인증
        view.findViewById(R.id.btnCenterAuth).setOnClickListener(v -> startFragment(CenterAuthHomeFragment.class, args, R.string.fragment_id_center));

        // 자체인증
        view.findViewById(R.id.btnSelfAuth).setOnClickListener(v -> startFragment(SelfAuthHomeFragment.class, args, R.string.fragment_id_self));

        // 하단 버전표시
        try {
            Date date = new Date(BuildConfig.TIMESTAMP);
            String dateStr = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(date);

            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            ((TextView) view.findViewById(R.id.tvVersion)).setText(String.format("Ver: %s_%s", String.valueOf(pi.versionName), dateStr));
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        showSetting(false);
    }
}

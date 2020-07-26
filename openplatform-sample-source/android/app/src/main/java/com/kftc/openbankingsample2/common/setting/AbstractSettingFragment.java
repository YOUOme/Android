package com.kftc.openbankingsample2.common.setting;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthHomeFragment;
import com.kftc.openbankingsample2.biz.main.MainActivity;
import com.kftc.openbankingsample2.common.http.KmProgressBar;
import com.kftc.openbankingsample2.common.util.view.onKeyBackPressedListener;

abstract public class AbstractSettingFragment extends PreferenceFragmentCompat implements onKeyBackPressedListener {

    // context
    private Context context;
    protected MainActivity activity;

    // progress
    private KmProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        // 메인액티빅티 개체 연결
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 메인액티빅티 개체 연결
        activity = (MainActivity) getActivity();

        // 네이게이션 메뉴버튼 보여주기
        // TODO: 2019-08-06 네비게이션 메뉴는 보여주지 말자. 메뉴가 단순함.
        //lockNavi(false);
        lockNavi(true);

        // 툴바 보여주기
        setToolbarVisible(true);

        // 툴바 타이틀 설정
        setTitle(getTag());

        // 세팅 아이콘 보여주기
        showSetting(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // 메인화면에서는 뒤로가기 버튼 제거
        if (getFragmentManager() != null) {
            Fragment f = getFragmentManager().findFragmentById(R.id.flContent);
            if (f instanceof CenterAuthHomeFragment) {
                setArrowVisible(false);
            } else {
                setArrowVisible(true);
            }
        }
    }

    // 네비게이션 메뉴버튼 보여주기/감추기
    public void lockNavi(boolean enableLock) {
        activity.lockNavi(enableLock);
    }

    // 툴바 보여주기/감추기
    public void setToolbarVisible(boolean isVisible) {
        activity.setToolbarVisible(isVisible);
    }

    // 타이틀 설정
    public void setTitle(String title) {
        activity.setTitle(title);
    }

    // 세팅 아이콘 보여주기/감추기
    public void showSetting(boolean enable) {
        activity.showSetting(enable);
    }

    // 백버튼 보여주기/감추기
    public void setArrowVisible(boolean bool) {
        activity.setArrowVisible(bool);
    }

    // fragment 시작
    public void startFragment(@NonNull Class fragmentClass, Bundle args, @StringRes int tagResId) {
        activity.startFragment(fragmentClass, args, tagResId);
    }

    // fragment 시작
    public void startFragment(@NonNull Class fragmentClass, Bundle args, String TAG_FRAGMENT, boolean replace, boolean keep) {
        activity.startFragment(fragmentClass, args, TAG_FRAGMENT, replace, keep);
    }

    @Override
    public void onBackPressed() {
        activity.onDefaultBackPressed();
    }
}

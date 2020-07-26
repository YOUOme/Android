package com.kftc.openbankingsample2.biz.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.setting.CenterAuthSettingFragment;
import com.kftc.openbankingsample2.biz.self_auth.AbstractSelfAuthMainFragment;
import com.kftc.openbankingsample2.biz.self_auth.setting.SelfAuthSettingFragment;
import com.kftc.openbankingsample2.common.navi.NaviMainFragment;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.CustomContextWrapper;
import com.kftc.openbankingsample2.common.util.view.KmDialogDefault;
import com.kftc.openbankingsample2.common.util.view.onKeyBackPressedListener;

import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * 메인 Activity 의 추상클래스.
 */
public abstract class AbstractMainActivity extends AppCompatActivity {

    // context
    private Context context;

    // view
    public DrawerLayout drawer;         // 네비게이션뷰 드로우어
    protected Toolbar toolbar;          // 툴바 레이아웃
    protected Button btnArrow;          // 툴바 뒤로가기 버튼
    protected TextView tvTitle;         // 툴바 타이블
    protected Button btnNavi;           // 툴바 네비게이션 햄버거 버튼
    protected Button btnSetting;        // 툴바 설정

    // data
    // ...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
    }

    private void initView() {
        drawer = findViewById(R.id.drawer);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 뒤로가기 버튼
        btnArrow = findViewById(R.id.btnArrow);
        btnArrow.setOnClickListener(v -> onBackPressed());

        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setOnLongClickListener(v -> {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContent);
            if (f != null) {
                new AlertDialog.Builder(context).setTitle("(TEST)클래스명").setMessage(f.getClass().getName()).show();
            }
            return true;
        });
        btnNavi = findViewById(R.id.btnNavi);

        // 설정 버튼
        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(v -> {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContent);
            if (f == null) {
                return;
            }

            // 센터인증 화면이면 센터인증 설정으로, 자체인증 화면이면 자체인증 설정으로 이동
            if (f instanceof AbstractCenterAuthMainFragment) {
                startFragment(CenterAuthSettingFragment.class, null, R.string.fragment_id_center_auth_setting);
            } else if (f instanceof AbstractSelfAuthMainFragment) {
                startFragment(SelfAuthSettingFragment.class, null, R.string.fragment_id_self_auth_setting);
            }

        });

        // 네비게이션 초기화
        initNavi();

    }

    public void initNavi() {
        getSupportFragmentManager().beginTransaction().replace(R.id.navi, new NaviMainFragment(), getString(R.string.fragment_id_navi)).commit();
        btnNavi.setOnClickListener(v -> {
            closeSoftKeypad();
            if (drawer.isDrawerOpen(GravityCompat.END))
                drawer.closeDrawer(GravityCompat.END);
            else
                drawer.openDrawer(GravityCompat.END);
        });
    }

    public void closeSoftKeypad() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // 네이게이션 메뉴 보여주기/감추기
    public void lockNavi(boolean enableLock) {
        if (enableLock) {
            btnNavi.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            btnNavi.setVisibility(View.VISIBLE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    // 네이게이션 메뉴 닫기
    public void closeNavi() {
        drawer.closeDrawer(GravityCompat.END);
    }

    // 툴바 보여주기/감추기
    public void setToolbarVisible(boolean bool) {
        if (toolbar == null) return;
        if (bool) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    // 백버튼 보여주기/감추기
    public void setArrowVisible(boolean bool) {
        if (bool) {
            btnArrow.setVisibility(View.VISIBLE);
        } else {
            btnArrow.setVisibility(View.GONE);
        }
    }

    // fragment 시작
    public void startFragment(@NonNull Class fragmentClass, Bundle args, @StringRes int tagResId) {
        String tag = getResources().getString(tagResId);
        startFragment(fragmentClass, args, tag, true, true);
    }

    // fragment 시작
    public void startFragment(@NonNull Class fragmentClass, Bundle args, String TAG_FRAGMENT, boolean replace, boolean keep) {
        if (isFinishing() || isDestroyed()) {
            Timber.e("Activity가 종료중이거나 종료되어서 이동할수 없습니다.");
            return;
        }

        Fragment fragment;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Timber.e(e);
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager == null) {
            Timber.e("이동할 수 없음");
            return;
        }

        fragment.setArguments(args);

        // 현재 떠있는 Fragment와 이동할 프래그먼트가 같으면 add 하지 않음
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.flContent);
        if (currentFragment != null) {
            if (currentFragment.getClass() == fragment.getClass()) {
                Timber.d("fragment가 동일하여 이동하지 않습니다. : " + currentFragment.getClass().toString());
                return;
            }
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (replace) {
            transaction.replace(R.id.flContent, fragment, TAG_FRAGMENT);
        } else {
            transaction.add(R.id.flContent, fragment, TAG_FRAGMENT);
        }
        if (keep) {
            transaction.addToBackStack(TAG_FRAGMENT); // 해당 프래그먼트는 중단되고 사용자가 뒤로 탐색하면 재개
        }
        transaction.commitAllowingStateLoss();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void showSetting(boolean enable) {
        btnSetting.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        // 사용언어 처리(설정에서 개발자언어 사용여부에 따라 표시)
        Locale newLocale = Utils.getSavedBoolValue(Const.IS_DEV_LANG) ? Locale.ENGLISH : Locale.KOREA;
        Context context = CustomContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

    // 백버튼 처리
    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof onKeyBackPressedListener) {
                closeSoftKeypad();
                ((onKeyBackPressedListener) fragment).onBackPressed();
            }
        }
    }

    public void onDefaultBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (f == null) {
            startFragment(HomeFragment.class, null, R.string.app_name);
            return;
        }
        if (f.getClass() == HomeFragment.class) {
            KmDialogDefault dialog = new KmDialogDefault(context);
            dialog.setTitle("종료");
            dialog.setMessage("앱을 종료하시겠습니까?");
            dialog.setPositiveButton("확인", (dialog1, which) -> exitApp());
            dialog.setNegativeButton("취소", null);
            dialog.setCancelable(false);
            dialog.show();
        } else {
            super.onBackPressed();
        }
    }

    // 앱종료
    public void exitApp() {
        finishAffinity();
    }
}

package com.kftc.openbankingsample2.biz.center_auth.setting;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.google.android.material.snackbar.Snackbar;
import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.CenterAuthConst;
import com.kftc.openbankingsample2.biz.main.Const;
import com.kftc.openbankingsample2.biz.main.HomeFragment;
import com.kftc.openbankingsample2.biz.main.MainActivity;
import com.kftc.openbankingsample2.common.application.AppData;
import com.kftc.openbankingsample2.common.setting.AbstractSettingFragment;
import com.kftc.openbankingsample2.common.util.Utils;
import com.kftc.openbankingsample2.common.util.view.KmDialogDefault;

import timber.log.Timber;

/**
 * 센터인증 설정
 */
public class CenterAuthSettingFragment extends AbstractSettingFragment {

    // context
    private Context context;

    // view
    private SwitchPreference CENTER_AUTH_IS_REAL;
    private SwitchPreference IS_DEV_LANG;
    private Preference INIT_BUTTON;

    private EditTextPreference CENTER_AUTH_BASE_URI_TEST;

    private EditTextPreference CENTER_AUTH_CLIENT_ID_TEST;
    private EditTextPreference CENTER_AUTH_CLIENT_SECRET_TEST;
    private EditTextPreference CENTER_AUTH_CLIENT_USE_CODE_TEST;
    private EditTextPreference CENTER_AUTH_REDIRECT_URI_TEST;
    private EditTextPreference CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_TEST;
    private EditTextPreference CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_TEST;

    private EditTextPreference CENTER_AUTH_BASE_URI_REAL;

    private EditTextPreference CENTER_AUTH_CLIENT_ID_REAL;
    private EditTextPreference CENTER_AUTH_CLIENT_SECRET_REAL;
    private EditTextPreference CENTER_AUTH_CLIENT_USE_CODE_REAL;
    private EditTextPreference CENTER_AUTH_REDIRECT_URI_REAL;
    private EditTextPreference CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_REAL;
    private EditTextPreference CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_REAL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.fragment_center_auth_setting);
        initView();
    }

    void initView() {

        // 리얼서버 접속여부
        CENTER_AUTH_IS_REAL = (SwitchPreference) findPreference(CenterAuthConst.CENTER_AUTH_IS_REAL);
        CENTER_AUTH_IS_REAL.setSummary(CENTER_AUTH_IS_REAL.isChecked() ? CENTER_AUTH_IS_REAL.getSwitchTextOn() : CENTER_AUTH_IS_REAL.getSwitchTextOff());
        CENTER_AUTH_IS_REAL.setOnPreferenceChangeListener(onChangeListener);

        // 개발자언어(필드명) 사용여부
        IS_DEV_LANG = (SwitchPreference) findPreference(Const.IS_DEV_LANG);
        IS_DEV_LANG.setSummary(IS_DEV_LANG.isChecked() ? IS_DEV_LANG.getSwitchTextOn() : IS_DEV_LANG.getSwitchTextOff());
        IS_DEV_LANG.setOnPreferenceChangeListener(onChangeListener);

        // 초기화버튼
        INIT_BUTTON = findPreference("INIT_BUTTON");
        INIT_BUTTON.setOnPreferenceClickListener(preference -> {
            KmDialogDefault dialog = new KmDialogDefault(context);
            dialog.setTitle("초기화");
            dialog.setMessage("앱에 저장된 데이터가 모두 지워지고 초기설정으로 돌아갑니다. 앱 설정을 초기화 하시겠습니까? ");
            dialog.setCancelable(false);
            dialog.setPositiveButton("확인", (dialog1, which) -> {
                Utils.removePref(context);
                PreferenceManager.setDefaultValues(context, R.xml.fragment_center_auth_setting, true);
                PreferenceManager.setDefaultValues(context, R.xml.fragment_self_auth_setting, true);
                AppData.centerAuthAccessTokenList.clear();
                AppData.centerAuthBankAccountList.clear();
                AppData.selfAuthAccessTokenList.clear();
                AppData.selfAuthBankAccountList.clear();
                Toast.makeText(context, "앱 설정이 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
            });
            dialog.setNegativeButton("취소", null);
            dialog.show();

            return true;
        });

        // 테스트서버 접속정보
        CENTER_AUTH_BASE_URI_TEST = (EditTextPreference) findPreference(CenterAuthConst.CENTER_AUTH_BASE_URI + "_TEST");
        CENTER_AUTH_BASE_URI_TEST.setSummary(CENTER_AUTH_BASE_URI_TEST.getText());
        CENTER_AUTH_BASE_URI_TEST.setOnPreferenceChangeListener(onChangeListener);

        // 테스트서버 센터인증 정보
        CENTER_AUTH_CLIENT_ID_TEST = (EditTextPreference) findPreference( CenterAuthConst.CENTER_AUTH_CLIENT_ID + "_TEST");
        CENTER_AUTH_CLIENT_ID_TEST.setSummary(CENTER_AUTH_CLIENT_ID_TEST.getText());
        CENTER_AUTH_CLIENT_ID_TEST.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CLIENT_SECRET_TEST = (EditTextPreference) findPreference( CenterAuthConst.CENTER_AUTH_CLIENT_SECRET + "_TEST");
        CENTER_AUTH_CLIENT_SECRET_TEST.setSummary(CENTER_AUTH_CLIENT_SECRET_TEST.getText());
        CENTER_AUTH_CLIENT_SECRET_TEST.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CLIENT_USE_CODE_TEST = (EditTextPreference) findPreference( CenterAuthConst.CENTER_AUTH_CLIENT_USE_CODE + "_TEST");
        CENTER_AUTH_CLIENT_USE_CODE_TEST.setSummary(CENTER_AUTH_CLIENT_USE_CODE_TEST.getText());
        CENTER_AUTH_CLIENT_USE_CODE_TEST.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_REDIRECT_URI_TEST = (EditTextPreference) findPreference( CenterAuthConst.CENTER_AUTH_REDIRECT_URI + "_TEST");
        CENTER_AUTH_REDIRECT_URI_TEST.setSummary(CENTER_AUTH_REDIRECT_URI_TEST.getText());
        CENTER_AUTH_REDIRECT_URI_TEST.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_TEST = (EditTextPreference) findPreference( CenterAuthConst.CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM + "_TEST");
        CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_TEST.setSummary(CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_TEST.getText());
        CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_TEST.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_TEST = (EditTextPreference) findPreference( CenterAuthConst.CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM + "_TEST");
        CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_TEST.setSummary(CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_TEST.getText());
        CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_TEST.setOnPreferenceChangeListener(onChangeListener);

        // 리얼서버 접속정보
        CENTER_AUTH_BASE_URI_REAL = (EditTextPreference) findPreference(CenterAuthConst.CENTER_AUTH_BASE_URI + "_REAL");
        CENTER_AUTH_BASE_URI_REAL.setSummary(CENTER_AUTH_BASE_URI_REAL.getText());
        CENTER_AUTH_BASE_URI_REAL.setOnPreferenceChangeListener(onChangeListener);

        // 리얼서버 센터인증 정보
        CENTER_AUTH_CLIENT_ID_REAL = (EditTextPreference) findPreference(CenterAuthConst.CENTER_AUTH_CLIENT_ID + "_REAL");
        CENTER_AUTH_CLIENT_ID_REAL.setSummary(CENTER_AUTH_CLIENT_ID_REAL.getText());
        CENTER_AUTH_CLIENT_ID_REAL.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CLIENT_SECRET_REAL = (EditTextPreference) findPreference(CenterAuthConst.CENTER_AUTH_CLIENT_SECRET + "_REAL");
        CENTER_AUTH_CLIENT_SECRET_REAL.setSummary(CENTER_AUTH_CLIENT_SECRET_REAL.getText());
        CENTER_AUTH_CLIENT_SECRET_REAL.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CLIENT_USE_CODE_REAL = (EditTextPreference) findPreference( CenterAuthConst.CENTER_AUTH_CLIENT_USE_CODE + "_REAL");
        CENTER_AUTH_CLIENT_USE_CODE_REAL.setSummary(CENTER_AUTH_CLIENT_USE_CODE_REAL.getText());
        CENTER_AUTH_CLIENT_USE_CODE_REAL.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_REDIRECT_URI_REAL = (EditTextPreference) findPreference(CenterAuthConst.CENTER_AUTH_REDIRECT_URI + "_REAL");
        CENTER_AUTH_REDIRECT_URI_REAL.setSummary(CENTER_AUTH_REDIRECT_URI_REAL.getText());
        CENTER_AUTH_REDIRECT_URI_REAL.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_REAL = (EditTextPreference) findPreference(CenterAuthConst.CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM + "_REAL");
        CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_REAL.setSummary(CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_REAL.getText());
        CENTER_AUTH_CONTRACT_WITHDRAW_ACCOUNT_NUM_REAL.setOnPreferenceChangeListener(onChangeListener);
        CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_REAL = (EditTextPreference) findPreference(CenterAuthConst.CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM + "_REAL");
        CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_REAL.setSummary(CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_REAL.getText());
        CENTER_AUTH_CONTRACT_DEPOSIT_ACCOUNT_NUM_REAL.setOnPreferenceChangeListener(onChangeListener);
    }

    // 설정변경을 시도할때 변경이 완료되기 전 실행된다.
    private Preference.OnPreferenceChangeListener onChangeListener = (preference, newValue) -> {
        Timber.d("#### (PREFERENCE) onChanged : " + preference.getKey() + ", value : " + newValue);

        if (preference instanceof SwitchPreference) {
            boolean real = (boolean) newValue;
            SwitchPreference switchPreference = (SwitchPreference) preference;
            switchPreference.setSummary(real ? switchPreference.getSwitchTextOn() : switchPreference.getSwitchTextOff());

            // 필드명을 한글 또는 영문으로 변환시에는 앱을 재시작할지 물어본다.
            if (preference.getKey().equals(Const.IS_DEV_LANG)) {
                if (getActivity() != null) {
                    MainActivity activity = (MainActivity) getActivity();
                    Snackbar.make(getActivity().findViewById(R.id.flContent), "설정을 적용하려면 앱을 재시작해야 합니다.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("확인", v -> {
                                KmDialogDefault dialog = new KmDialogDefault(context);
                                dialog.setTitle("종료");
                                dialog.setMessage("앱을 재시작 하시겠습니까?");
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("확인", (dialog1, which) -> Utils.restartApp(context));
                                dialog.setNegativeButton("취소", null);
                                dialog.show();
                            }).show();
                } else {
                    Toast.makeText(context, "설정을 적용하려면 앱을 재시작해야 합니다.", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            preference.setSummary(String.valueOf(newValue));
        }

        return true;
    };

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        startFragment(HomeFragment.class, null, R.string.fragment_id_home);
    }
}

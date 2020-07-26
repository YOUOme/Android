package com.kftc.openbankingsample2.biz.center_auth.auth.authorize;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.kftc.openbankingsample2.R;
import com.kftc.openbankingsample2.biz.center_auth.AbstractCenterAuthMainFragment;
import com.kftc.openbankingsample2.biz.center_auth.util.CenterAuthUtils;
import com.kftc.openbankingsample2.common.application.AppData;

import timber.log.Timber;
import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * 사용지인증 or 계좌등록확인 개선버전 CASE 화면 공통로직
 */
public class AbstractCenterAuthAuthorizeCaseFragment extends AbstractCenterAuthMainFragment {

    // context
    private Context context;

    // view
    private EditText et_ANW_SCOPE;
    private Button btnScope;
    /*private EditText et_ANW_BG_COLOR;
    private Button btnBackgroundColor;
    private EditText et_ANW_TXT_COLOR;
    private Button btnTextColor;
    private EditText et_ANW_BTN1_COLOR;
    private Button btnBtn1Color;
    private EditText et_ANW_BTN2_COLOR;
    private Button btnBtn2Color;*/

    // data
    private int selectedBackgroundColor;
    private int selectedTextColor;
    private int selectedBtn1Color;
    private int selectedBtn2Color;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    // scope 선택버튼 등 초기화
    void initButton(View view) {

        // scope
        et_ANW_SCOPE = view.findViewById(R.id.et_ANW_SCOPE);
        btnScope = view.findViewById(R.id.btnScope);
        btnScope.setOnClickListener(v -> showScopeDialog(et_ANW_SCOPE, AppData.scopeList));
    }

    // 색상선택버튼 초기화
    void initColor(View view) {

        ////////// 배경색, 글자색 //////////

        // 배경색 초기화
        /*et_ANW_BG_COLOR = view.findViewById(R.id.et_ANW_BG_COLOR);
        String str_ANW_BG_COLOR = et_ANW_BG_COLOR.getText().toString();
        btnBackgroundColor = view.findViewById(R.id.btnBackgroundColor);

        // 글자색 초기화
        et_ANW_TXT_COLOR = view.findViewById(R.id.et_ANW_TXT_COLOR);
        String str_ANW_TXT_COLOR = et_ANW_TXT_COLOR.getText().toString();
        btnTextColor = view.findViewById(R.id.btnTextColor);

        // 초기 배경색
        if (!TextUtils.isEmpty(str_ANW_BG_COLOR)) {
            try {
                selectedBackgroundColor = Color.parseColor(str_ANW_BG_COLOR);
            } catch (IllegalArgumentException e) {
                Timber.e(e);
                selectedBackgroundColor = 0;
            }
            btnBackgroundColor.setBackgroundColor(selectedBackgroundColor);
            btnTextColor.setBackgroundColor(selectedBackgroundColor);
        }

        // 클릭시 변경
        btnBackgroundColor.setOnClickListener(v -> {
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(context, selectedBackgroundColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) { }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    String strColor = String.format("#%06X", 0xFFFFFF&color);
                    et_ANW_BG_COLOR.setText(strColor);
                    selectedBackgroundColor = color;
                    btnBackgroundColor.setBackgroundColor(selectedBackgroundColor);
                    btnTextColor.setBackgroundColor(selectedBackgroundColor);
                }
            });
            colorPicker.show();
        });

        // 롱클릭시 기본값으로 원복
        btnBackgroundColor.setOnLongClickListener(v -> {
            String defaultColor = CenterAuthUtils.getConstantsValEnv("ANW_BG_COLOR");
            et_ANW_BG_COLOR.setText(defaultColor);
            selectedBackgroundColor = Color.parseColor(defaultColor);
            btnBackgroundColor.setBackgroundColor(selectedBackgroundColor);
            btnTextColor.setBackgroundColor(selectedBackgroundColor);
            return true;
        });

        // 글자색
        if (!TextUtils.isEmpty(str_ANW_TXT_COLOR)) {
            try {
                selectedTextColor = Color.parseColor(str_ANW_TXT_COLOR);
            } catch (IllegalArgumentException e) {
                Timber.e(e);
                selectedTextColor = 0;
            }
            btnBackgroundColor.setTextColor(selectedTextColor);
            btnTextColor.setTextColor(selectedTextColor);
        }
        btnTextColor.setOnClickListener(v -> {
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(context, selectedTextColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) { }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    String strColor = String.format("#%06X", 0xFFFFFF&color);
                    et_ANW_TXT_COLOR.setText(strColor);
                    selectedTextColor = Color.parseColor(strColor);
                    btnBackgroundColor.setTextColor(selectedTextColor);
                    btnTextColor.setTextColor(selectedTextColor);
                }
            });
            colorPicker.show();
        });
        btnTextColor.setOnLongClickListener(v -> {
            String defaultColor = CenterAuthUtils.getConstantsValEnv("ANW_TXT_COLOR");
            et_ANW_TXT_COLOR.setText(defaultColor);
            selectedTextColor = Color.parseColor(defaultColor);
            btnBackgroundColor.setTextColor(selectedTextColor);
            btnTextColor.setTextColor(selectedTextColor);
            return true;
        });


        ////////// 확인버튼 //////////
        et_ANW_BTN1_COLOR = view.findViewById(R.id.et_ANW_BTN1_COLOR);
        String str_ANW_BTN1_COLOR = et_ANW_BTN1_COLOR.getText().toString();
        btnBtn1Color = view.findViewById(R.id.btnBtn1Color);
        if (!TextUtils.isEmpty(str_ANW_BTN1_COLOR)) {
            try {
                selectedBtn1Color = Color.parseColor(str_ANW_BTN1_COLOR);
            } catch (IllegalArgumentException e) {
                Timber.e(e);
                selectedBtn1Color = 0;
            }
            btnBtn1Color.setBackgroundColor(selectedBtn1Color);
        }
        btnBtn1Color.setOnClickListener(v -> {
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(context, selectedBtn1Color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) { }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    String strColor = String.format("#%06X", 0xFFFFFF&color);
                    et_ANW_BTN1_COLOR.setText(strColor);
                    selectedBtn1Color = color;
                    btnBtn1Color.setBackgroundColor(selectedBtn1Color);
                }
            });
            colorPicker.show();
        });
        btnBtn1Color.setOnLongClickListener(v -> {
            String defaultColor = CenterAuthUtils.getConstantsValEnv("ANW_BTN1_COLOR");
            et_ANW_BTN1_COLOR.setText(defaultColor);
            selectedBtn1Color = Color.parseColor(defaultColor);
            btnBtn1Color.setBackgroundColor(selectedBtn1Color);
            return true;
        });

        ////////// 취소버튼 //////////
        et_ANW_BTN2_COLOR = view.findViewById(R.id.et_ANW_BTN2_COLOR);
        String str_ANW_BTN2_COLOR = et_ANW_BTN2_COLOR.getText().toString();
        btnBtn2Color = view.findViewById(R.id.btnBtn2Color);
        if (!TextUtils.isEmpty(str_ANW_BTN2_COLOR)) {
            try {
                selectedBtn2Color = Color.parseColor(str_ANW_BTN2_COLOR);
            } catch (IllegalArgumentException e) {
                Timber.e(e);
                selectedBtn2Color = 0;
            }
            btnBtn2Color.setBackgroundColor(selectedBtn2Color);
        }
        btnBtn2Color.setOnClickListener(v -> {
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(context, selectedBtn2Color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) { }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    String strColor = String.format("#%06X", 0xFFFFFF&color);
                    et_ANW_BTN2_COLOR.setText(strColor);
                    selectedBtn2Color = color;
                    btnBtn2Color.setBackgroundColor(selectedBtn2Color);
                }
            });
            colorPicker.show();
        });
        btnBtn2Color.setOnLongClickListener(v -> {
            String defaultColor = CenterAuthUtils.getConstantsValEnv("ANW_BTN2_COLOR");
            et_ANW_BTN2_COLOR.setText(defaultColor);
            selectedBtn2Color = Color.parseColor(defaultColor);
            btnBtn2Color.setBackgroundColor(selectedBtn2Color);
            return true;
        });*/

    }
}

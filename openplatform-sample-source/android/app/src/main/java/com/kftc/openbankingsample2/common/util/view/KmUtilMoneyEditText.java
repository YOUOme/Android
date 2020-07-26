package com.kftc.openbankingsample2.common.util.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import java.text.DecimalFormat;

import timber.log.Timber;

/**
 * EditText를 천단위 마다 쉼표로 나타냄(원단위)
 */
public class KmUtilMoneyEditText extends EditText implements TextWatcher/*, View.OnTouchListener, View.OnFocusChangeListener */{

    // 세자리로 끊어서 쉼표 보여준다
    DecimalFormat df = new DecimalFormat("###,###");

    String unit = "원";

    // 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
    String result="";

    public KmUtilMoneyEditText(Context context) {
        super(context);
        init();
    }

    public KmUtilMoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KmUtilMoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 커스텀 함수
    public String getTextString() {
        return getText().toString().replaceAll(",", "").replaceAll(unit, "");
    }

    // View 초기화
    public void init() {
        super.addTextChangedListener(this);
    }

    ////////// TextWatcher 구현 부분 //////////
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void afterTextChanged(Editable s) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!s.toString().equals(result) && result != null) {
            try {
                Long number = Long.parseLong(s.toString().replaceAll(",", "").replaceAll(unit, ""));
                String decimal = df.format(number);
                Timber.d("decimal : " + decimal);
                if (decimal.length() == 0) {
                    result = decimal;
                    setText(result);                    // 결과 텍스트 셋팅.
                    setSelection(result.length());      // 커서를 제일 끝으로 보냄.
                } else {
                    result = decimal + unit;
                    setText(result);                    // 결과 텍스트 셋팅.
                    setSelection(result.length() - 1);      // 커서를 제일 끝으로 보냄.
                }
            } catch (NumberFormatException e) {

                // 입력된 값이 없을경우(empty) 여기로 진입
                Timber.d("숫자가 아닌 문자가 입력됨");
                result = "";
                setText(result);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}

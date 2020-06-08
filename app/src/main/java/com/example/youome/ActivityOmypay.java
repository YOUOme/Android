package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

public class ActivityOmypay extends AppCompatActivity {
    LinearLayout react_button;
    EditText et_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omypay);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        react_button = (LinearLayout)findViewById(R.id.react_button);
        et_money = (EditText)findViewById(R.id.et_money);
        et_money.addTextChangedListener(new PatternedTextWatcher(""));

    }
}
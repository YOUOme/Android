package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;

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
        et_money.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    react_button.setGravity(Gravity.TOP);
                else
                    react_button.setGravity(Gravity.BOTTOM);
            }
        });


    }
}
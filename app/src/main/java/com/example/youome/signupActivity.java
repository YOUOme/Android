package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class signupActivity extends AppCompatActivity {

    TextInputEditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_phone = (TextInputEditText)findViewById(R.id.et_phone);
        et_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Intent intent = new Intent(getApplicationContext(),YouomeActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}

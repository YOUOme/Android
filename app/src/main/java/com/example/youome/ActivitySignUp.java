package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ActivitySignUp extends AppCompatActivity {

    TextInputEditText et_phone,et_name,et_id;
    boolean onetime = true;

    FragmentSignUp1Auth auth;
    FragmentSignUp2Tele tele;

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

        et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && onetime && !tele.isAdded()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(tele, "tele").commit();
                    et_phone.setOnFocusChangeListener(null);
                    onetime = false;
                }
            }
        });

        et_name = (TextInputEditText)findViewById(R.id.et_name);
        et_id = (TextInputEditText)findViewById(R.id.et_inum);

        auth = new FragmentSignUp1Auth();
        tele = new FragmentSignUp2Tele();
        auth.setCancelable(false);
    }

    @Override
    protected void onStart() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(auth,"auth").commit();
        super.onStart();
    }
}

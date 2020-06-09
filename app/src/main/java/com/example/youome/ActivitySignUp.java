package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

public class ActivitySignUp extends AppCompatActivity {

    TextInputEditText et_phone,et_name,et_id;
    TextInputLayout textInputLayout;
    boolean onetime = true;

    FragmentSignUp1Auth auth;
    FragmentSignUp2Tele tele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_phone = (TextInputEditText)findViewById(R.id.et_phone);
        et_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Intent intent = new Intent(getApplicationContext(), ActivityYOUOme.class);
                startActivity(intent);
                return false;
            }
        });

        et_phone.addTextChangedListener(new PatternedTextWatcher("###-####-####"));
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

        textInputLayout = (TextInputLayout)findViewById(R.id.textinputlayout);
        et_name = (TextInputEditText)findViewById(R.id.et_name);
        et_id = (TextInputEditText)findViewById(R.id.et_inum);
        et_id.addTextChangedListener(new PatternedTextWatcher("######-#******"));

        auth = new FragmentSignUp1Auth();
        tele = new FragmentSignUp2Tele();
        tele.setCancelable(false);
        auth.setCancelable(false);
    }

    @Override
    protected void onStart() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(auth,"auth").commit();
        super.onStart();
    }
}

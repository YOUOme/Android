package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ActivitySignUp extends AppCompatActivity {

    TextInputEditText et_phone,et_name,et_id;
    boolean onetime = true;

    FragmentSignUp1Auth auth;
    FragmentSignUp2Tele tele;

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_phone = (TextInputEditText)findViewById(R.id.et_phone);
        et_phone.setOnTouchListener(new SignUpClickListener());
        et_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Intent intent = new Intent(getApplicationContext(),YouomeActivity.class);
                startActivity(intent);
                return false;
            }
        });
        et_name = (TextInputEditText)findViewById(R.id.et_name);
        et_name.setOnTouchListener(new SignUpClickListener());
        et_id = (TextInputEditText)findViewById(R.id.et_inum);
        et_id.setOnTouchListener(new SignUpClickListener());

        //auth = new FragmentSignUp1Auth();
        tele = new FragmentSignUp2Tele();
        //tele.setCancelable(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //ft = getSupportFragmentManager().beginTransaction();
    }

    private class SignUpClickListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(onetime) {
                //auth.show(getSupportFragmentManager(), auth.getTag());
                //ft.commit();
                //ft.add(auth,"auth");
                //ft.commit();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(auth,"auth");
                fragmentTransaction.commit();
                onetime = false;
            }
            if(!onetime && view.getId() == R.id.et_phone){
                //ft.commit();
                //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.add(tele,"tele").commit();
            }
            return false;
        }
    }
}

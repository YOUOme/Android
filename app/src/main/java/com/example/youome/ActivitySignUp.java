package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class ActivitySignUp extends AppCompatActivity {

    TextInputEditText et_phone;
    LinearLayout linearLayout_Auth;
    boolean onetime = true;
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

        linearLayout_Auth = (LinearLayout)findViewById(R.id.auth);
        linearLayout_Auth.setOnTouchListener(new View.OnTouchListener() { //  한 리스너에 때려박자.
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(onetime) {
                    onetime = false;
                    FragmentSignUpAuth bot = new FragmentSignUpAuth();
                    bot.show(getSupportFragmentManager(), bot.getTag());
                }
                return false;
            }
        });
    }
}

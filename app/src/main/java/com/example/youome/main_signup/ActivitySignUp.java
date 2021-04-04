package com.example.youome.main_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.youome.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.annotations.SerializedName;
import com.google.android.material.textfield.TextInputLayout;

public class ActivitySignUp extends AppCompatActivity {

    TextInputEditText et_phone,et_name,et_id;
    public TextInputLayout textInputLayout;
    TextView password;
    boolean onetime = true;

    //FragmentSignUp1Auth auth;  // 본인인증 약관동의 -> 폐기예정
    //FragmentSignUp2Tele tele;  // 통신사 설정 -> 폐기예정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_name = (TextInputEditText)findViewById(R.id.et_name);
        et_id = (TextInputEditText)findViewById(R.id.et_inum);

        et_name.setText("김승현");
        et_id.setText("신한 | 110-514-679760");
        password = (TextView)findViewById(R.id.bt_password);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitySignUpPassward.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(auth,"auth").commit();
        super.onStart();
    }


}

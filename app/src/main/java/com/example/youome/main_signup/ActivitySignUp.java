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

        //et_phone = (TextInputEditText)findViewById(R.id.et_phone);
        /*et_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        });*/

        //textInputLayout = (TextInputLayout)findViewById(R.id.textinputlayout);
        et_name = (TextInputEditText)findViewById(R.id.et_name);
        et_id = (TextInputEditText)findViewById(R.id.et_inum);
        //et_id.addTextChangedListener(new PatternedTextWatcher("######-#******"));

        //auth = new FragmentSignUp1Auth();
        //tele = new FragmentSignUp2Tele();
        //tele.setCancelable(false);
        //auth.setCancelable(false);

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

    // ----------------------- Network API -----------------------
    public class NetworkJoinReq {
        @SerializedName("memberEmail")
        String memberEmail;
        @SerializedName("memberPwd")
        String memberPwd;
        @SerializedName("memberName")
        String memberName;

        public NetworkJoinReq(String memberEmail, String memberPwd, String memberName) {
            this.memberEmail = memberEmail;
            this.memberPwd = memberPwd;
            this.memberName = memberName;
        }
    }

    public class NetworkJoinRes {
        @SerializedName("resultCode")
        int resultCode;
        @SerializedName("message")
        String message;
        @SerializedName("memberToken")
        int memberToken;

        public NetworkJoinRes(int resultCode, String message, int memberToken) {
            this.resultCode = resultCode;
            this.message = message;
            this.memberToken = memberToken;
        }
        public int getResultCode() {
            return resultCode;
        }
        public String getMessage() {
            return message;
        }
        public int getMemberToken() {
            return memberToken;
        }
    }
}

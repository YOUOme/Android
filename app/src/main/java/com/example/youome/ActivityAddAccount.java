package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class ActivityAddAccount extends AppCompatActivity {

    TextInputEditText et_bank,et_account,et_checknum;
    Button bt_code,bt_ok;

    FragmentAddAccount fragmentAddAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_bank = (TextInputEditText)findViewById(R.id.et_bank);
        et_account = (TextInputEditText)findViewById(R.id.et_account_num);
        et_checknum = (TextInputEditText)findViewById(R.id.et_checknum);
        bt_code = (Button)findViewById(R.id.bt_code);
        bt_ok = (Button)findViewById(R.id.bt_ok);

        et_bank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && !fragmentAddAccount.isAdded()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(fragmentAddAccount, "bank").commit();
                    et_bank.setOnFocusChangeListener(null);
                    //onetime = false;
                }
            }
        });

        fragmentAddAccount = new FragmentAddAccount();
    }
}
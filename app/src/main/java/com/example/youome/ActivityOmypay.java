package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ActivityOmypay extends AppCompatActivity {
    LinearLayout react_button;
    EditText et_money;
    TextView tx_aftermoney;
    private int myMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omypay);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myMoney = getIntent().getIntExtra("money",0);
        tx_aftermoney = (TextView)findViewById(R.id.tx_aftermoney);
        String s = null;
        try { s = String.format("%,d", myMoney); } catch (NumberFormatException e) { }
        tx_aftermoney.setText(s);

        react_button = (LinearLayout)findViewById(R.id.react_button);
        et_money = (EditText)findViewById(R.id.et_money);
        et_money.addTextChangedListener(new NumberTextWatcherForThousand(et_money));

        et_money.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_money.getWindowToken(), 0);
                try{Thread.sleep(200);}catch (Exception e){}
                react_button.setGravity(Gravity.BOTTOM);
                return false;
            }
        });

        et_money.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                react_button.setGravity(Gravity.TOP);
            }
        });

        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {react_button.setGravity(Gravity.TOP);}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                String s = null;
                int addMoney=0;
                if(!NumberTextWatcherForThousand.trimCommaOfString(et_money.getText().toString()).isEmpty())
                    addMoney = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(et_money.getText().toString()));
                try { s = String.format("%,d", myMoney+addMoney); } catch (NumberFormatException e) { }
                tx_aftermoney.setText(s);
                //react_button.setGravity(Gravity.TOP);
            }
        });
    }
}
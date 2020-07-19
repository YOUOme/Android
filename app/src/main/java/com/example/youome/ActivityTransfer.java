package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTransfer extends AppCompatActivity {
    LinearLayout react_button;
    EditText et_money;
    TextView tx_aftermoney, bt_account_change, bt_alarm,tx_sel_account;
    private int myMoney, addMoney=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myMoney = getIntent().getIntExtra("money",0);
        tx_aftermoney = (TextView)findViewById(R.id.tx_aftermoney);
        tx_sel_account = (TextView)findViewById(R.id.tx_sel_account);
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

        et_money.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                react_button.setGravity(Gravity.TOP);
                return false;
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
                if(!NumberTextWatcherForThousand.trimCommaOfString(et_money.getText().toString()).isEmpty())
                    addMoney = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(et_money.getText().toString()));
                try { s = String.format("%,d", myMoney-addMoney); } catch (NumberFormatException e) { }
                tx_aftermoney.setText(s);
            }
        });

        bt_account_change = (TextView)findViewById(R.id.bt_account_change);
        bt_account_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityChangeAccount.class);
                startActivity(intent);
            }
        });

        bt_alarm = (TextView)findViewById(R.id.bt_alarm);
        bt_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityAlarm.class);
                startActivity(intent);
            }
        });
    }

    public void onTranseferClick(View v){
        if(et_money.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"금액을 입력하십시오",Toast.LENGTH_SHORT).show();
            return;
        }

        if(addMoney>myMoney){
            Toast.makeText(getApplicationContext(),"금액을 초과하였습니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        View dlgView = View.inflate(this, R.layout.dialog_omypay, null);
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(dlgView);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView react,hide,result,remain,fail;
        react = (TextView)dlgView.findViewById(R.id.react_messsage);
        hide = (TextView)dlgView.findViewById(R.id.react_message2);
        result = (TextView)dlgView.findViewById(R.id.result_message);
        remain = (TextView)dlgView.findViewById(R.id.remain_money);
        fail = (TextView)dlgView.findViewById(R.id.fail_message);

        //   REST API ( 등록 계좌 잔액 검증 부)
        fail.setVisibility(View.GONE);
        react.setText(et_money.getText());
        hide.setVisibility(View.VISIBLE);
        hide.setText("원");
        result.setText("송금이 완료되었습니다.");
        remain.setText(String.format("%,d", myMoney-addMoney));
        et_money.setText("");

        Button ok = (Button) dlgView.findViewById(R.id.bt_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.cancel();
            }
        });
        ImageView cancel = (ImageView) dlgView.findViewById(R.id.bt_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.cancel();
            }
        });
        react_button.setGravity(Gravity.BOTTOM);
        dlg.show();
    }
}
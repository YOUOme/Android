package com.example.youome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ActivityLoan extends AppCompatActivity {
    TextView tName, tMoney, tAccount, tInterest;
    EditText et_interest;
    CalendarView cv;
    BottomSheetBehavior mBottomSheetBehavior;
    View nestedBottomSheet;
    int selectedMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        selectedMoney = getIntent().getIntExtra("money",0);

        tName = (TextView) findViewById(R.id.tx_name);
        tName.setText(getIntent().getStringExtra("name"));
        tMoney = (TextView) findViewById(R.id.tx_money);
        tMoney.setText(getIntent().getIntExtra("money", 0) + "원");
        tAccount = (TextView) findViewById(R.id.tx_account);
        tInterest = (TextView)findViewById(R.id.tx_interest);

        et_interest = (EditText)findViewById(R.id.et_interest);
        et_interest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!(et_interest.getText().toString().isEmpty())) {
                    double interest = Double.parseDouble(et_interest.getText().toString());
                    int month = (int) (((selectedMoney/100) * interest)) / 12;
                    tInterest.setText( month+"원)");
                }
                else
                    tInterest.setText( 0+"원)");
            }
        });

        nestedBottomSheet = findViewById(R.id.bottomsheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(nestedBottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    if(!et_interest.getText().toString().isEmpty()) {
                        double interest = Double.parseDouble(et_interest.getText().toString());
                        int month = (int) (((selectedMoney/100) * interest)) / 12;
                        tAccount.setText("매달 말일 연 " + et_interest.getText() + "% (월 " + month + "원)");
                    }
                    else
                        tAccount.setText("매달 말일 연 " + "0" + "% (월 " + "0" + "원)");
                } else if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    tAccount.setText("이자율 설정");
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });
        /*cv.findViewById(R.id.cv);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(),cv.getDate()+"",Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    public void onCompleteClick(View view) {
        View dlgView = View.inflate(this, R.layout.dialog_trade, null);
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(dlgView);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button ok = (Button) dlgView.findViewById(R.id.bt_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityIOU.class);
                intent.putExtra("mode",3);
                startActivity(intent);
            }
        });

        ImageView cancel = (ImageView) dlgView.findViewById(R.id.bt_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.cancel();
            }
        });

        dlg.show();
    }
}

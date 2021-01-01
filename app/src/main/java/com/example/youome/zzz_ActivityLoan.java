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

import com.example.youome.loanholder.ActivityIOU;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class zzz_ActivityLoan extends AppCompatActivity {
    TextView tName, tMoney, tAccount, tInterest,tDate;
    EditText et_interest;
    CalendarView cv;
    BottomSheetBehavior mBottomSheetBehavior;
    View nestedBottomSheet;

    int selectedMoney;
    String seletedDay;

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
        tDate = (TextView)findViewById(R.id.tx_pickdate) ;

        long now = System.currentTimeMillis();
        Date dNow = new Date(now);
        String s= dNow.getYear()+1900+"."+String.format("%02d",(dNow.getMonth()+1))+"."+String.format("%02d",dNow.getDate())+".";
        tDate.setText(printWeekDay(s,dNow.getYear()+1900,dNow.getMonth(),dNow.getDate()));
        seletedDay = s;

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
                        tAccount.setText("매달 말일    연 " + et_interest.getText().toString() + "% (월 " + tInterest.getText().toString());
                        tAccount.setScaleX(0.9f);
                        tAccount.setScaleY(0.9f);
                    }
                } else if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    tAccount.setText("이자율 설정");
                    tAccount.setScaleX(1.0f);
                    tAccount.setScaleY(1.0f);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });

        cv = (CalendarView)findViewById(R.id.cv);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String tmp = year + "." + String.format("%02d", (month + 1)) + "." + String.format("%02d", dayOfMonth) + ".";
                seletedDay = tmp;
                tDate.setText(printWeekDay(tmp,year,month,dayOfMonth));
            }
        });
    }

    private String printWeekDay(String tmp,int year, int month, int day){
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = format.parse(year + "" + String.format("%02d",(month+1)) + "" + String.format("%02d",day));
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            switch (c.get(Calendar.DAY_OF_WEEK)){
                case 1:tmp+="월";break;
                case 2:tmp+="화";break;
                case 3:tmp+="수";break;
                case 4:tmp+="목";break;
                case 5:tmp+="금";break;
                case 6:tmp+="토";break;
                case 7:tmp+="일";break;
            }
        }catch (ParseException e){}
        return tmp;
    }

    public void onCompleteClick(View view) {
        View dlgView = View.inflate(this, R.layout.dialog_trade, null);
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(dlgView);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView name,date,money;
        name = (TextView)dlgView.findViewById(R.id.dl_name);
        name.setText(getIntent().getStringExtra("name"));
        date = (TextView)dlgView.findViewById(R.id.dl_date);
        date.setText(seletedDay);
        money = (TextView)dlgView.findViewById(R.id.dl_money);
        money.setText(selectedMoney+"원");

        Button ok = (Button) dlgView.findViewById(R.id.bt_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityIOU.class);
                intent.putExtra("mode",3);
                startActivity(intent);
                finish();
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

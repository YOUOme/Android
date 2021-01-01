package com.example.youome.debtor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youome.ActivityYOUOme;
import com.example.youome.R;

public class ActivityDebtorIOUTransfer extends AppCompatActivity {

    private Button bt_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtor_i_o_u_transfer);

        bt_ok = (Button)findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityYOUOme.class);
                startActivity(intent);
            }
        });
    }
}
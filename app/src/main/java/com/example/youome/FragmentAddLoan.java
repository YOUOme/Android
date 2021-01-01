package com.example.youome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.youome.loanholder.ActivityAddLoan;
import com.example.youome.loanholder.ActivityLoan_v2;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class FragmentAddLoan extends BottomSheetDialogFragment {

    public String name;
    SeekBar seekBar;
    TextView tName, tMoney;
    Button bt_lend;
    int parentMoney;
    int selectedMoney,type; // type : 1 = 최초 호출, 2 = 수정시 호출

    public FragmentAddLoan(String name,int type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bottomsheet_loan, container, false);

        tName = (TextView)view.findViewById(R.id.lend_name);
        tName.setText(name);
        tMoney = (TextView)view.findViewById(R.id.lend_money);

        if(type == 1) parentMoney = ((ActivityAddLoan)getActivity()).money;
        else parentMoney = ((ActivityLoan_v2)getActivity()).money;

        if(type == 1) selectedMoney = (int)(((ActivityAddLoan)getActivity()).money*0.5);
        else selectedMoney = (int)(((ActivityLoan_v2)getActivity()).money*0.5);

        tMoney.setText( String.format("%,d",(int)(parentMoney*0.5))+" 원");

        seekBar = (SeekBar)view.findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setMax(parentMoney);
        seekBar.setProgress((int)(parentMoney*0.5));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tMoney.setText(String.format("%,d",i)+" 원");
                selectedMoney = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tMoney.setText(String.format("%,d",selectedMoney) +" 원");
            }
        });

        bt_lend = (Button)view.findViewById(R.id.bt_setlend);
        bt_lend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 1) {
                    Intent intent = new Intent(getContext(), ActivityLoan_v2.class);
                    intent.putExtra("name", name);
                    intent.putExtra("money", selectedMoney);
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                    ((ActivityLoan_v2)getActivity()).tx_money.setText(tMoney.getText().toString());
            }
        });

        return view;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

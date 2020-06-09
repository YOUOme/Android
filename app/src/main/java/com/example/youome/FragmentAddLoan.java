package com.example.youome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class FragmentAddLoan extends BottomSheetDialogFragment {

    public String name;
    SeekBar seekBar;
    TextView tName, tMoney;
    Button bt_lend;
    int selectedMoney;

    public FragmentAddLoan(String name) {
        this.name = name;
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
        selectedMoney = (int)(((ActivityAddLoan)getActivity()).money*0.5);
        tMoney.setText( (int)(((ActivityAddLoan)getActivity()).money*0.5)+" 원");

        seekBar = (SeekBar)view.findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setMax(((ActivityAddLoan)getActivity()).money);
        seekBar.setProgress((int)(((ActivityAddLoan)getActivity()).money*0.5));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tMoney.setText(i+" 원");
                selectedMoney = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tMoney.setText(selectedMoney +" 원");
            }
        });

        bt_lend = (Button)view.findViewById(R.id.bt_setlend);
        bt_lend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityLoan.class);
                intent.putExtra("name",name);
                intent.putExtra("money",selectedMoney);
                startActivity(intent);
                getActivity().finish();
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

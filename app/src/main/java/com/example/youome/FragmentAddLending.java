package com.example.youome;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class FragmentAddLending extends BottomSheetDialogFragment {

    public String name;
    SeekBar seekBar;
    TextView tName, tMoney;
    int selectedMoney;

    public FragmentAddLending(String name) {
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
        final View view = inflater.inflate(R.layout.fragment_bottomsheet_lending, container, false);

        tName = (TextView)view.findViewById(R.id.lend_name);
        tName.setText(name);
        tMoney = (TextView)view.findViewById(R.id.lend_money);
        selectedMoney = (int)(((ActivityAddLending)getActivity()).money*0.5);
        tMoney.setText( (int)(((ActivityAddLending)getActivity()).money*0.5)+" 원");

        seekBar = (SeekBar)view.findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setMax(((ActivityAddLending)getActivity()).money);
        seekBar.setProgress((int)(((ActivityAddLending)getActivity()).money*0.5));
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

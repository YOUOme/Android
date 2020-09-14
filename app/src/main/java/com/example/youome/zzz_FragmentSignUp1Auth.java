package com.example.youome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class zzz_FragmentSignUp1Auth extends BottomSheetDialogFragment {

    public CheckBox c_all,c1,c2,c3,c4;
    public int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.zzz_fragment_signupauth,container,false);
        c_all = (CheckBox)view.findViewById(R.id.check_all);
        c1 = (CheckBox)view.findViewById(R.id.check_1);
        c2 = (CheckBox)view.findViewById(R.id.check_2);
        c3 = (CheckBox)view.findViewById(R.id.check_3);
        c4 = (CheckBox)view.findViewById(R.id.check_4);

        c_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    c1.setChecked(true);c2.setChecked(true);
                    c3.setChecked(true);c4.setChecked(true);
                    count = 4;
                }else{
                    c1.setChecked(false);c2.setChecked(false);
                    c3.setChecked(false);c4.setChecked(false);
                    count = 0;
                }
            }
        });

        c1.setOnCheckedChangeListener(new CCListener());
        c2.setOnCheckedChangeListener(new CCListener());
        c3.setOnCheckedChangeListener(new CCListener());
        c4.setOnCheckedChangeListener(new CCListener());

        //NavigationView nav_view = view.findViewById(R.id.navigation_view);
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
                         // Add a remove transaction before each add transaction to prevent continuous add
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
                         // The same instance will use different tags will be an exception, capture here
            e.printStackTrace();
        }
    }

    private class CCListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) count++;
            else count--;
            if(count == 4) {
                c_all.setChecked(true);
                setCancelable(true);
            }
            else {
                c_all.setChecked(false);
                setCancelable(false);
            }
        }
    }

}

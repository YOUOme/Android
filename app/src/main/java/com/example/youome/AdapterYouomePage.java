package com.example.youome;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.youome.main.FragmentTab1Home;
import com.example.youome.main.FragmentTab2IOU;
import com.example.youome.main.FragmentTab3P2P;
import com.example.youome.main.FragmentTab4O2O;

public class AdapterYouomePage extends FragmentStatePagerAdapter {

    private int tabCount;
    public AdapterYouomePage(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentTab1Home Fragment1 = new FragmentTab1Home();
                return Fragment1;
            case 1:
                FragmentTab2IOU Fragment2 = new FragmentTab2IOU();
                return Fragment2;
            case 2:
                FragmentTab3P2P Fragment3 = new FragmentTab3P2P();
                return Fragment3;
            case 3:
                FragmentTab4O2O Fragment4 = new FragmentTab4O2O();
                return Fragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

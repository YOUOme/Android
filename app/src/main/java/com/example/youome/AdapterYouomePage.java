package com.example.youome;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
                Fragment1MyAccount Fragment1 = new Fragment1MyAccount();
                return Fragment1;
            case 1:
                Fragment2Loan Fragment2 = new Fragment2Loan();
                return Fragment2;
            case 2:
                Fragment3Payback Fragment3 = new Fragment3Payback();
                return Fragment3;
            case 3:
                YouomeFragment4 Fragment4 = new YouomeFragment4();
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

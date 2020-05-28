package com.example.youome;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class YouomePageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    public YouomePageAdapter(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentMyAccount Fragment1 = new FragmentMyAccount();
                return Fragment1;
            case 1:
                YouomeFragment2 Fragment2 = new YouomeFragment2();
                return Fragment2;
            case 2:
                YouomeFragment3 Fragment3 = new YouomeFragment3();
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

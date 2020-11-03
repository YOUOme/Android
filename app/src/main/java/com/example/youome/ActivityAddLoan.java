package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ActivityAddLoan extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    int money = 500000; // 잔액을 전달받아야함.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        //tabLayout.setHorizontalScrollBarEnabled(false);
        //tabLayout.setVerticalScrollBarEnabled(false);

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setHorizontalScrollBarEnabled(false);

        AdapterYouomeLoanPage pageAdapter = new AdapterYouomeLoanPage(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);
            }// smoothScroll : disable swipe animation
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });*/

    }

    class AdapterYouomeLoanPage extends FragmentStatePagerAdapter {

        private int tabCount;
        public AdapterYouomeLoanPage(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    ActivityAddLoanFragment1 Fragment1 = new ActivityAddLoanFragment1();
                    return Fragment1;
                case 1:
                    ActivityAddLoanFragment2 Fragment2 = new ActivityAddLoanFragment2();
                    return Fragment2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }


}
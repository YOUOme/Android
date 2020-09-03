package com.example.youome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class ActivityYOUOme extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("홈")); // newTab().setIcon(R.drawable.)
        tabLayout.addTab(tabLayout.newTab().setText("지인간 대출"));
        tabLayout.addTab(tabLayout.newTab().setText("P2P 펀딩"));
        tabLayout.addTab(tabLayout.newTab().setText("1:1 펀딩"));

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true; // block swipe
            }
        });

        AdapterYouomePage pageAdapter = new AdapterYouomePage(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) { viewPager.setCurrentItem(tab.getPosition(),false);} // smoothScroll : disable swipe animation
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

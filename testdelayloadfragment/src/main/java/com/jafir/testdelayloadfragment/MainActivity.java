package com.jafir.testdelayloadfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TabLayout mTab;
    private ViewPager mViewpager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"1", "2", "3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTab = (TabLayout) findViewById(R.id.tab);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mTab.setupWithViewPager(mViewpager);

        for (int i = 0; i < 3; i++) {
            Fragment fragment = new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type",i+1);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

//        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

        });


        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void go(View v){
        startActivity(new Intent(this,TestMainFragment.class));
    }
    public void toTest(View v){
        startActivity(new Intent(this,TestViewpagerFragment.class));
    }
}

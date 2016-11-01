package com.jafir.testdelayloadfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TestViewpagerFragment extends AppCompatActivity {


    private TabLayout mTab;
    private ViewPager mViewpager;
    private List<ViewpagerItemFragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"1", "2", "3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testviewpager);

        mTab = (TabLayout) findViewById(R.id.tab);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mTab.setupWithViewPager(mViewpager);

        for (int i = 0; i < 3; i++) {
            ViewpagerItemFragment fragment = new ViewpagerItemFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i + 1);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }


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

        mViewpager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Log.d("debug", ":onViewAttachedToWindow:");
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.d("debug", ":onViewDetachedFromWindow:");
            }
        });
//        初始化index
//        fragmentList.get(0).delayInit();//不行 因为还咩有 onCreate onCreateView

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("debug", ":onPageSelected:" + position);
                //延迟加载
                fragmentList.get(position).delayInit();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}

package com.jafir.testdelayloadfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/11/1.
 */

public class TestMainFragment extends AppCompatActivity {


    private List<MainItemFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fragment);
        init();
    }

    private void init() {
        initFragments();
        changeFragment(0);
    }

    private void initFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragmentList.size() == 0) {
            for (int i = 0; i < 3; i++) {
                MainItemFragment fragment = new MainItemFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", i + 1);
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
                fragmentTransaction.add(R.id.container, fragment, "fragment" + i);
                Log.d("debug", ":add :" + i);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void changeFragment(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//      如果不等于空就初始化
//        hide 和 show才能触发onHidden add不能触发 所以要想做延迟加载
//       在onHidden里面处理的话 需要初始化的时候就把所有的fragment都add
        hideAll(fragmentTransaction);
        fragmentTransaction.show(fragmentList.get(index));
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideAll(FragmentTransaction fragmentTransaction) {
        for (int i = 0; i < fragmentList.size(); i++) {
            if (fragmentList.get(i) != null && !fragmentList.get(i).isHidden()) {
                fragmentTransaction.hide(fragmentList.get(i));
            }
        }
    }


    public void click1(View view) {
        changeFragment(0);
    }

    public void click2(View view) {
        changeFragment(1);
    }

    public void click3(View view) {
        changeFragment(2);
    }

}

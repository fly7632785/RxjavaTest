package com.jafir.rxjavatest;

import com.jafir.rxjavatest.base.BaseActivity;
import com.jafir.rxjavatest.base.BasePresenter;
import com.jafir.rxjavatest.wigdet.TranslationSearchView;

/**
 * Created by jafir on 16/9/7.
 */
public class TestCustome extends BaseActivity {



    TranslationSearchView translationSearchView;



    @Override
    protected int createLayoutView() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        translationSearchView = (TranslationSearchView) findViewById(R.id.translation);


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}

package com.jafir.rxjavatest.main;

import com.jafir.rxjavatest.base.BaseView;

/**
 * Created by jafir on 16/7/14.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter(BaseView view) {
        this.view = (MainContract.View) view;
    }
}

package com.jafir.rxjavatest.FlowLayoutTest;

import com.jafir.rxjavatest.base.BaseView;

/**
 * Created by jafir on 16/7/20.
 */
public class FlowPresenter implements FlowContract.Presenter {


    private FlowContract.View view;

    public FlowPresenter(BaseView view) {
        this.view = (FlowContract.View) view;
    }
}

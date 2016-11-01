package com.jafir.rxjavatest;

import android.view.View;
import android.widget.Button;

import com.jafir.rxjavatest.base.BaseActivity;
import com.jafir.rxjavatest.base.BasePresenter;
import com.jafir.rxjavatest.bean.RxEvent;

/**
 * Created by jafir on 16/8/8.
 */
public class RxbusActivity extends BaseActivity {

    Button button ;
    Button button1 ;


    @Override
    protected int createLayoutView() {
        return R.layout.activity_rxbus;
    }

    @Override
    protected void initData() {


        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new RxEvent("name",1));

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().postSticky(new RxEvent("name1",11));
                RxBus.getDefault().postSticky(new RxEvent("name2",11));
                RxBus.getDefault().postSticky(new RxEvent("name3",11));

            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}

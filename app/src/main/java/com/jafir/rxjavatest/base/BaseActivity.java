package com.jafir.rxjavatest.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by jafir on 16/7/14.
 */
public abstract class BaseActivity<P extends BasePresenter> extends Activity {


    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();


        super.onCreate(savedInstanceState);

        beforSetContentView();
        setContentView(createLayoutView());
//        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
//        ViewGroup viewGroup = (ViewGroup) view.getParent();
//        ViewGroup viewGroup1 = (ViewGroup) viewGroup.getParent();
//        for (int i = 0; i < viewGroup.getChildCount(); i++) {
//            Log.e("debug", "id:" + viewGroup.getChildAt(i).getId());
//
//        }
//
//        for (int i = 0; i < viewGroup1.getChildCount(); i++) {
//            Log.e("debug", "id2:" + viewGroup1.getChildAt(i).getId());
//
//        }
//        for (int i = 0; i < view.getChildCount(); i++) {
//            Log.e("debug", "id1:" + view.getChildAt(i).getId());
//
//        }
//        Log.e("debug", viewGroup.getChildCount() + "ge");
//        view.setBackgroundResource(R.drawable.bg);

//        View view1 = new View(this);
//        view1.setBackgroundResource(R.drawable.corner2);
//
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        addContentView(view1,
//                params
//        );
        initView();
        initData();
    }

    protected void beforSetContentView() {
    }

    protected abstract int createLayoutView();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract P createPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

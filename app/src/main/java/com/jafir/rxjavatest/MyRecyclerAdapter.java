package com.jafir.rxjavatest;

import android.util.Log;

import com.jafir.rxjavatest.base.Recycler;

import java.util.List;

/**
 * Created by jafir on 16/8/8.
 */
public class MyRecyclerAdapter extends MyAdapter<Recycler> {

    public MyRecyclerAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        Log.d("debug","super size:"+data.size());


    }




    @Override
    protected void convert(com.jafir.rxjavatest.BaseViewHolder var1, Recycler var2) {
        Log.d("debug","convert:"+var2.getText());
        var1.setText(R.id.item_text,var2.getText());
        var1.setImageResource(R.id.item_img,var2.getId());
//        Glide.with(mContext).load(var2.getId()).into((ImageView) var1.getView(R.id.item_img));
    }
}

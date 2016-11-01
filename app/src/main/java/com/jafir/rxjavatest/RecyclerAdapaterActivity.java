package com.jafir.rxjavatest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jafir.rxjavatest.base.Recycler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jafir on 16/8/8.
 */
public class RecyclerAdapaterActivity extends Activity {

    private RecyclerView mRecycler;
    private MyRecyclerAdapter myRecyclerAdapter;

    protected int createLayoutView() {


        return R.layout.activity_recycler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(createLayoutView());

        initData();
    }

    protected void initData() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        List mData = new ArrayList<Recycler>();

        for (int i = 0; i < 15; i++) {
            Recycler recycler = new Recycler();
            recycler.setId(R.mipmap.ic_launcher);
            recycler.setText("text111");
            mData.add(recycler);
        }
        Log.d("debug", ":" + mData.size());

        myRecyclerAdapter = new MyRecyclerAdapter(R.layout.item_recycler, mData);

        TextView t = new TextView(this);
        t.setText("header");
        TextView f = new TextView(this);
        f.setText("footer");
        myRecyclerAdapter.addHeaderView(t);
//        myRecyclerAdapter.addFooterView(f);
        myRecyclerAdapter.openLoadMore(5,true);
//        myRecyclerAdapter.notifyDataChangedAfterLoadMore(true);
        myRecyclerAdapter.setOnLoadMoreListener(new MyAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {


                Toast.makeText(RecyclerAdapaterActivity.this, "more", Toast.LENGTH_SHORT).show();
//                myRecyclerAdapter.openLoadMore(false);



                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                },2000);

            }
        });


        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycler.setAdapter(myRecyclerAdapter);

        myRecyclerAdapter.setOnRecyclerViewItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View var1, int var2) {
                Toast.makeText(RecyclerAdapaterActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });


        Log.d("debug","size:"+myRecyclerAdapter.getData().size());
        Log.d("debug","count:"+myRecyclerAdapter.getItemCount());
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArrayList<Recycler> recyclers = new ArrayList<Recycler>();

            for (int i = 0; i < 15; i++) {
                Recycler recycler = new Recycler();
                recycler.setId(R.mipmap.ic_launcher);
                recycler.setText("text111");
                recyclers.add(recycler);
            }
            myRecyclerAdapter.notifyDataChangedAfterLoadMore(recyclers,false);
        }
    };


}

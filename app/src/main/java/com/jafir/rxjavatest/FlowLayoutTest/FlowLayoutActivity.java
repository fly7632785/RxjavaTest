package com.jafir.rxjavatest.FlowLayoutTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jafir.rxjavatest.BubbleActivity;
import com.jafir.rxjavatest.R;
import com.jafir.rxjavatest.base.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

/**
 * Created by jafir on 16/7/20.
 */
public class FlowLayoutActivity extends BaseActivity<FlowContract.Presenter> implements FlowContract.View{

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private TagFlowLayout mFlowLayout;


    private Button button ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("flow","onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("flow","pause");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.w("flow","onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("flow","ondestoy");
    }
    @Override
    protected int createLayoutView() {
        return R.layout.activity_flow;
    }

    @Override
    protected void initData() {

        mFlowLayout = (TagFlowLayout) findViewById(R.id.flowlayout);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlowLayoutActivity.this, BubbleActivity.class));
            }
        });


        TagAdapter tagAdapter = new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(FlowLayoutActivity.this).inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlowLayout.setAdapter(tagAdapter);


        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                Toast.makeText(FlowLayoutActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener()
        {
            @Override
            public void onSelected(Set<Integer> selectPosSet)
            {
                setTitle("choose:" + selectPosSet.toString());
            }
        });

        tagAdapter.setSelectedList(2,4,6);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected FlowContract.Presenter createPresenter() {

        return new FlowPresenter(this);
    }
}

package com.jafir.testdelayloadfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by jafir on 16/10/31.
 */

public class MainItemFragment extends Fragment {

    private LinearLayout mRoot;
    private int type;
    private Context mContext;
    private boolean canLoad = false;
    private TextView textView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle b = getArguments();
        if (b != null) {
            type = b.getInt("type", 0);
        }
        Log.d("debug", type + ":onCreate");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("debug", type + ":onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("debug", type + ":onStart");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("debug", type + ":onCreateView");
        mRoot = new LinearLayout(container.getContext());
        canLoad = true;
        return mRoot;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            delayInit();
        }
        Log.d("debug", type + ":onHiddenChanged" + hidden);
    }


    public void delayInit() {
        if (!canLoad) {
            return;
        }
        canLoad = false;

        textView = new TextView(mContext);
        textView.setText(type + "");

        ToggleButton button = new ToggleButton(mContext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        handler.sendEmptyMessageDelayed(0, 2000);


        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    setUserVisibleHint(isChecked);
                } else {
                    setUserVisibleHint(isChecked);
                }
            }
        });
        mRoot.addView(textView);
        mRoot.addView(button);
    }

    @Override
    public boolean getUserVisibleHint() {
        Log.d("debug", type + ":getUserVisibleHint");
        return super.getUserVisibleHint();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(type + "延迟之后");
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

package com.jafir.rxjavatest.chat;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jafir.rxjavatest.R;

/**
 * Created by jafir on 16/9/21.
 */
public class InputLayout extends LinearLayout implements View.OnClickListener {

    private EditText mEdit;
    private TextView mCancel;
    private TextView mSend;
    private LinearLayout mLayout;
    private OnSenListener mSendListener;
    private Context mContext;

    public InputLayout(Context context) {
        super(context);
        init(context);
    }


    public InputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mLayout = (LinearLayout) View.inflate(context, R.layout.input_layout, null);
        addView(mLayout);

        mEdit = (EditText) mLayout.findViewById(R.id.edit);
        mCancel = (TextView) mLayout.findViewById(R.id.cancel);
        mSend = (TextView) mLayout.findViewById(R.id.send);

        mCancel.setOnClickListener(this);
        mSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                close();
                break;
            case R.id.send:
                send();
                break;
        }


    }

    public void open() {
        this.setVisibility(VISIBLE);
        mEdit.requestFocus();
        openSoftInput();
    }

    public void close() {
        this.setVisibility(GONE);
        closeSoftInput();
        mSendListener.onHide();
    }

    private void closeSoftInput() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEdit.getWindowToken(), 0); //强制隐藏键盘
    }


    public void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void openSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(mEdit, InputMethodManager.SHOW_FORCED);
    }

    public void clearText() {
        mEdit.setText("");
    }


    private void send() {
        if (!TextUtils.isEmpty(mEdit.getText().toString())) {
            if (mSendListener != null) {
                mSendListener.onSend(mEdit.getText().toString());
            } else {
                Toast.makeText(mContext, "发送消息为空", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public interface OnSenListener {
        void onSend(String s);
        void onHide();

    }

    public OnSenListener getmSendListener() {
        return mSendListener;
    }

    public void setOnSendListener(OnSenListener mSendListener) {
        this.mSendListener = mSendListener;
    }
}

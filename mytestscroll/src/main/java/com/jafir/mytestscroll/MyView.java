package com.jafir.mytestscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by jafir on 16/10/12.
 */

public class MyView extends ViewGroup {


    private static final int DURATION = 1000;
    private View headerView;
    private View contentView;

    private int headerHeight;
    private int contentHeight;
    private Context mContext;
    private Scroller mScroller;

    private Interpolator mInterpolator = new LinearInterpolator();
    private boolean isOpen = true;
    private int offsetY;


    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mScroller = new Scroller(mContext);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            ((View) headerView.getParent()).scrollTo(mScroller.getCurrX(), -mScroller.getCurrY());
//            ((View) contentView.getParent()).scrollTo(mScroller.getCurrX(), -(mScroller.getCurrY() + headerHeight));
            offsetY = mScroller.getCurrY();
            Log.e("scroll", "y:" + mScroller.getCurrY());
            Log.e("scroll", "off:" + offsetY);
            Log.e("scroll", "head top" + headerView.getTop() + "bottom" + headerView.getBottom());
            Log.e("scroll", "contentView top" + contentView.getTop() + "bottom" + contentView.getBottom());
            postInvalidate();
        } else {
            Log.e("requestlayout", "head:" + headerHeight);
            Log.e("requestlayout", "isopen:" + isOpen);

            Log.e("scroll", "head top" + headerView.getTop() + "bottom" + headerView.getBottom());
            Log.e("scroll", "contentView top" + contentView.getTop() + "bottom" + contentView.getBottom());
        }

    }

    private boolean isScrolling() {
        return mScroller != null && !mScroller.isFinished();
    }

    private void open() {
        if (isScrolling()) {
            return;
        }
        isOpen = true;
        mScroller.startScroll(0, -headerHeight, 0, headerHeight, DURATION);
        invalidate();
    }

    private void close() {
        if (isScrolling()) {
            return;
        }
        isOpen = false;
        mScroller.startScroll(0, 0, 0, -headerHeight, DURATION);
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);

        LayoutParams headerParams = headerView.getLayoutParams();
        LayoutParams contentParams = contentView.getLayoutParams();

        headerHeight = headerView.getMeasuredHeight();
        contentHeight = contentView.getMeasuredHeight();


        int headerHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, headerParams.height);
        int contentHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, height - headerParams.height);

        headerView.measure(widthMeasureSpec, headerHeightSpec);
        contentView.measure(widthMeasureSpec, contentHeightSpec);


        headerHeight = headerView.getMeasuredHeight();
        contentHeight = contentView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("scroll", "ofsetY:" + offsetY);
        int offsetY = 0;
        if(isOpen){
            offsetY = 0;
        }else {
            offsetY = -headerHeight;
        }
        headerView.layout(0, offsetY, r, headerHeight + offsetY);
        contentView.layout(0, headerHeight + offsetY, r, headerHeight + contentHeight);


        Log.e("scroll", "head top" + headerView.getTop() + "bottom" + headerView.getBottom());
        Log.e("scroll", "contentView top" + contentView.getTop() + "bottom" + contentView.getBottom());
        Log.e("scroll", "head height" + headerView.getHeight());
        Log.e("scroll", "contentView height" + contentView.getHeight());
        Log.e("scroll", "head measureheight" + headerView.getMeasuredHeight());
        Log.e("scroll", "contentView measureheight" + contentView.getMeasuredHeight());
        Log.e("scroll", "head h" + headerHeight);
        Log.e("scroll", "contentView h" + contentHeight);


    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("必须是一个header 一个content");
        }

        headerView = getChildAt(0);
        contentView = getChildAt(1);


        contentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    close();
                } else {
                    open();
                }
            }
        });

    }
}

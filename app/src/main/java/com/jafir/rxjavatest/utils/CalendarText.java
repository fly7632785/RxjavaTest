package com.jafir.rxjavatest.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by jafir on 16/7/18.
 */
public class CalendarText extends TextView {

    private Paint mPressPaint;
    private Paint mPaint;
    private Paint mSelectPaint;


    private int mPressColor = Color.BLUE;
    private int mPaintCoor = Color.RED;
    private int mSelectColor = Color.WHITE;

    private int textColor = Color.BLACK;


    private int radius;
    private int selectRadius;

    private int x;
    private int y;


    private boolean isPress;
    private boolean isSelect;
    private boolean isChoose;
    private int padding = 20;


    public CalendarText(Context context) {
        super(context);
        init();
    }


    public CalendarText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("debug", "down");
                setPress(true);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.e("debug", "up");
                setPress(false);
                break;
        }

        return super.onTouchEvent(event);
    }

    private void init() {
        mPaint = new Paint();
        mPressPaint = new Paint();
        mSelectPaint = new Paint();

        mPaint.setAntiAlias(true);
        mSelectPaint.setAntiAlias(true);
        mPressPaint.setAntiAlias(true);

        mPaint.setColor(mPaintCoor);
        mSelectPaint.setColor(mSelectColor);
        mPressPaint.setColor(mPressColor);

        setClickable(true);
        setGravity(Gravity.CENTER);
    }


    //
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (widthMode == MeasureSpec.AT_MOST){
//            Rect rect = new Rect();
//            getPaint().getTextBounds(getText().toString(),0,getText().toString().length(),rect);
//            width = rect.width();
//        }
//        setMeasuredDimension(width+20, width+20);

        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();

        Log.e("debug", "childWidthSize：" + childWidthSize);
        Log.e("debug", "childHeightSize：" + childHeightSize);
//        radius应该等于对角线/2
        int diameter = (int) Math.pow(childWidthSize * childWidthSize + childHeightSize * childHeightSize, 0.5);
        padding = (diameter - childWidthSize) / 4;
        setPadding(padding, padding, padding, padding);


        Log.e("debug", "padding：" + padding);
        Log.e("debug", "diameter：" + diameter);
        //高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(diameter, MeasureSpec.EXACTLY);
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        Log.e("debug", "widthMeasureSpec：" + widthMeasureSpec);
        Log.e("debug", "heightMeasureSpec：" + heightMeasureSpec);
//      调用了俩次父类方法 因为要重新测量宽高  这样text才能重新绘制 然后在宽高变了之后 依旧是center
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("debug", "changed：" + left);
        Log.e("debug", "top：" + top);
        Log.e("debug", "right：" + right);
        Log.e("debug", "bottom：" + bottom);

//       是总的view大小 不是text的宽高 大小
    }

    /**
     * 绘制圆圈颜色等
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        int w = getWidth();
        int h = getHeight();

        radius = Math.max(w, h) / 2;
        selectRadius = radius / 7;


        x = w / 2;
        y = h / 2;


        Log.d("debug", "x:" + x);
        Log.d("debug", "y:" + y);
        Log.d("debug", "radius:" + radius);
        Log.d("debug", "selectRadius:" + selectRadius);


//        是否选中
        if (isChoose || isPress) {
            super.setTextColor(Color.WHITE);
            //画大圆
            canvas.drawCircle(x, y, radius, mPressPaint);
        } else {
            setTextColor(textColor);
        }

        if (!isPress && isChoose) {
            canvas.drawCircle(x, y, radius, mPaint);
        }

        //画小圆

        if (isSelect) {
            if (isChoose) {
                canvas.drawCircle(x, (float) (y + (radius / 1.5)), selectRadius, mSelectPaint);
            } else {
                canvas.drawCircle(x, (float) (y + (radius / 1.5)), selectRadius, mPaint);
            }
        }
        super.onDraw(canvas);

    }


    public void setTextColor(int color) {
        this.textColor = color;
        super.setTextColor(textColor);

    }

    public void setSelect(boolean select) {
        isSelect = select;
        invalidate();
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
        invalidate();
    }

    public boolean isPress() {
        return isPress;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setPress(boolean press) {
        isPress = press;
        invalidate();
    }

    public boolean isChoose() {

        return isChoose;
    }

    public void setmPressColor(int mPressColor) {
        this.mPressColor = mPressColor;
        mPressPaint.setColor(mPressColor);
    }

    public void setmPaintCoor(int mPaintCoor) {
        this.mPaintCoor = mPaintCoor;
        mPaint.setColor(mPaintCoor);
    }

    public void setmSelectColor(int mSelectColor) {
        this.mSelectColor = mSelectColor;
        mSelectPaint.setColor(mSelectColor);
    }
}



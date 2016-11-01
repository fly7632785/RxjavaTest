package com.jafir.testvoicedetector;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jafir on 16/10/25.
 */

public class CountTextView extends View {


    private String text[];
    private int textSize = 100;
    private float currSize;
    private long duration = 1000;
    private Paint paint;
    private int textColor = Color.BLACK;
    private int currColor;
    private int index = 0;
    private long startTime = -1;
    private float interpolatedTime;
    private boolean isAnimating = false;


    public CountTextView(Context context) {
        this(context, null);
    }

    public CountTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint();
    }


    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isAnimating) {
            long currTime = SystemClock.uptimeMillis();
//        计算duration时间段类的时间值
            if (startTime == -1) {
                startTime = currTime;
            }
            interpolatedTime = (float) (currTime - startTime) / (float) duration;
            if (interpolatedTime < 1) {
                invalidate();
            } else {
                index++;
                if (index == text.length) {
                    stop();
                } else {
                    next();
                    invalidate();
                }
            }
            ArgbEvaluator argbEvaluator = new ArgbEvaluator();
            currColor = (int) argbEvaluator.evaluate(interpolatedTime, textColor, Color.TRANSPARENT);
            currSize = textSize * (1 - interpolatedTime);
            if (text == null) {
                return;
            }
            Rect rect = new Rect();
            paint.setColor(currColor);
            paint.setTextSize(currSize);
            paint.getTextBounds(text[index], 0, text[index].length(), rect);
            canvas.drawText(text[index], getWidth() / 2 - rect.centerX(), getHeight() / 2 - rect.centerY(), paint);
        }
    }

    public void start() {
        reset();
        isAnimating = true;
        invalidate();
    }

    public void stop() {
        reset();
        isAnimating = false;
    }

    private void next() {
        startTime = -1;
    }

    private void reset() {
        currSize = textSize;
        currColor = textColor;
        index = 0;
        startTime = -1;
    }
}

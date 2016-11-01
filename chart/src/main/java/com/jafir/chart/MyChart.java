package com.jafir.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by jafir on 16/10/20.
 */

public class MyChart extends View {

    private Context mContext;
    private int lineWidth = 2, columnWidth = 50;
    private int fontSize = 30;
    private int columnColor = Color.GREEN, lineColor = Color.BLUE, bgColor = Color.WHITE, textColor = Color.BLUE;

    //布局 宽高
    private int mWidth, mHeight;
    private Paint xyPaint, linePaint, columnPaint, textPaint;
    private int mCoordinatesTextColor = Color.GRAY;
    private float mCoordinatesLineWidth = 2f;

    private int minHeight;


    //    mock
    private String[] columnData = new String[]{"9.10", "9.10", "9.11", "9.14", "9.15", "9.16", "今日"};
    private int[] rawData = new int[]{4, 5, 6, 7, 8, 9, 11};
    //  y轴文字的rect 为了获取文字长宽高等信息
    private Rect textBound;
    //x轴每一个 单位值 代表的 px
    private float XScale;
    //y轴每一个 单位制 代表的 px
    private float YScale;
    //  x y包裹的矩形和整个布局之间的间距 文字的高度和宽度放在间隙里面
    private int gap = 50;

    //    data里面最大的值最小的值
    private int min, max;
    //默认Y 5个分段 最好是取5的整数
    private int YCount = 5;
    //  y轴最大的值，最小的值   区别去 data里面最大的值最小的值
    private int yMaxValue, yMinValue;


    public MyChart(Context context) {
        this(context, null);
    }

    public MyChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }


    public void setRawData(int[] rawData) {
        this.rawData = rawData;
        invalidate();
    }

    public void setColumnData(String[] columnData) {
        this.columnData = columnData;
        invalidate();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyChart, defStyleAttr, 0);
        int index = array.getIndexCount();
        for (int i = 0; i < index; i++) {
            int attr = array.getIndex(i);

            switch (attr) {
                case R.styleable.MyChart_lineColor:
                    lineColor = array.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.MyChart_columnColor:
                    columnColor = array.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.MyChart_backColor:
                    bgColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.MyChart_lineWidth:
                    lineWidth = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 2, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyChart_columnWidth:
                    columnWidth = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyChart_fontSize:
                    fontSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 34, getResources().getDisplayMetrics()));
                    break;
            }
            // 记得释放资源
            array.recycle();
        }

        initPaint();
    }

    private void initPaint() {
        xyPaint = new Paint();
        xyPaint.setAntiAlias(true);
        xyPaint.setColor(mCoordinatesTextColor);
        xyPaint.setStrokeWidth(mCoordinatesLineWidth);
        xyPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStyle(Paint.Style.STROKE);

        columnPaint = new Paint();
        columnPaint.setAntiAlias(true);
        columnPaint.setColor(columnColor);
        columnPaint.setStrokeWidth(columnWidth);
        columnPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(fontSize);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        textBound = new Rect();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int heiMode = MeasureSpec.getMode(heightMeasureSpec);

//      如果不是确定的 设置一个默认的大小
        if (widMode != MeasureSpec.EXACTLY) {
            mWidth = 300;
        }
        if (heiMode != MeasureSpec.EXACTLY) {
            mHeight = (mWidth / 5) * 3;
        }

        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rawData.length != columnData.length) {
            throw new RuntimeException("rawData.length != columnData.length");
        }

        min = (int) getArrayMin(rawData);
        max = (int) getArrayMax(rawData);
        XScale = ((float) (mWidth - getPaddingRight() - getPaddingLeft() - gap * 4)) / columnData.length;
        if(max <= 10){
            YScale = ((float) (mHeight - getPaddingBottom() - getPaddingTop() - gap * 3)) / 10;//每一个1值多px
        }else {
            YScale = ((float) (mHeight - getPaddingBottom() - getPaddingTop() - gap * 3)) / (max - min);//每一个1值多px
        }
        Log.d("debug", "scale:" + YScale);
        drawCoordinates(canvas);
        drawCoordinatesText(canvas);
        drawLines(canvas, rawData);
        drawPoints(canvas, rawData);
        drawColumns(canvas, rawData);

    }

    private void drawPoints(Canvas canvas, int[] data) {


        Log.d("debug", "scale:" + YScale);
        for (int i = 0; i < data.length; i++) {
            float x = getPaddingLeft() + 2 * gap + i * XScale + XScale / 2;
            float y = mHeight - getPaddingBottom() - gap - (YScale * (data[i] - yMinValue));
            canvas.drawCircle(x, y, 5, linePaint);
            Log.d("debug", "x:" + x);
            Log.d("debug", "y:" + y);

        }


    }

    private void drawCoordinatesText(Canvas canvas) {
        for (int i = 0; i < columnData.length; i++) {
            textPaint.getTextBounds(columnData[i], 0, columnData[i].length(), textBound);
            float x = getPaddingLeft() + 2 * gap + i * XScale - textBound.centerX() + XScale / 2;
            float y = mHeight;
            canvas.drawText(columnData[i], x, y, textPaint);
        }
//        each表示 y轴上面每个分段的分段值  比如 100默认分5段 each就是20
        float each = calcRange();
        //这里除以max这个最大值是为了有多大的去见就分成多少等分,是的后面折线的点更精准,否者就会对不齐刻度,
        for (int i = 0; i < YCount + 1; i++) {
            float x = getPaddingLeft();
            float y = mHeight - getPaddingBottom() - gap - YScale * (each * i);
            canvas.drawText(String.valueOf(yMinValue + each * i), x, y, textPaint);
        }
    }


    private void drawLines(Canvas canvas, int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            float x1 = getPaddingLeft() + 2 * gap + i * XScale + XScale / 2;
            float y1 = mHeight - getPaddingBottom() - gap - (YScale * (data[i] - yMinValue));
            float x2 = getPaddingLeft() + 2 * gap + (i + 1) * XScale + XScale / 2;
            float y2 = mHeight - getPaddingBottom() - gap - (YScale * (data[i + 1] - yMinValue));
            canvas.drawLine(x1, y1, x2, y2, linePaint);
        }
    }

    private void drawColumns(Canvas canvas, int[] data) {
        for (int i = 0; i < data.length; i++) {
            float l = getPaddingLeft() + 2 * gap + i * XScale + XScale / 2 - textBound.centerX();
            float t = mHeight - getPaddingBottom() - gap - (YScale * (data[i] - yMinValue));
            float r = getPaddingLeft() + 2 * gap + i * XScale + XScale / 2 + textBound.centerX();
            float b = mHeight - getPaddingBottom() - gap - mCoordinatesLineWidth;
            canvas.drawRect(l, t, r, b, columnPaint);
        }

    }


    /**
     * 画坐标系
     *
     * @param canvas
     */
    private void drawCoordinates(Canvas canvas) {
        // X轴
        canvas.drawLine(getPaddingLeft() + 2 * gap, mHeight - getPaddingBottom() - gap,
                mWidth - getPaddingRight() - gap, mHeight - getPaddingBottom() - gap,
                xyPaint);

//        // 绘制Y轴
//        canvas.drawLine(getPaddingLeft() + gap, getPaddingTop() + gap, getPaddingLeft() + gap,
//                mHeight - getPaddingBottom() - gap, xyPaint);
    }


    /**
     * 获取数值中的最大值
     *
     * @return
     */
    private float getArrayMax(int values[]) {

        float max = 0;
        for (int i = 0; i < values.length; i++) {
            float pre = Float.parseFloat(values[i] + "");
            max = Math.max(max, pre);
        }
        return max;
    }

    /**
     * 获取最小值
     *
     * @return
     */
    private float getArrayMin(int values[]) {
        float min = 999999;
        for (int i = 0; i < values.length; i++) {
            float pre = Float.parseFloat(values[i] + "");
            min = Math.min(min, pre);
        }

        return min;
    }


    private float calcRange() {
        yMaxValue = addTo10(max); //27 变成 30
        yMinValue = minusTo10(min);// 23 变成 20
//      求出范围的最大值 最小值  但是有一个问题就是需要 分段 这个分段最好是在5的倍数上面
        int rangeR = yMaxValue - yMinValue;
        Log.d("debug", "r1:" + yMaxValue);
        Log.d("debug", "r2:" + yMinValue);
        Log.d("debug", "range:" + rangeR);
        float each = 0;
        if (max <= 10) {
            each = 2;
            YCount = 10;
        } else {
            each = rangeR / YCount;
            each = to10(each);// 21 取上  变成30
        }
        Log.d("debug", "each:" + each);
        return each;

    }


    /**
     * 27 to 30  23 to 20
     *
     * @return
     */
    private int addTo10(int a) {
        return (a + 5) / 10 * 10;
    }

    /**
     * 23 变成 20  27 to 20
     * 230 变成 200  270 变成200
     *
     * @return
     */
    private int minusTo10(int a) {
        int result = a;

        if (a <= 100) {
            result = a / 10 * 10;
        } else if (a <= 1000) {
            result = a / 100 * 100;
        } else if (a <= 10000) {
            result = a / 1000 * 1000;
        }

        return result;
    }

    /**
     * 23 变成 30  27 to 30
     *
     * @return
     */
    private float to10(float a) {
        if (a < 10) {
            return (a / 1 + 1);
        } else {
            return (a / 10 + 1) * 10;
        }
    }

}

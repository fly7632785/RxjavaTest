package com.jafir.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jafir on 16/10/21.
 */

public class CHart extends View {
    //坐标轴原点的位置
    private int xPoint = 60;
    private int yPoint = 260;
    //刻度长度
    private int xScale = 8;  //8个单位构成一个刻度
    private int yScale = 40;
    //x与y坐标轴的长度
    private int xLength = 380;
    private int yLength = 240;

    private int MaxDataSize = xLength / xScale;   //横坐标  最多可绘制的点

    private List<Integer> data = new ArrayList<Integer>();   //存放 纵坐标 所描绘的点

    private String[] yLabel = new String[yLength / yScale];  //Y轴的刻度上显示字的集合


    private Handler mh = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {                //判断接受消息类型
                invalidate();  //刷新View
            }
        }

        ;
    };

    public CHart(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < yLabel.length; i++) {
            yLabel[i] = (i + 1) + "M/s";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {     //在线程中不断往集合中增加数据
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (data.size() > MaxDataSize) {  //判断集合的长度是否大于最大绘制长度
                        data.remove(0);  //删除头数据
                    }
                    data.add(new Random().nextInt(5) + 1);  //生成1-6的随机数
                    mh.sendEmptyMessage(0);   //发送空消息通知刷新
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        //绘制Y轴
        canvas.drawLine(xPoint, yPoint - yLength, xPoint, yPoint, paint);
        //绘制Y轴左右两边的箭头
        canvas.drawLine(xPoint, yPoint - yLength, xPoint - 3, yPoint - yLength + 6, paint);
        canvas.drawLine(xPoint, yPoint - yLength, xPoint + 3, yPoint - yLength + 6, paint);
        //Y轴上的刻度与文字
        for (int i = 0; i * yScale < yLength; i++) {
            canvas.drawLine(xPoint, yPoint - i * yScale, xPoint + 5, yPoint - i * yScale, paint);  //刻度
            canvas.drawText(yLabel[i], xPoint - 50, yPoint - i * yScale, paint);//文字
        }
        //X轴
        canvas.drawLine(xPoint, yPoint, xPoint + xLength, yPoint, paint);
        //如果集合中有数据
        if (data.size() > 1) {
            for (int i = 1; i < data.size(); i++) {  //依次取出数据进行绘制
                canvas.drawLine(xPoint + (i - 1) * xScale, yPoint - data.get(i - 1) * yScale, xPoint + i * xScale, yPoint - data.get(i) * yScale, paint);
            }
        }

    }

}
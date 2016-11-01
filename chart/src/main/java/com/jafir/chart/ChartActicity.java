package com.jafir.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 16/10/21.
 */

public class ChartActicity extends AppCompatActivity {


    private LineChart chart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        init();
    }

    private void init() {
        // in this example, a LineChart is initialized from xml
        chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(1000 + 30 * i, 1000 + 40 * i - 10));
        }
        List<Entry> entries1 = new ArrayList<Entry>();
        for (int i = 0; i < 10; i++) {
            entries1.add(new Entry(1000 + 20 * i, 1000 + 15 * i - 10));
        }

        LineDataSet dataSet = new LineDataSet(entries, "line1"); // add entries to dataset
        // set the line to be drawn like this "- - - - - -"
        dataSet.enableDashedLine(10f, 5f, 0f);
        dataSet.enableDashedHighlightLine(10f, 5f, 0f);
        dataSet.setColors(Color.BLACK, Color.GREEN, Color.CYAN, Color.YELLOW, Color.MAGENTA);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawFilled(true);
        dataSet.setValueTextColor(Color.GREEN); // styling, ...

        LineDataSet dataSet1 = new LineDataSet(entries1, "line2"); // add entries to dataset
        dataSet1.setColor(Color.RED);
        dataSet1.setValueTextColor(Color.BLACK); // styling, ...
        LineData lineData = new LineData(dataSet, dataSet1);
        chart.setData(lineData);
        Legend l = chart.getLegend();//图例
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setTextSize(10f);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
        chart.getXAxis().setLabelRotationAngle(-20);//设置x轴字体显示角度
        chart.getXAxis().setDrawGridLines(true);//是否画线
        chart.getXAxis().setDrawGridLines(true);
        chart.getAxisRight().setEnabled(true);
        chart.getAxisLeft().setEnabled(true);
        chart.getAxisLeft().setDrawAxisLine(true);
        chart.getAxisLeft().setAxisLineColor(Color.CYAN);//设置轴线颜色：
        chart.getAxisRight().setDrawZeroLine(true);
        chart.getAxisLeft().setDrawZeroLine(true);
//        chart.getAxisLeft().setAxisMinimum(0);
        chart.animateXY(3000,3000);//图表数据显示动画
        chart.setContentDescription("黑呵呵呵");
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value == 1000) {
                    return "一月";
                }
                return "哦";
            }

            @Override
            public int getDecimalDigits() {
//                钱的表现形式
//                DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");
                return 0;
            }
        });
//        chart.setScaleEnabled(true);
        chart.setScaleEnabled(false);
        chart.invalidate(); // refresh

    }
}

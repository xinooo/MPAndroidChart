package com.example.MPAndroidChart.Chart.MyChart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.renderer.LineChartRenderer;

public class MyLineChart extends LineChart{
    public MyLineChart(Context context) {
        super(context);
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new LineChartRenderer(this, mAnimator, mViewPortHandler);
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, mXAxis, mLeftAxisTransformer);
    }

    public void reset(){
        init();
    }
}

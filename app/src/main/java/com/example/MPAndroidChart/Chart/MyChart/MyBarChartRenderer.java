package com.example.MPAndroidChart.Chart.MyChart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyBarChartRenderer extends BarChartRenderer {

    private RectF mBarShadowRectBuffer = new RectF();

    public MyBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        // draw the bar shadow before the values
        if (mChart.isDrawBarShadowEnabled()) {
            mShadowPaint.setColor(dataSet.getBarShadowColor());

            BarData barData = mChart.getBarData();

            final float barWidth = barData.getBarWidth();
            final float barWidthHalf = barWidth / 2.0f;
            float x,y;

            for (int i = 0, count = Math.min((int)(Math.ceil((float)(dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                 i < count;
                 i++) {

                BarEntry e = dataSet.getEntryForIndex(i);

                x = e.getX();
                y = e.getY();

                mBarShadowRectBuffer.left = x - barWidthHalf;
                mBarShadowRectBuffer.right = x + barWidthHalf;

                trans.rectValueToPixel(mBarShadowRectBuffer);

                if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right))
                    continue;

                if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left))
                    break;

                mBarShadowRectBuffer.top = mViewPortHandler.contentTop();
                mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom();

                float radius = (mBarShadowRectBuffer.right - mBarShadowRectBuffer.left) / 2;

                if (y > 0){
                    c.drawRect(mBarShadowRectBuffer.left, mBarShadowRectBuffer.top + radius, mBarShadowRectBuffer.right, mBarShadowRectBuffer.bottom, mShadowPaint);
                    c.drawCircle(mBarShadowRectBuffer.left + radius, mBarShadowRectBuffer.top + radius, radius, mShadowPaint);
                }else {
                    c.drawRect(mBarShadowRectBuffer.left, mBarShadowRectBuffer.top, mBarShadowRectBuffer.right, mBarShadowRectBuffer.bottom - radius, mShadowPaint);
                    c.drawCircle(mBarShadowRectBuffer.left + radius, mBarShadowRectBuffer.bottom - radius, radius, mShadowPaint);
                }
//                c.drawRect(mBarShadowRectBuffer, mShadowPaint);
            }
        }

        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        final boolean isSingleColor = dataSet.getColors().size() == 1;

        if (isSingleColor) {
            mRenderPaint.setColor(dataSet.getColor());
        }

        for (int j = 0; j < buffer.size(); j += 4) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue;

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;

            if (!isSingleColor) {
                // Set the color for the currently drawn value. If the index
                // is out of bounds, reuse colors.
                mRenderPaint.setColor(dataSet.getColor(j / 4));
            }

            float y = dataSet.getEntryForIndex(j/4).getY();

            float left = buffer.buffer[j];
            float top = buffer.buffer[j + 1];
            float right = buffer.buffer[j + 2];
            float bottom = buffer.buffer[j + 3];
            float radius = (right - left) / 2;
            if (y > 0) {
                //高度減去圓的半徑
                c.drawRect(left, top + radius, right, bottom, mRenderPaint);
                //畫圓
                c.drawCircle(left + radius, top + radius, radius, mRenderPaint);
            }else {
                //高度減去圓的半徑
                c.drawRect(left, top, right, bottom - radius, mRenderPaint);
                //畫圓
                c.drawCircle(left + radius, bottom - radius, radius, mRenderPaint);
            }


//            c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
//                    buffer.buffer[j + 3], mRenderPaint);

            if (drawBorder) {
                c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                        buffer.buffer[j + 3], mBarBorderPaint);
            }
        }
    }
}

package com.example.MPAndroidChart.Chart.MyChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyHorizontalBarChartRenderer extends HorizontalBarChartRenderer {

    private RectF mBarShadowRectBuffer = new RectF();

    public MyHorizontalBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
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
            float x;

            for (int i = 0, count = Math.min((int)(Math.ceil((float)(dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                 i < count;
                 i++) {

                BarEntry e = dataSet.getEntryForIndex(i);

                x = e.getX();

                mBarShadowRectBuffer.top = x - barWidthHalf;
                mBarShadowRectBuffer.bottom = x + barWidthHalf;

                trans.rectValueToPixel(mBarShadowRectBuffer);

                if (!mViewPortHandler.isInBoundsTop(mBarShadowRectBuffer.bottom))
                    continue;

                if (!mViewPortHandler.isInBoundsBottom(mBarShadowRectBuffer.top))
                    break;

                mBarShadowRectBuffer.left = mViewPortHandler.contentLeft();
                mBarShadowRectBuffer.right = mViewPortHandler.contentRight();


                float radius = (mBarShadowRectBuffer.bottom - mBarShadowRectBuffer.top) / 2;

                //高度減去圓的半徑
                c.drawRect(mBarShadowRectBuffer.left, mBarShadowRectBuffer.top, mBarShadowRectBuffer.right - radius, mBarShadowRectBuffer.bottom, mShadowPaint);
                //畫圓
                c.drawCircle(mBarShadowRectBuffer.right - radius, mBarShadowRectBuffer.top + radius, radius, mShadowPaint);

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
            coloringLine(dataSet,mRenderPaint,c.getWidth(),c.getHeight(),dataSet.getColor());
        }

        for (int j = 0; j < buffer.size(); j += 4) {

            if (!mViewPortHandler.isInBoundsTop(buffer.buffer[j + 3]))
                break;

            if (!mViewPortHandler.isInBoundsBottom(buffer.buffer[j + 1]))
                continue;

            if (!isSingleColor) {
                // Set the color for the currently drawn value. If the index
                // is out of bounds, reuse colors.
                coloringLine(dataSet,mRenderPaint,c.getWidth(),c.getHeight(),dataSet.getColor(j / 4));
            }

            float left = buffer.buffer[j];
            float top = buffer.buffer[j + 1];
            float right = buffer.buffer[j + 2];
            float bottom = buffer.buffer[j + 3];
            float radius = (bottom - top) / 2;

            //高度減去圓的半徑
            c.drawRect(left, top, right - radius, bottom, mRenderPaint);
            //畫圓
            c.drawCircle(right - radius, top + radius, radius, mRenderPaint);


//            c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
//                    buffer.buffer[j + 3], mRenderPaint);

            if (drawBorder) {
                c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                        buffer.buffer[j + 3], mBarBorderPaint);
            }
        }
    }

    private void coloringLine(IBarDataSet dataSet, Paint renderer, float pointX, float pointY, Integer color) {

        try {
            if(dataSet.getColors().size() == 1){
                mRenderPaint.setShader(new LinearGradient(0,0,pointX,0,color,Color.WHITE, Shader.TileMode.CLAMP));
            }else{
                mRenderPaint.setShader(new LinearGradient(
                        0,
                        0,
                        pointX,
                        0,
                        preparePrimitiveColors(dataSet),
                        null,
                        Shader.TileMode.CLAMP
                ));
            }
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            renderer.setColor(dataSet.getColor());
            ex.printStackTrace();
        }
    }

    private int[] preparePrimitiveColors(IBarDataSet dataSet) {
        int[] colors = new int[dataSet.getColors().size()];
        int i = 0;
        for (int color : dataSet.getColors()) {
            colors[i] = color;
            i++;
        }
        return colors;
    }
}

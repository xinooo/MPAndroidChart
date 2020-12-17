package com.example.MPAndroidChart.Chart.MyChart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyXAxisRenderer extends XAxisRenderer {
    public static float Hight = 2;
    public MyXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }
//    public MyXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans,float hight) {
//        super(viewPortHandler, xAxis, trans);
//        Hight = hight;
//    }

    //重寫X軸Label範圍大小
    protected void computeSize() {

        String longest = mXAxis.getLongestLabel();

        mAxisLabelPaint.setTypeface(mXAxis.getTypeface());
        mAxisLabelPaint.setTextSize(mXAxis.getTextSize());

        final FSize labelSize = Utils.calcTextSize(mAxisLabelPaint, longest);

        final float labelWidth = labelSize.width;
        final float labelHeight = labelSize.height*Hight;

        final FSize labelRotatedSize = Utils.getSizeOfRotatedRectangleByDegrees(
                labelWidth,
                labelHeight,
                mXAxis.getLabelRotationAngle());


        mXAxis.mLabelWidth = Math.round(labelWidth);
        mXAxis.mLabelHeight = Math.round(labelHeight);
        mXAxis.mLabelRotatedWidth = Math.round(labelRotatedSize.width);
        mXAxis.mLabelRotatedHeight = Math.round(labelRotatedSize.height);

        FSize.recycleInstance(labelRotatedSize);
        FSize.recycleInstance(labelSize);
    }

    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        float labelHeight = mXAxis.getTextSize();
        String[] labels = formattedLabel.split(",");


        Paint mFirstLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstLinePaint.setColor(Color.BLACK);
        mFirstLinePaint.setTextAlign(Paint.Align.CENTER);
        mFirstLinePaint.setTextSize(Utils.convertDpToPixel(15f));
        mFirstLinePaint.setTypeface(mXAxis.getTypeface());


        Paint mSecondLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondLinePaint.setColor(Color.BLACK);
        mSecondLinePaint.setTextAlign(Paint.Align.CENTER);
        mSecondLinePaint.setTextSize(Utils.convertDpToPixel(10f));
        mSecondLinePaint.setTypeface(mXAxis.getTypeface());

        if (labels.length > 1) {
            Utils.drawXAxisValue(c, labels[0], x, y, mFirstLinePaint, anchor, angleDegrees);
            Utils.drawXAxisValue(c, labels[1], x, y + labelHeight, mSecondLinePaint, anchor, angleDegrees);
        } else {
            Utils.drawXAxisValue(c, formattedLabel, x, y, mFirstLinePaint, anchor, angleDegrees);
        }
    }
}

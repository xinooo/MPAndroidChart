package com.example.MPAndroidChart.Chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MPAndroidChart.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PieChartActivity extends AppCompatActivity {

    private PieChart mPieChart;
    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        mPieChart = (PieChart) findViewById(R.id.mPieChart);
        ll = (LinearLayout) findViewById(R.id.ll);
        drawPieChart();
    }

    private void drawPieChart() {
        showPieChart(mPieChart, getPieData());
    }

    private void showPieChart(final PieChart pieChart, PieData pieData) {
        pieChart.setHoleRadius(20f); //中間區塊半徑
        pieChart.setTransparentCircleRadius(30f); //半透明圈
        pieChart.setEntryLabelColor(Color.BLACK);


        pieChart.setDescription(null);

        pieChart.setDrawHoleEnabled(false);//餅狀圖顯示中間區塊

        pieChart.setRotationAngle(90); //初始角度
        pieChart.setRotationEnabled(true); //能夠手動旋轉
        pieChart.setUsePercentValues(true); //顯示百分比
        pieChart.setDragDecelerationFrictionCoef(0.95f);//轉動阻力摩擦係數

//        pieChart.setDrawCenterText(true); //餅狀圖中間能夠加入文字
//        pieChart.setCenterText("人員分布");
//        pieChart.setCenterTextColor(Color.GRAY);

        pieChart.setData(pieData);

        Legend mLegend = pieChart.getLegend();
        //設置圖例
        drawLegen(mLegend);
        //設置圖例
        drawLegen(getLabels(mLegend),getColors(mLegend));//獲取圖例文字、圖例顏色

        pieChart.animateXY(1000, 1000);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
//                Log.e("AAA",""+(int)h.getX()+", ");
//                Log.e("AAA",""+pieChart.getData().getDataSet().getColor((int)h.getX()));
            }

            @Override
            public void onNothingSelected() {

            }
        });

        pieChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.e("BBB",pieChart.getIndexForAngle(90)+"");
                Log.e("BBB","A:" + getLabels(pieChart.getLegend())[pieChart.getIndexForAngle(90)]);
                String now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss  ").format(new Date());
                Log.e("BBB","A:" + now);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
    }

    private int [] getColors(Legend legend) {
        LegendEntry[] legendEntries = legend.getEntries();
        int [] colors = new int[legendEntries.length];
        for (int i = 0; i < legendEntries.length; i++) {
            colors[i] = legendEntries[i].formColor;
        }
        return colors;
    }

    private String [] getLabels(Legend legend) {
        LegendEntry [] legendEntries = legend.getEntries();
        String [] labels = new String[legendEntries.length];
        for (int i = 0; i < legendEntries.length; i++) {
            labels[i] = legendEntries[i].label;
        }
        return labels;
    }


    private PieData getPieData() {
        String[]  content = new String[] {"交通", "飲食", "家庭開銷", "美容美髮", "旅遊"};
        ArrayList<PieEntry> yValue = new ArrayList<PieEntry>(); //用來表示封裝每一個餅塊的實際數據

        List<Float> qs = new ArrayList<Float>();
        qs.add(14.5f); qs.add(13.5f);qs.add(24f);qs.add(29f);qs.add(19f);
        for (int i = 0; i < qs.size(); i++) {
            yValue.add(new PieEntry(qs.get(i), content[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(yValue,"");
        pieDataSet.setSliceSpace(2f);//每一個餅塊的距離
        ArrayList<Integer> colors = new ArrayList<Integer>();
        //餅圖顏色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        colors.add(Color.rgb(50, 188, 70));
        pieDataSet.setColors(colors); //設置顏色
        pieDataSet.setValueTextSize(20f);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setDrawValues(true);

        pieDataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        pieDataSet.setValueLinePart1Length(0.6f);
        pieDataSet.setValueLinePart2Length(0.4f);
        pieDataSet.setValueLineColor(Color.YELLOW);//设置连接线的颜色
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); //選中態多出的長度
//        PieData pieData = new PieData(xValues, pieDataSet);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        return pieData;
    }

    private void drawLegen(Legend mLegend){
        mLegend.setEnabled(true);
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        mLegend.setForm(Legend.LegendForm.DEFAULT);//設置圖例形狀
        mLegend.setFormSize(20f);//圖例大小
        mLegend.setXEntrySpace(40f);
        mLegend.setYEntrySpace(5f);
        mLegend.setTextSize(20);
        mLegend.setTextColor(Color.GRAY);
        mLegend.setWordWrapEnabled(true);
    }

    private void drawLegen(String[] legendlabels, int[] legendcolors){
        int count = 0;
        while (count < legendcolors.length - 1) {
            LinearLayout row = new LinearLayout(this);
            for (int j = 0; j < 3; j++){
                if(count < legendcolors.length - 1){
                    LinearLayout.LayoutParams left_parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(left_parms);

                    //圖例icon
                    Bitmap icon = drawIcon(legendcolors[count],50,100);
                    Drawable legend_drawable = new BitmapDrawable(getResources(), icon);

                    //圖例文字
                    LinearLayout.LayoutParams txt_parms = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
                    TextView legen_txt = new TextView(this);
                    legen_txt.setLayoutParams(txt_parms);
                    legen_txt.setText(legendlabels[count]);
                    legen_txt.setTextSize(20);

                    legen_txt.setCompoundDrawablesWithIntrinsicBounds(legend_drawable,null,null,null);
                    legen_txt.setCompoundDrawablePadding(10);
                    row.addView(legen_txt);
                }
                count++;
            }

            //row < 3，繼續添加view
            while (row.getChildCount() < 3){
                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                textView.setVisibility(View.INVISIBLE);
                row.addView(textView);
            }
            ll.addView(row);
        }
    }

    private Bitmap drawIcon(int iconColor, int Size, int radius){
        Bitmap cvbitmap = Bitmap.createBitmap(Size,Size, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(cvbitmap);

        //設定座標點 Rect(左,上,右,下)
        Rect rect = new Rect(0,0,Size,Size);
        RectF rectF = new RectF(rect);
        //去鋸齒
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(iconColor);
        //畫圓角矩形
        canvas.drawRoundRect(rectF, radius, radius, paint);
        canvas.save();
        canvas.restore();
        if (cvbitmap.isRecycled()){
            cvbitmap.recycle();
        }
        return cvbitmap;
    }
}

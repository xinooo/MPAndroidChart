package com.example.MPAndroidChart.Chart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.MPAndroidChart.Chart.MyChart.DoubleXLabelAxisRenderer;
import com.example.MPAndroidChart.Chart.MyChart.MyBarChart;
import com.example.MPAndroidChart.Chart.MyChart.MyHorizontalBarChart;
import com.example.MPAndroidChart.R;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;


import java.util.ArrayList;


public class BarChartActivity extends AppCompatActivity {

    private MyBarChart bar_chart;
    String[]  content = new String[] {"交通", "飲食", "家庭開銷", "美容美髮", "旅遊"};
    String[]  content2 = new String[] {"10.5%", "14.5%", "25%", "18%", "22%"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        bar_chart = (MyBarChart)findViewById(R.id.mbarChart);
        BarDataSet barDataSet = new BarDataSet(dataValue(),"data1");
        barDataSet.setValueTextSize(20);
        barDataSet.setColors(//第一个颜色会从顶部开始显示
                Color.parseColor("#4D78FE"));


        Legend legend = bar_chart.getLegend();
        legend.setEnabled(false);

        bar_chart.setTouchEnabled(false);
        bar_chart.setNoDataText("No Data");
        bar_chart.setNoDataTextColor(Color.BLUE);
        bar_chart.setDrawGridBackground(false);
        bar_chart.setDrawBorders(false);
        bar_chart.setBorderColor(Color.RED);
        bar_chart.setDrawBarShadow(true);
        bar_chart.getAxisLeft().setEnabled(false);//不显示左侧
        bar_chart.getAxisRight().setEnabled(false);//不显示右侧
        bar_chart.getAxisRight().setDrawGridLines(false);
        bar_chart.getAxisLeft().setAxisMinimum(0f);
        bar_chart.setDrawValueAboveBar(false);//數值是否顯示在長條圖內

        XAxis xAxis = bar_chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(content){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value < 0) value = -1;
                return super.getFormattedValue(value, axis);
            }
        });
        xAxis.setTextColor(Color.BLACK);//x轴文字颜色
//        xAxis.setAxisMinimum(0);
        xAxis.setTextSize(12f);
        xAxis.setEnabled(true);//显示x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);//设置x轴的显示位置
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(5, false);

        Description description = new Description();
        description.setText("");
        bar_chart.setDescription(description);

        bar_chart.setXAxisRenderer(new DoubleXLabelAxisRenderer(bar_chart.getViewPortHandler(), xAxis, bar_chart.getTransformer(YAxis.AxisDependency.LEFT),new IndexAxisValueFormatter(content2)));





        BarData barData = new BarData(barDataSet);
        barData.setDrawValues(false); //是否顯示數值
        barData.setBarWidth(0.5f);
        barData.setValueFormatter(new PercentFormatter());
        bar_chart.setData(barData);
        bar_chart.invalidate();
    }

    private ArrayList<BarEntry> dataValue(){
        ArrayList<BarEntry> dataValue = new ArrayList<BarEntry>();
        dataValue.add(new BarEntry(0,10.5f));
        dataValue.add(new BarEntry(1,14.5f));
        dataValue.add(new BarEntry(2,25));
        dataValue.add(new BarEntry(3,18));
        dataValue.add(new BarEntry(4,22));
        return dataValue;
    }
}

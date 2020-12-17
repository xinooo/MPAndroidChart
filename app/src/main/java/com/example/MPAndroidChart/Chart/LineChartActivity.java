package com.example.MPAndroidChart.Chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MPAndroidChart.Chart.MyChart.MyLineChart;
import com.example.MPAndroidChart.R;
import com.example.MPAndroidChart.greenDAO.DBService;
import com.example.MPAndroidChart.greenDAO.DBTools;
import com.example.MPAndroidChart.greenDAO.Ledger;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    private DBService dbService = new DBService();
    private MyLineChart linechart;
    String[] content = new String[] {"週一,哈哈哈", "週二,哈哈哈", "週三,哈哈哈", "週四,哈哈哈", "週五,哈哈哈", "週六,哈哈哈", "週日,哈哈哈"};
    String[] week = new String[] {"週一", "週二", "週三", "週四", "週五", "週六", "週日"};
    String[]  content2 = new String[] {"一月,哈哈哈", "二月,哈哈哈", "三月,哈哈哈", "四月,哈哈哈", "五月,哈哈哈", "六月,哈哈哈",
                                        "七月,哈哈哈","八月,哈哈哈", "九月,哈哈哈", "十月,哈哈哈", "十一月,哈哈哈", "十二月,哈哈哈"};
    private Button btn1,btn2,btn3,btn4;
    private List<Ledger> ledgerList = new ArrayList<>();
    private List<String> xAxisValueList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        dbService.init(this);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        linechart = (MyLineChart)findViewById(R.id.mLineChart);
        refresh(dataValue(), content,null);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh(dataValue(), content,null);
//                DBTools.save(dbService,"","",new Random().nextInt(500),"");
//                dbService.getDaoSession().getLedgerDao().deleteAll();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledgerList = DBTools.query(dbService);
                xAxisValueList.clear();
                for (int i = 0; i < ledgerList.size(); i++){
                    xAxisValueList.add(ledgerList.get(i).getName() + ","+ ledgerList.get(i).getType());
                }
                refresh(dataValue3(ledgerList),null,xAxisValueList);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledgerList = DBTools.getWeekData(dbService);
                xAxisValueList.clear();
                for (int i = 0; i < ledgerList.size(); i++){
                    xAxisValueList.add(week[i] + "," + ledgerList.get(i).getDate());
                }
                refresh(dataValue3(ledgerList), null,xAxisValueList);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledgerList = DBTools.getMonthData(dbService);
                xAxisValueList.clear();
                for (int i = 0; i < ledgerList.size(); i++){
                    xAxisValueList.add(ledgerList.get(i).getDate());
                }
                refresh(dataValue3(ledgerList), null,xAxisValueList);
            }
        });


    }

    private ArrayList<Entry> dataValue3(List<Ledger> ledgers){
        ArrayList<Entry> dataValue = new ArrayList<Entry>();
        for (int i = 0; i < ledgers.size(); i++){
            dataValue.add(new Entry(i,ledgers.get(i).getDollars()));
        }
        return dataValue;
    }

    private ArrayList<Entry> dataValue(){
        ArrayList<Entry> dataValue = new ArrayList<Entry>();
        dataValue.add(new Entry(0,5604));
        dataValue.add(new Entry(1,8860));
        dataValue.add(new Entry(2,-3050));
        dataValue.add(new Entry(3,-7770));
//        dataValue.add(new Entry(4,4460));
//        dataValue.add(new Entry(5,5909));
//        dataValue.add(new Entry(6,6650));
        return dataValue;
    }

    private ArrayList<Entry> dataValue2(){
        ArrayList<Entry> dataValue = new ArrayList<Entry>();
        dataValue.add(new Entry(0,5));
        dataValue.add(new Entry(1,8));
        dataValue.add(new Entry(2,3));
        dataValue.add(new Entry(3,7));
        dataValue.add(new Entry(4,4));
        dataValue.add(new Entry(5,9));
        dataValue.add(new Entry(6,6));
        dataValue.add(new Entry(7,5));
        dataValue.add(new Entry(8,9));
        dataValue.add(new Entry(9,3));
        dataValue.add(new Entry(10,1));
        dataValue.add(new Entry(11,4));
        return dataValue;
    }

    private void refresh(ArrayList<Entry> dataValue,String[]  content,List<String> list){
        linechart.clear();
        linechart.reset();
        LineDataSet Linedataset = new LineDataSet(dataValue,"data1");



        Linedataset.setLineWidth(0.5f);
        Linedataset.setColor(getResources().getColor(R.color.line, getTheme()));
        Linedataset.setDrawCircles(true);
        Linedataset.setDrawCircleHole(true);
        Linedataset.setCircleColor(getResources().getColor(R.color.line, getTheme()));
        Linedataset.setCircleColorHole(getResources().getColor(R.color.line, getTheme()));
        Linedataset.setValueTextSize(20);
        Linedataset.setValueTextColor(getResources().getColor(R.color.color_Accent, getTheme()));
        Linedataset.enableDashedLine(10,5,0);
        Linedataset.setDrawFilled(true);
        Linedataset.setFillDrawable(getDrawable(R.drawable.chart_shadow));

        Legend legend = linechart.getLegend();
        legend.setEnabled(false);


        linechart.setNoDataText("No Data");
        linechart.setNoDataTextColor(Color.BLUE);
        linechart.setDrawGridBackground(true);
        linechart.setDrawBorders(true);
        linechart.setBorderColor(getResources().getColor(R.color.red, getTheme()));
        linechart.getAxisRight().setEnabled(false);//不显示右侧
        //设置是否可以拖拽
        linechart.setDragEnabled(true);
        //设置是否可以缩放
        linechart.setScaleEnabled(false);

        XAxis xAxis = linechart.getXAxis();
        if (content != null){
            xAxis.setValueFormatter(new IndexAxisValueFormatter(content));
        }else {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
        }
        xAxis.setTextColor(Color.BLACK);//x轴文字颜色
        xAxis.setTextSize(20);
        xAxis.setEnabled(true);//显示x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setSpaceMin(0.5f);//折線起點距離左側Y軸距離
        xAxis.setSpaceMax(0.5f);//折線終點距離右側Y軸距離
        xAxis.setGridLineWidth(0.5f);
        xAxis.setGridColor(getResources().getColor(R.color.color_CECECE, getTheme()));

        if (dataValue.size() < 7){
            xAxis.setLabelCount(dataValue.size(), false);
        }

//        lineChart.getAxisLeft().setAxisMinimum(0);
        linechart.getAxisLeft().setDrawGridLines(false);



        Description description = new Description();
        description.setText("");
        linechart.setDescription(description);
//        xAxis.setLabelCount(dataValue.size(), false);

        float ratio = (float) dataValue.size()/(float) 7;
        //重新设置x轴放大倍数
        linechart.zoom(0f, 1f, 0F, 0F);
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        linechart.zoom(ratio,1f,0,0);

        LineData data = new LineData();
        data.addDataSet(Linedataset);
        if (dataValue.size() > 0){
            linechart.setData(data);
        }
        linechart.invalidate();
    }

}

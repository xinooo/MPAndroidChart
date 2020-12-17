package com.example.MPAndroidChart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.MPAndroidChart.Chart.BarChartActivity;
import com.example.MPAndroidChart.Chart.LineChartActivity;
import com.example.MPAndroidChart.Chart.PieChartActivity;
import com.example.MPAndroidChart.greenDAO.DBService;

public class MainActivity extends AppCompatActivity {

    private DBService dbService = new DBService();
    private Button btn_linechart,btn_piechart,btn_barchart,btn_income,btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbService.init(this);
        btn_linechart = (Button)findViewById(R.id.btn_linechart);
        btn_piechart = (Button)findViewById(R.id.btn_piechart);
        btn_barchart = (Button)findViewById(R.id.btn_barchart);
        btn_income = (Button)findViewById(R.id.btn_income);
        btn_clear = (Button)findViewById(R.id.btn_clear);

        btn_linechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LineChartActivity.class));
            }
        });
        btn_piechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PieChartActivity.class));
            }
        });
        btn_barchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BarChartActivity.class));
            }
        });
        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IncomeActivity.class));
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbService.getDaoSession().getLedgerDao().deleteAll();
            }
        });
    }
}

package com.example.MPAndroidChart;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MPAndroidChart.greenDAO.DBService;
import com.example.MPAndroidChart.greenDAO.DBTools;

import java.util.ArrayList;
import java.util.List;

public class IncomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DBService dbService = new DBService();
    private List<String> typeList = new ArrayList<>();
    private Spinner spinner;
    private EditText edit_Name,edit_Dollars;
    private MySpinnerAdapter mySpinnerAdapter;
    private Button btn_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        dbService.init(this);
        typeList.add("工资");typeList.add("奖金");typeList.add("投资");typeList.add("副业");
        spinner = (Spinner)findViewById(R.id.spinner);
        edit_Name = (EditText) findViewById(R.id.edit_Name);
        edit_Dollars = (EditText) findViewById(R.id.edit_Dollars);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        mySpinnerAdapter = new MySpinnerAdapter(this,typeList);
        spinner.setAdapter(mySpinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dollars = Integer.parseInt(edit_Dollars.getText().toString());
                DBTools.save(dbService, (String) spinner.getSelectedItem(),edit_Name.getText().toString(),dollars,"");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mySpinnerAdapter.setSelect(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
